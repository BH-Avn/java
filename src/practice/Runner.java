package practice;

// ============================================================================
// REMINDER: compile ALL .java files in the "practice" package BEFORE running
// this launcher, so their .class files exist in bin/practice for scanning:
//
//     javac -d bin src/practice/*.java
//     java -cp bin practice.Runner
//
// If you add/change a class in src/practice, re-run the javac command above
// before launching Runner again, or Runner will scan stale/missing .class
// files.
// ============================================================================

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import static utils.Base.scanner;

/**
 * Terminal launcher that finds runnable classes among the compiled .class
 * files in bin/practice (including package-private classes that share a
 * .java file with others) and runs them via reflection.
 *
 * Uses the single shared utils.Base.scanner (not its own "new Scanner(System.in)")
 * because every practice class's own main() also reads from System.in via that
 * same shared scanner - two independent Scanner objects wrapping System.in would
 * fight over its internal read buffer and silently drop input between them.
 */
public class Runner {

    public static void main(String[] args) {
        File binRoot = resolveBinRoot();
        File packageDir = new File(binRoot, "practice");
        File historyFile = new File(packageDir, "runner_history.json");

        // --name (e.g. --re_1): run that class immediately on startup, skipping
        // the menu just this once. After that, the loop behaves normally.
        String pendingDirect = parseDirectTarget(args);

        boolean keepGoing = true;
        while (keepGoing) {
            List<HistoryEntry> history = loadHistory(historyFile);
            String targetSimpleName;

            if (pendingDirect != null) {
                targetSimpleName = pendingDirect;
                pendingDirect = null;
            } else {
                List<String> eligible = new ArrayList<>();
                List<String> notRunnable = new ArrayList<>();
                scanClasses(packageDir, eligible, notRunnable);

                // Unified numbering: recent runs first, then eligible classes continue
                // the same number sequence (so there's only one number space to type from).
                List<String> numbered = new ArrayList<>();

                System.out.println();
                if (!history.isEmpty()) {
                    System.out.println("Recent runs:");
                    for (HistoryEntry h : history) {
                        numbered.add(h.className);
                        System.out.println("  " + numbered.size() + ". " + h.className + "  (" + h.timestamp + ")");
                    }
                }

                if (!eligible.isEmpty()) {
                    System.out.println("Eligible classes:");
                    for (String name : eligible) {
                        numbered.add(name);
                        System.out.println("  " + numbered.size() + ". " + name);
                    }
                } else if (history.isEmpty()) {
                    System.out.println("No eligible classes found in " + packageDir.getPath());
                }

                if (!notRunnable.isEmpty()) {
                    System.out.println("Not runnable:");
                    for (String line : notRunnable) {
                        System.out.println("  - " + line);
                    }
                }

                System.out.print("\nEnter a number, or type a class name directly (q to quit): ");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("q")) {
                    break;
                }
                if (input.isEmpty()) {
                    continue;
                }

                targetSimpleName = resolveSelection(input, numbered);
                if (targetSimpleName == null) {
                    System.out.println("No such menu number. Try again.");
                    continue;
                }
            }

            boolean ran = runClass(packageDir, targetSimpleName);
            if (ran) {
                addToHistory(historyFile, history, targetSimpleName);
            }

            System.out.print("\nRun another? (y/n): ");
            String again = scanner.nextLine().trim();
            if (again.equalsIgnoreCase("n")) {
                keepGoing = false;
            }
        }

        System.out.println("Goodbye!");
        // Not closing scanner: it's utils.Base's shared instance, not ours to close.
    }

    /** Looks for a "--ClassName" argument, e.g. "--re_1", and returns "re_1" if found. */
    private static String parseDirectTarget(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("--") && arg.length() > 2) {
                return arg.substring(2);
            }
        }
        return null;
    }

    /**
     * Runner's own .class file lives in bin/practice at runtime. Its classpath
     * root (the "bin" folder passed via -cp) is what getCodeSource() reports,
     * so we use that instead of hardcoding a path relative to the current
     * working directory.
     */
    private static File resolveBinRoot() {
        try {
            return new File(Runner.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (Exception e) {
            // Fallback if run in an unusual way (e.g. not via -cp bin): assume cwd is the project root.
            return new File("bin");
        }
    }

    /**
     * Scans packageDir for .class files and sorts each one into "eligible"
     * (has a public run() plus a usable no-arg constructor, OR a public
     * static main(String[]) - which needs no constructor at all) or
     * "notRunnable" (with a short reason).
     */
    private static void scanClasses(File packageDir, List<String> eligible, List<String> notRunnable) {
        File[] files = packageDir.listFiles((dir, name) -> name.endsWith(".class"));
        if (files == null) {
            return;
        }
        Arrays.sort(files, Comparator.comparing(File::getName));

        for (File f : files) {
            String simpleName = f.getName().substring(0, f.getName().length() - ".class".length());

            // Skip Runner itself, and skip compiler-generated classes: lambdas, anonymous
            // classes, and nested classes all produce Outer$Something.class files that
            // aren't top-level classes you wrote to run directly.
            if (simpleName.equals("Runner") || simpleName.contains("$")) {
                continue;
            }

            String fqn = "practice." + simpleName;
            try {
                // initialize=false: we only need constructor/method metadata right now,
                // not to actually run the class's static initializers yet.
                Class<?> cls = Class.forName(fqn, false, Runner.class.getClassLoader());

                if (cls.isInterface() || Modifier.isAbstract(cls.getModifiers())) {
                    notRunnable.add(simpleName + " (interface/abstract - cannot be instantiated)");
                    continue;
                }

                // run() is an instance method, so it needs a usable no-arg constructor.
                // main(String[]) is static, so it needs no constructor at all - useful for
                // classes like sp, which only ever takes constructor args.
                boolean hasRun = findPublicNoArgMethod(cls, "run") != null;
                boolean hasUsableCtor = hasUsableNoArgConstructor(cls);
                boolean hasMain = findPublicStaticMain(cls) != null;

                if ((hasRun && hasUsableCtor) || hasMain) {
                    eligible.add(simpleName);
                } else if (hasRun) {
                    notRunnable.add(simpleName + " (has run() but no usable no-arg constructor)");
                } else {
                    notRunnable.add(simpleName + " (no public run()+constructor, or public static main(String[]))");
                }
            } catch (ClassNotFoundException e) {
                notRunnable.add(simpleName + " (class file could not be loaded)");
            }
        }
    }

    private static boolean hasUsableNoArgConstructor(Class<?> cls) {
        try {
            // getDeclaredConstructor() finds a no-arg constructor regardless of its
            // access modifier (unlike getConstructor(), which only finds public ones).
            // Runner lives in the same "practice" package as these classes, and calls
            // setAccessible(true) before using it, so public AND package-private
            // constructors both work - only a genuinely private one is off-limits.
            Constructor<?> ctor = cls.getDeclaredConstructor();
            return !Modifier.isPrivate(ctor.getModifiers());
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    private static Method findPublicNoArgMethod(Class<?> cls, String methodName) {
        try {
            // getMethod() only returns PUBLIC methods (declared or inherited).
            return cls.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private static Method findPublicStaticMain(Class<?> cls) {
        try {
            Method m = cls.getMethod("main", String[].class);
            return Modifier.isStatic(m.getModifiers()) ? m : null;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * Turns the user's raw input into a simple class name to run: a number
     * within menu range maps to that menu entry, anything else is treated as
     * a class name typed directly (even if it wasn't shown as eligible - the
     * reflection calls below will report a clear error if it can't actually run).
     */
    private static String resolveSelection(String input, List<String> numbered) {
        try {
            int idx = Integer.parseInt(input);
            if (idx >= 1 && idx <= numbered.size()) {
                return numbered.get(idx - 1);
            }
            return null; // a valid number, but outside the menu's range
        } catch (NumberFormatException e) {
            return input; // not a number - treat it as a typed class name
        }
    }

    /**
     * Loads, constructs, and invokes the target class via reflection.
     * Returns true if the class was actually instantiated and its entry point
     * was invoked (even if that entry point then threw its own exception) -
     * that's what counts as a "run" worth recording in history.
     */
    private static boolean runClass(File packageDir, String simpleName) {
        String fqn = "practice." + simpleName;

        // Step 1: load the Class object by fully-qualified name. initialize=true
        // this time, since we're about to actually use the class for real.
        Class<?> cls;
        try {
            cls = Class.forName(fqn, true, Runner.class.getClassLoader());
        } catch (ClassNotFoundException e) {
            System.out.println("Error: class '" + fqn + "' was not found. Did you compile it? (javac -d bin src/practice/*.java)");
            return false;
        }

        // Step 2: find an entry point - prefer run() if present, otherwise fall back to main(String[]).
        Method runMethod = findPublicNoArgMethod(cls, "run");
        Method mainMethod = runMethod == null ? findPublicStaticMain(cls) : null;
        if (runMethod == null && mainMethod == null) {
            System.out.println("Error: '" + simpleName
                    + "' has neither a public no-arg run() nor a public static main(String[]) method.");
            return false;
        }

        try {
            System.out.println("\n--- Running " + simpleName + " ---");
            if (runMethod != null) {
                // run() is an instance method, so we need an object to call it on. That's
                // what the no-arg constructor + newInstance() below are for. main(String[])
                // is static and skips this entirely - see the else branch.
                Constructor<?> ctor;
                try {
                    ctor = cls.getDeclaredConstructor();
                    if (Modifier.isPrivate(ctor.getModifiers())) {
                        System.out.println("Error: '" + simpleName + "'s no-arg constructor is private.");
                        return false;
                    }
                } catch (NoSuchMethodException e) {
                    System.out.println("Error: '" + simpleName + "' has no no-arg constructor.");
                    return false;
                }

                // setAccessible(true): defensive - lets us call public members even when the
                // declaring class itself is package-private (Runner lives in the same
                // "practice" package, so this normally isn't even needed, but it's a safe fallback).
                ctor.setAccessible(true);
                runMethod.setAccessible(true);

                // Step 3: actually create the instance - equivalent to "new ClassName()".
                Object instance = ctor.newInstance();
                // Step 4: invoke the method on the instance - equivalent to "instance.run()".
                runMethod.invoke(instance);
            } else {
                // main(String[]) is static, so the target object is null. The (Object) cast
                // is important: without it, invoke(...) would try to spread the String[]
                // as varargs instead of passing it as the single intended argument.
                mainMethod.setAccessible(true);
                mainMethod.invoke(null, (Object) new String[0]);
            }
            System.out.println("--- Finished " + simpleName + " ---");
            return true;
        } catch (InvocationTargetException e) {
            // The target method ran but threw its own exception. Reflection wraps that
            // real exception inside getCause() - unwrap it so the message is useful.
            Throwable cause = e.getCause();
            System.out.println("'" + simpleName + "' threw an exception while running: "
                    + (cause != null ? cause.getClass().getSimpleName() + ": " + cause.getMessage() : e));
            return true; // it did run - just failed internally - so it's still worth logging
        } catch (IllegalAccessException | InstantiationException e) {
            System.out.println("Error: could not create/run '" + simpleName + "': " + e);
            return false;
        }
    }

    // ---- run history (stored as hand-rolled JSON, no external library) ----

    private static class HistoryEntry {
        final String className;
        final String timestamp;

        HistoryEntry(String className, String timestamp) {
            this.className = className;
            this.timestamp = timestamp;
        }
    }

    private static void addToHistory(File historyFile, List<HistoryEntry> history, String simpleName) {
        history.add(0, new HistoryEntry(simpleName, new Date().toString()));
        while (history.size() > 5) {
            history.remove(history.size() - 1);
        }
        saveHistory(historyFile, history);
    }

    private static List<HistoryEntry> loadHistory(File historyFile) {
        List<HistoryEntry> list = new ArrayList<>();
        if (!historyFile.exists()) {
            return list;
        }

        String json;
        try (BufferedReader br = new BufferedReader(new FileReader(historyFile))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append(' ');
            }
            json = sb.toString();
        } catch (IOException e) {
            System.out.println("Warning: could not read history file (" + e.getMessage() + "); starting with empty history.");
            return list;
        }

        // We only ever parse JSON that this class wrote itself, in one fixed shape:
        // an array of {"className":"...","timestamp":"..."} objects. A full JSON
        // parser would be overkill for that, so we just scan for { ... } chunks by hand.
        int pos = 0;
        while (true) {
            int start = json.indexOf('{', pos);
            if (start < 0) {
                break;
            }
            int end = json.indexOf('}', start);
            if (end < 0) {
                break;
            }
            String body = json.substring(start + 1, end);
            String className = extractJsonValue(body, "className");
            String timestamp = extractJsonValue(body, "timestamp");
            if (className != null && timestamp != null) {
                list.add(new HistoryEntry(className, timestamp));
            }
            pos = end + 1;
        }
        return list;
    }

    private static String extractJsonValue(String objBody, String key) {
        String marker = "\"" + key + "\"";
        int keyIdx = objBody.indexOf(marker);
        if (keyIdx < 0) {
            return null;
        }
        int colon = objBody.indexOf(':', keyIdx + marker.length());
        if (colon < 0) {
            return null;
        }
        int quoteStart = objBody.indexOf('"', colon + 1);
        if (quoteStart < 0) {
            return null;
        }

        StringBuilder value = new StringBuilder();
        int i = quoteStart + 1;
        while (i < objBody.length() && objBody.charAt(i) != '"') {
            char c = objBody.charAt(i);
            if (c == '\\' && i + 1 < objBody.length()) {
                value.append(objBody.charAt(i + 1));
                i += 2;
            } else {
                value.append(c);
                i++;
            }
        }
        return value.toString();
    }

    private static void saveHistory(File historyFile, List<HistoryEntry> history) {
        StringBuilder sb = new StringBuilder("[\n");
        for (int i = 0; i < history.size(); i++) {
            HistoryEntry h = history.get(i);
            sb.append("  {\"className\":\"").append(escape(h.className))
              .append("\",\"timestamp\":\"").append(escape(h.timestamp)).append("\"}");
            if (i < history.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("]\n");

        try (PrintWriter pw = new PrintWriter(new FileWriter(historyFile))) {
            pw.print(sb);
        } catch (IOException e) {
            System.out.println("Warning: could not save run history (" + e.getMessage() + ")");
        }
    }

    private static String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}

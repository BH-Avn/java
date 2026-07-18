# Java Learning & Practice

A collection of Java projects and exercises built while learning the language.

> **Note:** All code in this repo is written by me.

---

## Projects

### Trig Formula Database — `src/completed/Main.java`
A CLI app for storing and searching trigonometric formulas. Formulas are persisted to a flat file (`Formula_database.txt`) and loaded on startup.

**Features:** add · search by expression or keyword · list all · edit · delete

**Run:**
```bash
javac -d bin src/utils/Base.java src/completed/Main.java
java -cp bin completed.Main
```

---

### Linear Equation Solver — `src/completed/equations.java`
Solves single-variable linear equations (e.g. `3x + 2 = 8`) entered as strings.

**How it works:** tokenizes the input → Shunting-Yard algorithm → builds an expression tree → evaluates to find `x`.

**Run:**
```bash
javac -d bin src/completed/equations.java
java -cp bin completed.equations
```

---

## Practice

Every practice class is runnable through **Runner** (`src/practice/Runner.java`) — a
terminal launcher that scans the compiled `.class` files in `bin/practice` (including
package-private classes sharing a file with others) and runs whichever one you pick.

**Workflow:** new practice code is either a method added to an existing class, or a whole
new class — either way, give it a public no-arg `run()` (instance method) or a
`public static void main(String[])`, then test it through Runner instead of writing a
one-off launcher.

**Run:**
```bash
javac -d bin -cp bin src/practice/*.java "src/practice/school work.java" src/utils/Base.java
java -cp bin practice.Runner
```

- Shows recent runs (last 5, cached in `bin/practice/runner_history.json`) and all eligible
  classes in one numbered menu — pick a number, or type a class name directly.
- `java -cp bin practice.Runner --ClassName` skips the menu and runs that class immediately,
  then continues into the normal "Run another?" loop.
- Ineligible classes are listed under "Not runnable" with a reason instead of being hidden.

| File | Contents |
|---|---|
| `src/practice/lc.java` | `lc` — LeetCode problems: Contains Duplicate II, sliding window sum |
| `src/practice/learning.java` | `InkiPinkiPonki` — electron shell-filling demo |
| `src/practice/re_1.java` | Three classes: `re_1` (recursion — count/palindrome/Tower of Hanoi), `sp` (grid + string utilities — spiral matrix, anagram, prime checks, string compression, etc.), `school` (matrix multiply, cow-distribution trick, int palindrome) |
| `src/practice/recursion.java` | `recursion` — digit sum, string palindrome, array reverse, power, find max, string permutations |
| `src/practice/school work.java` | `digitToWord` / `NumberToWords` — two number-to-words converters, plus `Main` to choose between them |

---

## Structure

```
src/
  completed/      finished, runnable programs
  practice/       exercises and experiments
  utils/          Base.java — shared Scanner, print helpers, input validation
lib/
  sqlite-jdbc-3.53.2.0.jar
docs/
  formula-db-design.md    design notes for the Formula DB
```

---

## Requirements
- Java 11+
- No build tool required — compile manually with `javac` as shown above
- SQLite driver in `lib/` isn't currently used by any active exercise (kept for future SQLite practice)

---

## Clone
```bash
git clone https://github.com/BH-Avn/java.git
```

/* ax+b=c ; a , b ,c  x=   
*/ // 
package completed;

import java.util.*;

class BN {
    String data;
    BN left;
    BN right;

    BN(String data) {
        this.data = data;
        left = right = null;
    }
}

public class equations {
    // Global variables must be static to be accessed directly from the static main
    // method
    static HashMap<String, Double> knownVars = new HashMap<>();
    static Scanner in = new Scanner(System.in);

    // 1. The Pre-Processor (Standardization & Implicit Multiplication)
    static String standard(String s1) {
        s1 = s1.replace(" ", "");

        StringBuffer eq = new StringBuffer();
        for (int i = 0; i < s1.length(); i++) {
            char c = s1.charAt(i);
            eq.append(c);

            // CHANGE: Look-ahead logic to inject explicit '*' operators
            if (i < s1.length() - 1) {
                char next = s1.charAt(i + 1);
                // IF Digit touches Letter/Parenthesis OR Letter touches
                // Digit/Letter/Parenthesis
                if ((Character.isDigit(c) && (Character.isLetter(next) || next == '(')) ||
                        (Character.isLetter(c) && (Character.isLetterOrDigit(next) || next == '('))) {
                    eq.append('*');
                }
            }
        }

        // Standardize the equals sign
        int equalIndex = eq.indexOf("=");
        if (equalIndex != -1) {
            eq.insert(equalIndex, "-(");
            eq.deleteCharAt(equalIndex + 2); // remove '='
            eq.append(")");
        }
        return eq.toString();
    }

    // 2. The Tokenizing Parser
    static String shunting_yard(String s1) {
        StringBuffer output = new StringBuffer();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s1.length(); i++) {
            char x = s1.charAt(i);

            // CHANGE: Group contiguous letters, digits, or decimals into a single token
            if (Character.isLetterOrDigit(x) || x == '.') {
                while (i < s1.length() && (Character.isLetterOrDigit(s1.charAt(i)) || s1.charAt(i) == '.')) {
                    output.append(s1.charAt(i));
                    i++;
                }
                output.append(" "); // CHANGE: Inject space delimiter to separate words
                i--; // Step back to align with the outer for-loop increment
            } else if (x == '(') {
                stack.push(x);
            } else if (x == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(stack.pop()).append(" "); // CHANGE: Add spaces after popped operators
                }
                if (!stack.isEmpty() && stack.peek() == '(')
                    stack.pop();
            } else if (x == '+' || x == '-' || x == '*' || x == '/') {
                while (!stack.isEmpty() && stack.peek() != '(' && precendenceHE(x, stack.peek())) {
                    output.append(stack.pop()).append(" "); // CHANGE: Add spaces after popped operators
                }
                stack.push(x);
            }
        }

        while (!stack.isEmpty()) {
            output.append(stack.pop()).append(" "); // CHANGE: Add spaces after remaining operators
        }

        return output.toString();
    }

    // Helper method for Shunting-Yard operator precedence
    static boolean precendenceHE(char x, char y) {
        int a = (x == '-' || x == '+') ? 1 : 2;
        int b = (y == '-' || y == '+') ? 1 : 2;
        return a <= b; // True if stack top (y) has >= precedence than current (x)
    }

    // 4. Build AST: Constructs a Binary Tree from the Postfix expression
    // 3. The Token Tree Builder
    static BN postToTree(String pf) {
        Stack<BN> stack = new Stack<>();

        // CHANGE: Split the postfix string by spaces into an array of complete words
        String[] tokens = pf.trim().split("\\s+");

        for (int i = 0; i < tokens.length; i++) {
            String t = tokens[i];
            if (t.isEmpty())
                continue;

            if (t.equals("+") || t.equals("-") || t.equals("*") || t.equals("/")) {
                BN node = new BN(t);
                node.right = stack.pop();
                node.left = stack.pop();
                stack.push(node);
            } else {
                // CHANGE: Push the entire string token (e.g., "14" or "vel") into the node
                stack.push(new BN(t));
            }
        }
        return stack.pop();
    }

    // 5. Evaluate Engine: Recursively calculates the mathematical value of the tree
    static double evaluate(BN node, double x) {
        // Base Case: Leaf node (variable or number)
        if (node.left == null && node.right == null) {

            // 1. Is it exactly our target variable "x"?
            if (node.data.equals("x")) {
                return x;
            }
            // 2. Is it a known user-defined variable? (O(1) lookup)
            else if (knownVars.containsKey(node.data)) {
                return knownVars.get(node.data);
            }
            // 3. Fallback: If it's not 'x' and not in the map, it MUST be a number.
            else {
                return Double.parseDouble(node.data);
            }
        }

        // Recursive Step: Evaluate the left and right branches
        double leftVal = evaluate(node.left, x);
        double rightVal = evaluate(node.right, x);

        // Process Root: Apply the mathematical operator
        switch (node.data) {
            case "+":
                return leftVal + rightVal;
            case "-":
                return leftVal - rightVal;
            case "*":
                return leftVal * rightVal;
            case "/":
                return leftVal / rightVal;
        }

        return 0; // Default fallback
    }

    // 6. The Mathematical Solver: Orchestrates the pipeline
    static void solve(String equation) {
        // Run the 3-step pipeline
        String standardEq = standard(equation);
        String postfix = shunting_yard(standardEq);
        BN root = postToTree(postfix);

        // Find C and M using the expression tree
        double C = evaluate(root, 0.0);
        double f1 = evaluate(root, 1.0);
        double M = f1 - C;

        System.out.println("\n--- Calculating ---");
        System.out.println("Standardized: " + standardEq);
        System.out.println("Postfix: " + postfix);

        // Handle mathematical edge cases
        if (M == 0) {
            if (C == 0) {
                System.out.println("Result: The equation has infinitely many solutions (0 = 0)");
            } else {
                System.out.println("Result: The equation has no solution (e.g., 5 = 0)");
            }
        } else {
            double x = -C / M;
            System.out.println("Result: x = " + x);
        }
        System.out.println("-------------------\n");
    }

    // 7. Program Main Execution
    public static void main(String args[]) {
        boolean enterEquation = true;
        String s1 = "";

        while (true) {
            if (enterEquation) {
                System.out.println("Enter Equation (use 'x' as target variable):");
                s1 = in.nextLine();
            }
            if (s1.trim().isEmpty() || !s1.contains("=")) {
                System.out.println("Error: Invalid input. Equation must contain an '=' sign.");
                continue; //
            }
            // Look for custom variables (a, b, v, etc.) and prompt the user
            for (int i = 0; i < s1.length(); i++) {
                char x = s1.charAt(i);

                if (Character.isLetter(x) && x != 'x') {
                    if (!knownVars.containsKey("" + x)) {
                        System.out.print("Enter the value of " + x + ": ");
                        knownVars.put("" + x, in.nextDouble());
                    }
                }
            }

            solve(s1);

            // Post-evaluation Menu
            System.out.println("1 - New Equation");
            System.out.println("2 - Same Equation, different values");
            System.out.println("3 - Exit");
            System.out.print("Select an option: ");
            int choice = in.nextInt();

            in.nextLine(); // Clear the Scanner buffer to prevent skipped inputs

            switch (choice) {
                case 1:
                    enterEquation = true;
                    knownVars.clear(); // Empty map for a brand new equation
                    continue; // Loops back to the start
                case 2:
                    enterEquation = false;
                    knownVars.clear(); // Empty map to force fresh prompts for the same formula
                    continue;
                case 3:
                    System.out.println("Exiting Program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Error, invalid value. Stopping the run!");
                    System.exit(0);
            }
        }
    }
}

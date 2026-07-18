package practice;

import java.util.*;
import static utils.Base.scanner;

class recursion {
    int sumDigits(int n) {
        if (n / 10 == 0)
            return n;
        return n % 10 + sumDigits(n / 10);
    }

    boolean isPalindrome(String s) {
        if (s == null || s.equals(""))
            return false;
        if (s.length() <= 2 && s.charAt(0) == s.charAt(s.length() - 1))
            return true;

        return (s.charAt(0) == s.charAt(s.length() - 1)) && isPalindrome(s.substring(1, s.length() - 1));
    }

    void reverse(int[] arr, int start, int end) {
        if (start < end) {
            arr[start] = arr[start] + arr[end];
            arr[end] = arr[start] - arr[end];
            arr[start] = arr[start] - arr[end];
            reverse(arr, start + 1, end - 1);
        }
    }

    int power(int x, int n) {
        if (n == 0)
            return 1;
        if (n == 1)
            return x;
        return x * power(x, n - 1);
    }

    int findMax(int[] arr, int n) {
        if (arr.length - 1 == n)
            return arr[arr.length - 1];
        if (arr[n] > arr[n + 1]) {
            int temp = arr[n];
            arr[n] = arr[n + 1];
            arr[n + 1] = temp;
        }
        return findMax(arr, n + 1);
    }

    void string_permutation(String remaining, String result) {
        if (remaining.length() == 0) {
            System.out.println(result);
            return;
        }

        for (int i = 0; i < remaining.length(); i++) {
            char ch = remaining.charAt(i);
            String ref = remaining.substring(0, i) + remaining.substring(i + 1);
            string_permutation(ref, result + ch);
        }
    }

    // Runnable via Runner (java -cp bin practice.Runner --recursion).
    public static void main(String[] args) {
        Scanner sc = scanner; // shared utils.Base.scanner - see Runner.java for why
        recursion obj = new recursion();

        System.out.println("1. sumDigits          - sum of an integer's digits");
        System.out.println("2. isPalindrome       - check if a string is a palindrome");
        System.out.println("3. reverse            - reverse an int array in place");
        System.out.println("4. power              - x raised to the power n");
        System.out.println("5. findMax            - find the max value in an int array");
        System.out.println("6. string_permutation - print all permutations of a string");
        System.out.print("Choose a method: ");
        int choice = Integer.parseInt(sc.nextLine().trim());

        switch (choice) {
            case 1: {
                System.out.print("Enter number: ");
                int n = Integer.parseInt(sc.nextLine().trim());
                System.out.println("Sum of digits: " + obj.sumDigits(n));
                break;
            }
            case 2: {
                System.out.print("Enter string: ");
                String s = sc.nextLine();
                System.out.println("Is palindrome: " + obj.isPalindrome(s));
                break;
            }
            case 3: {
                System.out.print("Enter numbers separated by spaces: ");
                String[] parts = sc.nextLine().trim().split("\\s+");
                int[] arr = new int[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    arr[i] = Integer.parseInt(parts[i]);
                }
                obj.reverse(arr, 0, arr.length - 1);
                System.out.println("Reversed: " + Arrays.toString(arr));
                break;
            }
            case 4: {
                System.out.print("Enter base x: ");
                int x = Integer.parseInt(sc.nextLine().trim());
                System.out.print("Enter exponent n: ");
                int n = Integer.parseInt(sc.nextLine().trim());
                System.out.println("Result: " + obj.power(x, n));
                break;
            }
            case 5: {
                System.out.print("Enter numbers separated by spaces: ");
                String[] parts = sc.nextLine().trim().split("\\s+");
                int[] arr = new int[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    arr[i] = Integer.parseInt(parts[i]);
                }
                System.out.println("Max: " + obj.findMax(arr, 0));
                break;
            }
            case 6: {
                System.out.print("Enter string: ");
                String s = sc.nextLine();
                obj.string_permutation(s, "");
                break;
            }
            default:
                System.out.println("Invalid choice.");
        }
    }
}
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

    int power(int base, int exp) {
        if (exp == 0)
            return 1; // Any number to the power of 0 is 1
        return base * power(base, exp - 1);
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

    int factorial(int n) {
        if (n == 0)
            return 1;
        return n * factorial(n - 1);
    }

    int fibonacci(int n) {
        if (n <= 1)
            return n; // Base cases: 0 returns 0, 1 returns 1
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    int tribonacci(int n) {
        if (n == 0)
            return 0;
        if (n == 1 || n == 2)
            return 1;
        return tribonacci(n - 1) + tribonacci(n - 2) + tribonacci(n - 3);
    }

    boolean isPrime(int n, int divisor) {
        if (n <= 1)
            return false;
        if (divisor == n)
            return true; // Reached the number without finding factors
        if (n % divisor == 0)
            return false; // Found a factor, not prime
        return isPrime(n, divisor + 1);
    }

    int binarySearch(int[] arr, int target, int left, int right) {
        if (left > right)
            return -1; // Base case 1: Search space exhausted, target not found

        int mid = left + (right - left) / 2; // Prevents potential integer overflow

        if (arr[mid] == target)
            return mid; // Base case 2: Target found

        if (arr[mid] > target) {
            return binarySearch(arr, target, left, mid - 1); // Search left half
        }
        return binarySearch(arr, target, mid + 1, right); // Search right half
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
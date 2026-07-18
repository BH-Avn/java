package practice;

import java.util.*;
import static utils.Base.scanner;

class lc {
    /*
     * Problem: Contains Duplicate II
     * Given an integer array nums and an integer k, return true if there are two
     * distinct indices i and j in the array such that nums[i] == nums[j] and abs(i
     * - j) <= k.
     * 
     * Example 1:
     * 
     * Input: nums = [1, 2, 3, 1], k = 3
     * 
     * Output: true
     */

    boolean problem1(int arr[], int k) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {

            if (map.containsKey(arr[i]) && (int) Math.abs(map.get(arr[i]) - i) <= k) {
                return true;
            }
            map.put(arr[i], i);

        }
        return false;
    }
 static int sliding_window(int n[], int k) {

        int s = 0;
        for (int i = 0; i < k; i++) {
            s += n[i];
        }

        for (int i = k; i < n.length; i++) {
            s += n[i] - n[i - k];
        }
        return s;
    }
 
    // Runnable via Runner (java -cp bin practice.Runner --lc).
    public static void main(String[] args) {
        Scanner sc = scanner; // shared utils.Base.scanner - see Runner.java for why
        lc obj = new lc();

        System.out.println("1. problem1       - contains duplicate within distance k");
        System.out.println("2. sliding_window - sum of the first k-sized window");
        System.out.print("Choose a method: ");
        int choice = Integer.parseInt(sc.nextLine().trim());

        switch (choice) {
            case 1: {
                int[] arr = readInts(sc);
                System.out.print("Enter k: ");
                int k = Integer.parseInt(sc.nextLine().trim());
                System.out.println("Result: " + obj.problem1(arr, k));
                break;
            }
            case 2: {
                int[] arr = readInts(sc);
                System.out.print("Enter k: ");
                int k = Integer.parseInt(sc.nextLine().trim());
                System.out.println("Result: " + sliding_window(arr, k));
                break;
            }
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static int[] readInts(Scanner sc) {
        System.out.print("Enter numbers separated by spaces: ");
        String[] parts = sc.nextLine().trim().split("\\s+");
        int[] arr = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            arr[i] = Integer.parseInt(parts[i]);
        }
        return arr;
    }
}
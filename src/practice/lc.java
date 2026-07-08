package practice;

import java.util.*;

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
 
    public static void main(String ar[])
    {
        int arr[]={1,23,43,5455,654,34};

        sliding_window(arr,2);
    }
}
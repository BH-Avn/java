package random;

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
}
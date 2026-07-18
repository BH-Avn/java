package practice;

//recursion
import java.util.*;
import static utils.Base.scanner;

class re_1 { // find number of 'a'
    int count(String s1) {
        if (s1.equals("")) {
            return 0;
        }
        return (s1.charAt(0) == 'a' ? 1 : 0) + count(s1.substring(1));
    }

    // palindrome string
    boolean palindrome(String s1) {
        if (s1.length() <= 1)
            return true;
        return s1.charAt(0) == s1.charAt(s1.length() - 1) && palindrome(s1.substring(1, s1.length() - 1));
    }

    // Tower of Hanoi
    void hanoi(int n, char st, char helper, char end) {
        if (n <= 1) {
            System.out.println("Move disk from " + st + "to " + end);
            return;
        }
        hanoi(n - 1, st, end, helper);
        System.out.println("Move disk " + n + "from " + st + "to " + end);
        hanoi(n - 1, helper, st, end);
    }

    // Runnable via Runner (java -cp bin practice.Runner --re_1). Not closing sc:
    // Runner keeps using System.in afterwards for its own "Run another?" prompt.
    public static void main(String[] args) {
        Scanner sc = scanner; // shared utils.Base.scanner - see Runner.java for why
        re_1 obj = new re_1();

        System.out.println("1. count      - count occurrences of 'a' in a string");
        System.out.println("2. palindrome - check if a string is a palindrome");
        System.out.println("3. hanoi      - print Tower of Hanoi moves");
        System.out.print("Choose a method: ");
        int choice = Integer.parseInt(sc.nextLine().trim());

        switch (choice) {
            case 1: {
                System.out.print("Enter string: ");
                String s = sc.nextLine();
                System.out.println("Count of 'a': " + obj.count(s));
                break;
            }
            case 2: {
                System.out.print("Enter string: ");
                String s = sc.nextLine();
                System.out.println("Is palindrome: " + obj.palindrome(s));
                break;
            }
            case 3: {
                System.out.print("Enter number of disks: ");
                int n = Integer.parseInt(sc.nextLine().trim());
                obj.hanoi(n, 'A', 'B', 'C');
                break;
            }
            default:
                System.out.println("Invalid choice.");
        }
    }
}

class sp {
    Scanner sc = scanner; // shared utils.Base.scanner - see Runner.java for why
    int ar[][];
    char arr[][];
    int size;
    StringBuilder s1;
    int r;
    int c;

    sp(int size) {
        this.size = size;
        arr = new char[size][size];
        ar = new int[size][size];
        s1 = new StringBuilder(size * 4);
    }

    sp(int r, int c) {
        this.r = r;
        this.c = c;
        arr = new char[r][c];
        s1 = new StringBuilder(size * 4);
    }

    void fill() {
        System.out.println("Enter Characters:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                arr[i][j] = Character.toUpperCase(sc.next().charAt(0));
            }
        }
    }

    void last() {
        for (int j = 0; j < size; j++) {
            int i = 0;
            s1.append(arr[i][j]);
        }
        for (int i = 1; i < size; i++) {
            int j = size - 1;
            s1.append(arr[i][j]);
        }
        for (int j = size - 2; j >= 0; j--) {
            int i = size - 1;
            s1.append(arr[i][j]);
        }
        for (int i = size - 2; i >= 1; i--) {
            int j = 0;
            s1.append(arr[i][j]);
        }
        String s2 = s1.toString();
        String s3 = s1.reverse().toString();
        if (s2.equals(s3)) {
            System.out.println("Palindromic Boundary");
        } else {
            System.out.println("Not Palindromic Boundary");
        }
    }

    void spiralPrintr() {
        int tr = 0, br = r, rc = 0, lc = c;
        while (tr < br && rc < lc) {
            for (int j = rc; j < lc; j++) {
                int i = tr;
                s1.append(arr[i][j]);
            }
            tr++;
            for (int i = tr; i < br; i++) {
                int j = lc - 1;
                s1.append(arr[i][j]);
            }
            lc--;
            if (rc < lc) {
                for (int j = lc - 1; j >= rc; j--) {
                    int i = br - 1;
                    s1.append(arr[i][j]);
                }
                br--;
            }
            if (tr < br) {
                for (int i = br - 1; i >= tr; i--) {
                    int j = rc;
                    s1.append(arr[i][j]);
                }
                rc++;
            }

        }
    }

    void rotate() {
        for (int i = 0; i < size; i++) {
            for (int j = i; j < size; j++) {
                int temp = ar[i][j];
                ar[i][j] = ar[j][i];
                ar[j][i] = temp;
            }
        }
        for (int i = 0; i < size; i++) {
            int k = size - 1;
            for (int j = 0; j < size / 2; j++) {
                int temp = ar[i][j];
                ar[i][j] = ar[i][k];
                ar[i][k--] = temp;
            }
        }
    }

    String reverseWordsInPlace(String sentence) {
        StringTokenizer ob1 = new StringTokenizer(sentence);
        StringBuffer s1 = new StringBuffer(sentence.length());
        int lastWord = 0;
        while (ob1.hasMoreTokens()) {
            s1.append(ob1.nextToken());
            int k = s1.length() - 1;
            for (int i = lastWord; i < k; i++, k--) {
                char temp = s1.charAt(i);
                s1.setCharAt(i, s1.charAt(k));
                s1.setCharAt(k, temp);
            }
            s1.append(" ");
            lastWord = s1.length();
        }
        return s1.toString().trim();
    }

    boolean spyNumber(int n) {

        int sum = 0;
        int mul = 1;
        int d;// saves memory
        while (n > 0) {
            d = n % 10;
            sum = sum + d;
            mul = mul * d;
            n /= 10;
        }
        return (sum == mul);
    }

    boolean isKaprekar(int n) {
        if (n < 0)
            return false;

        int sq = n * n;
        int cn = 0;
        int copy1 = n;
        while (copy1 > 0) {
            cn++;
            copy1 /= 10;
        }
        int divisor = (int) Math.pow(10, cn);
        return ((sq % divisor) + (sq / divisor) == n);
    }

    boolean isPrime(int p) {
        int c = 0;
        for (int i = 2; i * i <= p; i++) {
            if (p % i == 0) {
                c++;
            }
        }
        return c == 0;
    }

    boolean isCircularPrime(int n) {
        if (!isPrime(n) || n < 2)
            return false;

        int length = 0;
        int c1 = n;
        int c2 = n;
        int flag = 0;
        while (c1 > 0) {
            length++;
            c1 /= 10;
        }
        int a = (int) Math.pow(10, length - 1);
        for (int i = 1; i < length; i++) {
            int temp = c2 % 10;
            c2 = temp * a + c2 / 10;
            if (!isPrime(c2)) {

                flag++;
                break;
            }

        }
        return flag == 0;
    }

    String removeDup(String s1) {
        if (s1 == null || s1.equals(""))
            return "";

        StringBuffer sb1 = new StringBuffer();
        for (int i = 0; i < s1.length(); i++) {
            int lastIndex = sb1.length() - 1;
            char ch = s1.charAt(i);
            if (sb1.length() > 0 && sb1.charAt(lastIndex) == ch)
                sb1.deleteCharAt(lastIndex);
            else
                sb1.append(ch);
        }
        return sb1.toString();
    }

    void noRepeat(String s1) {
        if (s1 == null || s1.length() == 0)
            return;
        int g = 0;
        int lastIndex;
        StringBuffer sb1 = new StringBuffer();
        for (int i = 0; i < s1.length(); i++) {
            lastIndex = sb1.indexOf(String.valueOf(s1.charAt(i)));
            if (lastIndex == -1) {
                sb1.append(s1.charAt(i));
            } else {
                if (g < sb1.length())
                    g = sb1.length();
                sb1.delete(0, lastIndex + 1);
                sb1.append(s1.charAt(i));
            }
        }
        if (sb1.length() > g)
            g = sb1.length();
        System.out.println("Longest Substring=" + g);
    }

    void firstNoRepeat(String s1) // O(n^2)
    {
        if (s1 == null || s1.length() == 0)
            return;
        {
            for (int i = 0; i < s1.length(); i++) {
                if (s1.indexOf(s1.charAt(i)) == s1.lastIndexOf(s1.charAt(i))) {
                    System.out.println(i);
                    break;
                }
            }
        }
    }

    void firstNoRepeat2(String s1)// O (2n)
    {
        if (s1 == null || s1.length() == 0)
            return;

        int arr[] = new int[123];
        for (int i = 0; i < s1.length(); i++) {
            arr[s1.charAt(i)]++;
        }

        boolean found = false;
        for (int i = 0; i < s1.length(); i++) {
            char ch = s1.charAt(i);
            if (arr[ch] == 1) {
                System.out.println(ch);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No unique character");
        }
    }

    boolean Anagram(String s1, String s2) {

        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }
        if (s1.length() == 0)
            return true;

        int arr[] = new int[256];

        for (int i = 0; i < s1.length(); i++) {
            arr[s1.charAt(i)]++;
            arr[s2.charAt(i)]--;
        }

        for (int i = 0; i < 256; i++) {
            if (arr[i] != 0) {
                return false;
            }
        }
        return true;
    }

    boolean hiddenWord(String s1, String s2) {
        if (s1 == null || s2 == null || s2.length() > s1.length())
            return false;
        StringBuffer sb1 = new StringBuffer();
        sb1.append(s1);
        sb1.append(s1);
        if (sb1.indexOf(s2) != -1)
            return true;
        return false;
    }

    String stringcompression(String s1) {
        if (s1 == null || s1.length() == 0)
            return s1;
        StringBuffer sb1 = new StringBuffer();
        sb1.append(s1.charAt(0));
        int flag = 1;
        for (int i = 1; i < s1.length(); i++) {
            if (s1.charAt(i) == s1.charAt(i - 1)) {
                flag++;
            } else {
                sb1.append(flag);
                sb1.append(s1.charAt(i));
                flag = 1;
            }
        }
        sb1.append(flag);
        return (sb1.length() < s1.length()) ? sb1.toString() : s1;
    }

    // Runnable via Runner (java -cp bin practice.Runner --sp). sp has no no-arg
    // constructor by design, so it's only reachable through this static main().
    public static void main(String[] args) {
        Scanner sc = scanner; // shared utils.Base.scanner - see Runner.java for why

        System.out.println(" 1. fill                - fill an n x n grid, then check its border for a palindrome");
        System.out.println(" 2. spiralPrintr        - spiral-read an r x c grid of characters");
        System.out.println(" 3. rotate              - rotate an n x n int grid 90 degrees (demo grid is all zeros)");
        System.out.println(" 4. reverseWordsInPlace - reverse each word's letters in a sentence");
        System.out.println(" 5. spyNumber           - digit sum == digit product?");
        System.out.println(" 6. isKaprekar          - Kaprekar number check");
        System.out.println(" 7. isPrime             - primality check");
        System.out.println(" 8. isCircularPrime     - circular prime check");
        System.out.println(" 9. removeDup           - remove adjacent duplicate characters");
        System.out.println("10. noRepeat            - length of the longest substring without repeats");
        System.out.println("11. firstNoRepeat       - index of the first non-repeating character (O(n^2))");
        System.out.println("12. firstNoRepeat2      - first non-repeating character (O(n))");
        System.out.println("13. Anagram             - check if two strings are anagrams");
        System.out.println("14. hiddenWord          - check if s2 is a rotation of s1");
        System.out.println("15. stringcompression   - run-length compress a string");
        System.out.print("Choose a method: ");
        int choice = Integer.parseInt(sc.nextLine().trim());

        switch (choice) {
            case 1: {
                System.out.print("Enter grid size n: ");
                int n = Integer.parseInt(sc.nextLine().trim());
                sp obj = new sp(n);
                obj.fill();
                sc.nextLine(); // fill() reads with next(), which leaves the trailing newline unconsumed
                obj.last();
                break;
            }
            case 2: {
                System.out.print("Enter rows: ");
                int r = Integer.parseInt(sc.nextLine().trim());
                System.out.print("Enter columns: ");
                int c = Integer.parseInt(sc.nextLine().trim());
                sp obj = new sp(r, c);
                System.out.println("Enter characters:");
                for (int i = 0; i < r; i++) {
                    for (int j = 0; j < c; j++) {
                        obj.arr[i][j] = Character.toUpperCase(sc.next().charAt(0));
                    }
                }
                sc.nextLine(); // consume the trailing newline left after reading tokens
                obj.spiralPrintr();
                System.out.println("Spiral order: " + obj.s1.toString());
                break;
            }
            case 3: {
                System.out.print("Enter grid size n: ");
                int n = Integer.parseInt(sc.nextLine().trim());
                sp obj = new sp(n);
                obj.rotate();
                System.out.println("Rotated (all zeros - ar[][] was never filled in this demo).");
                break;
            }
            case 4: {
                System.out.print("Enter sentence: ");
                String s = sc.nextLine();
                sp obj = new sp(0);
                System.out.println("Reversed: " + obj.reverseWordsInPlace(s));
                break;
            }
            case 5: {
                System.out.print("Enter number: ");
                int n = Integer.parseInt(sc.nextLine().trim());
                sp obj = new sp(0);
                System.out.println("Is spy number: " + obj.spyNumber(n));
                break;
            }
            case 6: {
                System.out.print("Enter number: ");
                int n = Integer.parseInt(sc.nextLine().trim());
                sp obj = new sp(0);
                System.out.println("Is Kaprekar: " + obj.isKaprekar(n));
                break;
            }
            case 7: {
                System.out.print("Enter number: ");
                int n = Integer.parseInt(sc.nextLine().trim());
                sp obj = new sp(0);
                System.out.println("Is prime: " + obj.isPrime(n));
                break;
            }
            case 8: {
                System.out.print("Enter number: ");
                int n = Integer.parseInt(sc.nextLine().trim());
                sp obj = new sp(0);
                System.out.println("Is circular prime: " + obj.isCircularPrime(n));
                break;
            }
            case 9: {
                System.out.print("Enter string: ");
                String s = sc.nextLine();
                sp obj = new sp(0);
                System.out.println("Result: " + obj.removeDup(s));
                break;
            }
            case 10: {
                System.out.print("Enter string: ");
                String s = sc.nextLine();
                sp obj = new sp(0);
                obj.noRepeat(s);
                break;
            }
            case 11: {
                System.out.print("Enter string: ");
                String s = sc.nextLine();
                sp obj = new sp(0);
                obj.firstNoRepeat(s);
                break;
            }
            case 12: {
                System.out.print("Enter string: ");
                String s = sc.nextLine();
                sp obj = new sp(0);
                obj.firstNoRepeat2(s);
                break;
            }
            case 13: {
                System.out.print("Enter first string: ");
                String s1 = sc.nextLine();
                System.out.print("Enter second string: ");
                String s2 = sc.nextLine();
                sp obj = new sp(0);
                System.out.println("Are anagrams: " + obj.Anagram(s1, s2));
                break;
            }
            case 14: {
                System.out.print("Enter base string: ");
                String s1 = sc.nextLine();
                System.out.print("Enter word to find: ");
                String s2 = sc.nextLine();
                sp obj = new sp(0);
                System.out.println("Contains rotated word: " + obj.hiddenWord(s1, s2));
                break;
            }
            case 15: {
                System.out.print("Enter string: ");
                String s = sc.nextLine();
                sp obj = new sp(0);
                System.out.println("Compressed: " + obj.stringcompression(s));
                break;
            }
            default:
                System.out.println("Invalid choice.");
        }
    }
}

class school {
    void circular_Trick(int n)// cow filler program
    {
        int arr[] = new int[2 * n];
        for (int i = 0; i < n; i++) {
            int temp1 = i * n;
            arr[i] = temp1;
            arr[i + n] = temp1;
        }
        for (int i = 0; i < n; i++) {
            System.out.print("Child " + i + 1 + " gets the cows numbered: ");
            for (int j = 0; j < n; j++) {
                System.out.print(arr[j + i] + j + 1 + "\t");
            }
            System.out.println();
        }
    }

    boolean symetric_array(int[][] arr) {
        int n = arr[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int temp = arr[i][j];
                arr[i][j] = arr[j][n - 1 - i];
                arr[j][n - 1 - i] = temp;
            }
        }

        return false;

    }

    public boolean isPalindrome(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0))
            return false;
        if (x / 10 == 0)
            return true;
        int rev = 0;
        while (x > rev) {
            rev = (10 * (rev) + x % 10);
            x /= 10;
        }
        return x == rev || rev / 10 == x;
    }

    int[][] multiply(int arr1[][], int arr2[][]) {
        if (arr1 == null || arr2 == null || arr2.length == 0 || arr1.length == 0) {
            System.out.print("Error");
            return null;
        }
        if (arr1[0].length != arr2.length) {
            System.out.println("Number of Rows and Columns do not match !");
            return null;
        }
        int r1 = arr1.length;
        int r2 = arr2.length;
        int c2 = arr2[0].length;
        int[][] mul = new int[r1][c2];
        int sum = 0;
        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                for (int k = 0; k < r2; k++) {
                    sum += (arr1[i][k] * arr2[k][j]);
                }
                mul[i][j] = sum;
                sum = 0;
            }

        }
        return mul;
    }

    // Runnable via Runner (java -cp bin practice.Runner --school).
    public static void main(String[] args) {
        Scanner sc = scanner; // shared utils.Base.scanner - see Runner.java for why
        school obj = new school();

        System.out.println("1. circular_Trick - cow-distribution demo for n children");
        System.out.println("2. symetric_array - transpose-style shuffle of a square int matrix");
        System.out.println("3. isPalindrome   - check if an int reads the same forwards and backwards");
        System.out.println("4. multiply       - multiply two int matrices");
        System.out.print("Choose a method: ");
        int choice = Integer.parseInt(sc.nextLine().trim());

        switch (choice) {
            case 1: {
                System.out.print("Enter n: ");
                int n = Integer.parseInt(sc.nextLine().trim());
                obj.circular_Trick(n);
                break;
            }
            case 2: {
                System.out.print("Enter matrix size n: ");
                int n = Integer.parseInt(sc.nextLine().trim());
                int[][] arr = readMatrix(sc, n, n);
                obj.symetric_array(arr);
                System.out.println("Result:");
                printMatrix(arr);
                break;
            }
            case 3: {
                System.out.print("Enter number: ");
                int x = Integer.parseInt(sc.nextLine().trim());
                System.out.println("Is palindrome: " + obj.isPalindrome(x));
                break;
            }
            case 4: {
                System.out.print("Enter rows and columns of matrix A (e.g. 2 3): ");
                String[] dimsA = sc.nextLine().trim().split("\\s+");
                int[][] a = readMatrix(sc, Integer.parseInt(dimsA[0]), Integer.parseInt(dimsA[1]));

                System.out.print("Enter rows and columns of matrix B (e.g. 3 2): ");
                String[] dimsB = sc.nextLine().trim().split("\\s+");
                int[][] b = readMatrix(sc, Integer.parseInt(dimsB[0]), Integer.parseInt(dimsB[1]));

                System.out.println("Result:");
                printMatrix(obj.multiply(a, b));
                break;
            }
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static int[][] readMatrix(Scanner sc, int rows, int cols) {
        System.out.println("Enter " + rows + "x" + cols + " matrix, row by row (space-separated):");
        int[][] m = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            String[] parts = sc.nextLine().trim().split("\\s+");
            for (int j = 0; j < cols; j++) {
                m[i][j] = Integer.parseInt(parts[j]);
            }
        }
        return m;
    }

    private static void printMatrix(int[][] m) {
        if (m == null) {
            System.out.println("(null)");
            return;
        }
        for (int[] row : m) {
            System.out.println(Arrays.toString(row));
        }
    }
}

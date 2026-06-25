package random;

//recursion 
import java.util.*;

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
}

class sp {
    Scanner sc = new Scanner(System.in);
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
}

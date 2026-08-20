package practice;

import java.util.Scanner;

public class raju {
    Scanner sc = new Scanner(System.in);

    int n;

    void main() {

        // System.out.println("Enter Number of Initial Terms:");
        // n = sc.nextInt();
        // padovan(n);

        // hcf(12,18);

        int arr[][] = {
                { 11, 2, 9 },
                { 4, 3, 6 },
                { 7, 1, 9 }
        };

        saddlepoint(arr, arr.length, arr[0].length);

    }

    /*
     * n chars input
     * 
     * 3
     * a
     * b
     * c
     * 
     * ab -> a + b
     * bc -> b + c
     * cab -> c + ab
     * abbc -> ab + bc
     * cab-abbc
     * 
     * 
     * 
     * 
     */

    void padovan(int n) {
        System.out.println("Enter Total Terms to be printed:");
        int t = sc.nextInt();

        String ar[] = new String[(t > n) ? t : n]; // explain

        System.out.println("Enter Initial Terms:");
        for (int i = 0; i < n; i++) {
            ar[i] = sc.next();
        }

        /*
         * 3
         * 10
         * 
         * ar={a , b , c , a+b , b+c , c+a+b , - , - , - , -} n=3
         * 
         * 0 1 2 3 4 5 6 7 8 9
         */
        System.out.println("Padovan Sequecne:");
        for (int i = n; i < t - 1; i++) {
            ar[i] = ar[i - n] + ar[i - n + 1];
            System.out.println(ar[i]);
        }

    }

    int hcf(int a, int b) {
        if (b == 0)
            return a;
        return hcf(b, a % b);
    }

    void saddlepoint(int arr[][], int rows, int cols) {

        for (int i = 0; i < rows; i++) {

            int min = arr[i][0];
            int minCol = 0;

            for (int j = 1; j < cols; j++) {
                if (arr[i][j] < min) {
                    min = arr[i][j];
                    minCol = j;
                }
            }

            boolean isMax = true;
            for (int k = 0; k < rows; k++) {
                if (arr[k][minCol] > min) {
                    isMax = false;
                    break;
                }
            }

            if (isMax) {
                System.out.println("Saddle point: " + min + " at (" + i + "," + minCol + ")");
            }
        }

    }

}

// aviral is dumb!!!
// 11  2  9
// 4   3  6
// 7   1  9
package practice;

import java.util.Scanner;

class digitToWord {
    String l1[] = { "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine" };
    String l2[] = { "", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety" };
    String l3[] = { "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen" };

    String ones(int n) {
        return " " + l1[n];
    }

    String tens(int n) {
        if (n < 20)
            return " " + l3[n - 10];
        return " " + l2[n / 10] + ones(n % 10);
    }

    String hundred(int n) {
        return " " + l1[n / 100] + " Hundred" + selector(n % 100);
    }

    String thousand(int n) {
        return " " + l1[n / 1000] + " Thousand" + selector(n % 1000);
    }

    String ten_Thousand(int n) {
        return tens(n / 1000) + " Thousand" + selector(n % 1000);
    }

    String lakh(int n) {
        return " " + l1[n / 100000] + " Lakh" + selector(n % 100000);
    }

    String ten_lakh(int n) {
        return tens(n / 100000) + " Lakh" + selector(n % 100000);
    }

    String crore(int n) {
        return " " + l1[n / 10000000] + " Crore" + selector(n % 10000000);
    }

    String ten_crore(int n) {
        return tens(n / 10000000) + " Crore" + selector(n % 10000000);
    }

    String selector(int n) {
        if (n > 99999999)
            return ten_crore(n);
        else if (n > 9999999)
            return crore(n);
        else if (n > 999999)
            return ten_lakh(n);
        else if (n > 99999)
            return lakh(n);
        else if (n > 9999)
            return ten_Thousand(n);
        else if (n > 999)
            return thousand(n);
        else if (n > 99)
            return hundred(n);
        else if (n > 9)
            return tens(n);
        else
            return ones(n);
    }

}

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int n = sc.nextInt();

        digitToWord obj = new digitToWord();
        String result;

        if (n == 0)
            result = "Zero";
        else if (n < 0)
            result = "Negative.." + obj.selector(-n);
        else
            result = obj.selector(n);

        System.out.println(result.trim());
        sc.close();
    }
}
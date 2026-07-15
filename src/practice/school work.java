package practice;

import java.util.Scanner;

class digitToWord {
    String l1[] = { "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine" };
    String l2[] = { "", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety" };
    String l3[] = { "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen",
            "Nineteen" };

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



 class NumberToWords {
     String[] ones = { "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine" };
     String[] teens = { "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen",
            "Eighteen", "Nineteen" };
     String[] tens = { "", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety" };

     String twoDigit(int num) {
        if (num == 0)
            return "";
        if (num < 10)
            return ones[num];
        if (num < 20)
            return teens[num - 10];
        return (tens[num / 10] + " " + ones[num % 10]).trim();
    }

     String threeDigit(int num) {
        StringBuilder sb = new StringBuilder();
        if (num >= 100) {
            sb.append(ones[num / 100]).append(" Hundred ");
            num %= 100;
        }
        sb.append(twoDigit(num));
        return sb.toString().trim();
    }

     String convert(long num) {
        if (num == 0)
            return "Zero";

        long crore = num / 10000000;
        num %= 10000000;
        long lakh = num / 100000;
        num %= 100000;
        long thousand = num / 1000;
        num %= 1000;
        long hundred = num;

        StringBuilder result = new StringBuilder();

        if (crore > 0)
            result.append(threeDigit((int) crore)).append(" Crore ");
        if (lakh > 0)
            result.append(twoDigit((int) lakh)).append(" Lakh ");
        if (thousand > 0)
            result.append(twoDigit((int) thousand)).append(" Thousand ");
        if (hundred > 0)
            result.append(threeDigit((int) hundred));

        return result.toString().trim();
    }

    
}

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Choose a converter:");
        System.out.println("1. digitToWord (recursive)");
        System.out.println("2. NumberToWords (iterative)");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        System.out.print("Enter a number: ");
        long n = sc.nextLong();

        String result;

        if (choice == 2) {
            NumberToWords obj = new NumberToWords();
            result = (n < 0) ? "Negative.." + obj.convert(-n) : obj.convert(n);
        } else {
            digitToWord obj = new digitToWord();
            int num = (int) n;
            if (num == 0)
                result = "Zero";
            else if (num < 0)
                result = "Negative.." + obj.selector(-num);
            else
                result = obj.selector(num);
        }

        System.out.println(result.trim());
        sc.close();
    }
}
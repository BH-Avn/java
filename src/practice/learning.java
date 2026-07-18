package practice;


import java.util.*;
import static utils.Base.scanner;

class InkiPinkiPonki {

    void chem(int n) {

        int ar[] = { 2, 8, 18, 32, 50, 72 };

        for (int i = 0; i < ar.length; i++) {
            if (n < 1)
                break;
            if (n >= ar[i]) {
                System.out.println(ar[i] + "\t");
                n -= ar[i];
            } else {
                System.out.println(n);
                break;
            }
        }
       
    }


    

    // Runnable via Runner (java -cp bin practice.Runner --InkiPinkiPonki).
    public static void main(String args[]) {
        Scanner sc = scanner; // shared utils.Base.scanner - see Runner.java for why
        InkiPinkiPonki obj = new InkiPinkiPonki();

        System.out.println("1. chem - electron shell filling demo");
        System.out.print("Choose a method: ");
        int choice = Integer.parseInt(sc.nextLine().trim());

        switch (choice) {
            case 1: {
                System.out.print("Enter number of electrons: ");
                int n = Integer.parseInt(sc.nextLine().trim());
                obj.chem(n);
                break;
            }
            default:
                System.out.println("Invalid choice.");
        }
    }
}
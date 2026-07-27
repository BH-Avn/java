package practice;

import java.util.Scanner;

public class raju {
    Scanner sc = new Scanner(System.in);
    int n;

   public void run()
    {
        main();
        
    }

    void main() {
        System.out.println("Raju is pro");
        System.out.println("Enter Number:");
        n = sc.nextInt();
        isprime(2);
    }
/*
n==7
prime(2)
false           
prime

*/
    boolean isprime(int i) {
        if (i < 2) 
            return false;
        if (n % i == 0)
            return false;

        return (i >= n) ? true : isprime(i + 1);
    }



 
 



 
}
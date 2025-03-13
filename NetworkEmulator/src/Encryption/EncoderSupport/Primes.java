package Encryption.EncoderSupport;

import java.util.Scanner;

/**
 * Queries input and determines if it is prime
 */
public class Primes {
    public int nextPrime(Scanner s){
        int prime = -1;
        boolean notPrime = true;
        while(notPrime) {
            int i = s.nextInt();
            if(i == 2 || i == 3) return prime;
            for (int x = 2; x <= Math.sqrt(i); x++) {
                if (i % x == 0) {
                    notPrime = true;
                    System.out.println("Number is not prime");
                    System.out.print("Insert prime: ");
                    break;
                }
                notPrime = false;
            }
            prime = i;
        }
        return prime;
    }
}
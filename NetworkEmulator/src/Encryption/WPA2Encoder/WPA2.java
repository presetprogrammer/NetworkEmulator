package Encryption.WPA2Encoder;

import Encryption.EncoderSupport.*;

import java.math.BigInteger;
import java.util.Scanner;

public class WPA2{
    public static void useWPA2(Scanner userin) throws InterruptedException{
        boolean first = true;
        WordArt wa = new WordArt();
        wa.printWPA2();
        System.out.println("Welcome to WPA2 Emulator!");
        Thread.sleep(500);
        System.out.println("Here are the available options:");
        Thread.sleep(1500);
        //Options
        System.out.println("Simulate Handshake (h)");
        Thread.sleep(500);
        System.out.println("Encrypt (e)");
        Thread.sleep(500);
        System.out.println("Decrypt (d)");
        Thread.sleep(500);
        System.out.println("Exit (q)");
        Thread.sleep(500);
        System.out.print("Please select an option: ");
        while(true){
            if(!first){
                System.out.println("Simulate Handshake (h)");
                System.out.println("Encrypt (e)");
                System.out.println("Decrypt (d)");
                System.out.println("Exit (q)");
                Thread.sleep(250);
                System.out.print("Please select an option: ");
            }
            switch(userin.nextLine().toLowerCase()){
                case "simulate":
                case "handshake":
                case "h":
                    BigInteger handshake = WPA2Protocol.handshake();
                    System.out.println("Handshake Test: " + handshake);
                    break;
                case "encrypt":
                case "e":
                    BigInteger encrypt = WPA2Protocol.encrypt();
                    System.out.println("Encrypt Test: " + encrypt);
                    break;
                case "decrypt":
                case "d":
                    BigInteger decrypt = WPA2Protocol.decrypt();
                    System.out.println("Decrypt Test: " + decrypt);
                    break;
                case "exit":
                case "q":
                    return;
                default:
                    System.out.println("Statement not recognized.\n");
            }
            first = false;
            Thread.sleep(1000);
        }
    }
}

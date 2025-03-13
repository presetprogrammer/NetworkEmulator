package Encryption.AESEncoder;

import Encryption.EncoderSupport.WordArt;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

public class AES{
    public static void useAES(Scanner userin) throws InterruptedException{
        boolean first = true;
        WordArt wa = new WordArt();
        wa.printAES();
        System.out.println("Welcome to AES Emulator!");
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
                    BigInteger handshake = AESProtocol.handshake();
                    System.out.println("Handshake Test: " + handshake);
                    break;
                case "encrypt":
                case "e":
                    System.out.print("File name: ");
                    String f = userin.nextLine();
                    File file = new File(f);
                    try{
                        AESProtocol.encrypt(file);
                    }
                    catch (IOException ioe){
                        System.out.println("File not found (searches at " + file.getAbsolutePath() + ").");
                    }
                    break;
                case "decrypt":
                case "d":
                    System.out.print("File name: ");
                    f = userin.nextLine();
                    file = new File(f);
                    try{
                        AESProtocol.decrypt(file);
                    }
                    catch(IOException ioe){
                        System.out.println("File not found (searches at " + file.getAbsolutePath() + ").");
                    }
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
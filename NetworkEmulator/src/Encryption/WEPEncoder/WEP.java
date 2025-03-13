package Encryption.WEPEncoder;

import Encryption.EncoderSupport.RC4;
import Encryption.EncoderSupport.WordArt;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * WPS Simulator Class. Contains Broad "useWEP" method to run WPS and RC4 from 'EncoderSupport' for encryption.
 */
public class WEP {
    public static void useWEP(Scanner userin) throws InterruptedException, IOException {
        boolean first = true;
        WordArt wa = new WordArt();
        wa.printWEP();
        System.out.println("Welcome to WEP Emulator!");
        Thread.sleep(500);
        System.out.println("Here are the available options:");
        Thread.sleep(1500);
        //Options
        System.out.println("Encrypt (e)");
        Thread.sleep(500);
        System.out.println("Decrypt (d)");
        Thread.sleep(500);
        System.out.println("Exit (q)");
        Thread.sleep(500);
        System.out.print("Please select an option: ");
        while(true){
            if (!first){
                System.out.println("Encrypt (e)");
                System.out.println("Decrypt (d)");
                System.out.println("Exit (q)");
                Thread.sleep(250);
                System.out.print("Please select an option: ");
            }
            File file;
            switch (userin.nextLine().toLowerCase()) {
                //ENCRYPT
                case "encrypt":
                case "e":
                    System.out.print("Name of File: ");
                    file = new File(userin.nextLine());
                    Byte[] keyset = RC4.encrypt(file);
                    StringBuilder keystring = new StringBuilder();
                    keystring.append('{');
                    for(int i : keyset){
                        keystring.append(i);
                        keystring.append(", ");
                    }
                    keystring.replace(keystring.length()-2, keystring.length(), "}");
                    StringSelection clip = new StringSelection(keystring.toString());
                    //Sleep
                    System.out.print('.');
                    Thread.sleep(500);
                    System.out.print('.');
                    Thread.sleep(500);
                    System.out.print('.');
                    Thread.sleep(500);
                    System.out.println();

                    System.out.println("Successfully encrypted " + file.getName());
                    System.out.println("Key: " + Arrays.toString(keyset));
                    Thread.sleep(250);
                    System.out.print("Copy to clipboard? (y/n): ");
                    if(userin.next().charAt(0) == 'y'){
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(clip, clip);
                        System.out.println("Copied to clipboard!");
                    }
                    break;
                //DECRYPT
                case "decrypt":
                case "d":
                    System.out.print("Name of File: ");
                    file = new File(userin.nextLine());
                    System.out.print("Decrypt PIN: ");
                    String pinset = userin.nextLine();
                    Byte[] pin = new Byte[pinset.length()];
                    for(int a = 0; a < pinset.length(); a++){
                        pin[a] = (byte) pinset.charAt(a);
                    }
                    RC4.decrypt(file, new ArrayList<>(Arrays.asList(pin)));
                    //Sleep
                    System.out.print('.');
                    Thread.sleep(500);
                    System.out.print('.');
                    Thread.sleep(500);
                    System.out.print('.');
                    Thread.sleep(500);
                    System.out.println();

                    System.out.println("Successfully decrypted " + file.getName());
                    break;
                case "exit":
                case "q":
                    return;
            }
            first = false;
            Thread.sleep(1000);
        }
    }
}
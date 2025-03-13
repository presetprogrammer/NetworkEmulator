package Encryption.WPA3Encoder;

import Encryption.EncoderSupport.*;
import Networking.IPv4;
import Networking.IPv6;
import Networking.Supporting.IllegalAddressException;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;
import static Encryption.EncoderSupport.IntegerPlay.getIntegers;

public class WPA3{
    public static void useWPA3(Scanner userin) throws InterruptedException, FileNotFoundException, IllegalAddressException {
        boolean first = true;
        WordArt wa = new WordArt();
        InputType it = null;
        boolean input;

        wa.printWPA3();
        System.out.println("Welcome to WPA3 Emulator!");
        Thread.sleep(500);
        System.out.println("Here are the available options:");
        Thread.sleep(1500);
        //Options
        System.out.println("Simulate Dragonfly Handshake (h)");
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
                System.out.println("Simulate Dragonfly Handshake (h)");
                System.out.println("Encrypt (e)");
                System.out.println("Decrypt (d)");
                System.out.println("Exit (q)");
                Thread.sleep(250);
                System.out.print("Please select an option: ");
            }
            switch (userin.nextLine().toLowerCase()){
                //SIMULATE DRAGONFLY HANDSHAKE
                case "simulate":
                case "handshake":
                case "h":
                    System.out.print("Enter Host Address (IPv4 or IPv6: ");
                    String hostin = userin.nextLine();
                    IPv4 host4 = null;
                    IPv6 host6 = null;
                    if(hostin.indexOf(':') != -1){
                        host6 = IPv6.toIPv6(hostin);
                    }
                    else if(hostin.indexOf('.') != 0) host4 = IPv4.toIPv4(hostin);
                    else {
                        throw new IllegalAddressException("Illegal Address. Format for IPv4 or IPv6");
                    }
                        for(int x = 0; x < 4; x++) {
                            //Validate
                            StringBuilder sb = new StringBuilder();
                            for (int t = 0; t < hostin.length(); t++) {
                                char a = hostin.charAt(t);
                                if (!Character.isDigit(a) && (a != '.')) {
                                    System.out.println("Expected Integer or '.'");
                                    break;
                                } else if (Character.isDigit(a)) sb.append(a);
                                else {
                                    int check = Integer.parseInt(sb.toString());
                                    if (check > 255) {
                                        System.out.println(check + " is greater than 255.");
                                        break;
                                    }
                                }
                            }
                        }
                    System.out.print("Enter Target Address (IPv4 or IPv6: ");
                    String targetin = userin.nextLine();
                    IPv4 target4 = null;
                    IPv6 target6 = null;
                    if(targetin.indexOf(':') != -1) target6 = IPv6.toIPv6(targetin);
                    else if(targetin.indexOf('.') != -1) target4 = IPv4.toIPv4(targetin);
                    //Dragonfly
                    if(host6 == null){
                        if(target6 == null){
                            Dragonfly.Simulate(host4, target4);
                        }
                        else{
                            Dragonfly.Simulate(host4, target6);
                        }
                    }
                    else{
                        if(target6 == null){
                            Dragonfly.Simulate(host6, target4);
                        }
                        else{
                            Dragonfly.Simulate(host6, target6);
                        }
                    }
                    break;
                case "encrypt":
                case "e":
                    BigInteger encrypt;
                    input = true;
                    while(input){
                        switch (userin.nextLine().toLowerCase()){
                            case "file" -> {it = InputType.FILE; input = false;}
                            case "text" -> {it = InputType.TEXT; input = false;}
                            case "integer" -> {it = InputType.INTEGER; input = false;}
                            case "packet" -> {it = InputType.PACKAGE; input = false;}
                            default -> System.out.println("Statement not recognized. Use \"file\", \"text\", or \"integer\".");
                        }
                    }
                    switch(it){
                        case FILE -> {
                            System.out.print("Text file name (Example file is example.txt): ");
                            File file = new File(userin.next());
                            Scanner filein = new Scanner(file);
                            WPA3Protocol.encrypt(filein);
                            System.out.println("Successfully encrypted " + file.getName());
                        }
                        case TEXT -> {
                            System.out.print("Insert string sequence: ");
                            String text = userin.nextLine();
                            encrypt = WPA3Protocol.encrypt(text);
                            System.out.println("Successfully encrypted " + encrypt);
                        }
                        case INTEGER -> {
                            System.out.print("Insert integer sequence");
                            String typeint = userin.nextLine();
                            ArrayList<BigInteger> intin = getIntegers(typeint);
                            encrypt = WPA3Protocol.encrypt(intin);
                            System.out.println("Successfully encrypted " + encrypt);
                        }
                        case PACKAGE -> {
                            System.out.print("Package file name (Example file is example.pkg");
                            File file = new File(userin.next());
                            Scanner filein = new Scanner(file);
                            WPA3Protocol.encrypt(filein);
                            System.out.println("Successfully encrypted" + file.getName());
                        }
                    }
                    break;
                //DECRYPT
                case "decrypt":
                case "d":

                    BigInteger decrypt;
                    input = true;
                    while(input){
                        switch (userin.nextLine().toLowerCase()){
                            case "file" -> {it = InputType.FILE; input = false;}
                            case "text" -> {it = InputType.TEXT; input = false;}
                            case "integer" -> {it = InputType.INTEGER; input = false;}
                            case "packet" -> {it = InputType.PACKAGE; input = false;}
                            default -> System.out.println("Statement not recognized. Use \"file\", \"text\", or \"integer\".");
                        }
                    }
                    switch(it){
                        case FILE -> {
                            System.out.print("Text file name (Example file is example.txt): ");
                            File file = new File(userin.next());
                            Scanner filein = new Scanner(file);
                            WPA3Protocol.decrypt(filein);
                            System.out.println("Successfully decrypted " + file.getName());
                        }
                        case TEXT -> {
                            System.out.print("Insert string sequence: ");
                            String text = userin.nextLine();
                            decrypt = WPA3Protocol.decrypt(text);
                            System.out.println("Successfully decrypted " + decrypt);
                        }
                        case INTEGER -> {
                            System.out.println("Insert integer sequence");
                            String typeint = userin.nextLine();
                            ArrayList<BigInteger> intin = getIntegers(typeint);
                            decrypt = WPA3Protocol.decrypt(intin);
                            System.out.println("Successfully decrypted " + decrypt);
                        }
                        case PACKAGE -> {
                            System.out.print("Enter packet file name (Example file is example.pkg");
                            File file = new File(userin.next());
                            Scanner packetin = new Scanner(file);
                            WPA3Protocol.decrypt(packetin);
                            System.out.println("Successfully decrypted" + file.getName());
                        }
                    }
                    break;
                //RETURN TO APPLICATION HOME PAGE
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

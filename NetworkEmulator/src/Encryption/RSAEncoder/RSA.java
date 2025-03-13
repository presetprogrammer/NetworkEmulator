package Encryption.RSAEncoder;

import Encryption.EncoderSupport.*;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

import static Encryption.EncoderSupport.IntegerPlay.*;

public class RSA{
    static RSAProtocol rsaProtocol = new RSAProtocol();
    static WordArt wa = new WordArt();
    public static void useRSA(Scanner userin) throws IOException, InterruptedException{
        InputType it = null;
        Primes primes = new Primes();
        BigInteger e = BigInteger.valueOf(0);
        BigInteger d = BigInteger.valueOf(0);
        BigInteger n = BigInteger.valueOf(0);
        boolean encr_bug = false;
        wa.printRSA();
        System.out.println("\n\nWelcome to RSAEncoder.RSAProtocol Simulator");
        Thread.sleep(1500);
        while(true){
            System.out.println("Encrypt (e)\nDecrypt (d) \nExit (q)");
            System.out.print("Please select an option: ");
            if(encr_bug){
                encr_bug = false;
                userin.nextLine();
            }
            String enc_dec;
            enc_dec = userin.nextLine();
            File f;
            ArrayList<ArrayList<BigInteger>> al;
            FileWriter fw;
            if (enc_dec.equalsIgnoreCase("e") || enc_dec.equalsIgnoreCase("encrypt")) {
                //Assign primes q and p
                System.out.print("Insert first prime number: ");
                BigInteger p = BigInteger.valueOf(primes.nextPrime(userin));
                System.out.print("Insert second prime number: ");
                BigInteger q = BigInteger.valueOf(primes.nextPrime(userin));
                n = p.multiply(q);
                BigInteger n2 = (p.subtract(BigInteger.valueOf(1))).multiply(q.subtract(BigInteger.valueOf(1)));
                boolean test = false;
                //random e s.t. gcd(n2, e) = 1
                while (!test) {
                    e = BigInteger.valueOf((long) (Math.random() * 12395 + 324));
                    test = gcd(n2, e).equals(BigInteger.valueOf(1));
                }
                test = false;
                //random d s.t. 1 = exd mod(n2)
                while (!test) {
                    d = BigInteger.valueOf((long) (Math.random() * 10003 + 285));
                    test = (BigInteger.valueOf(1).equals((e.multiply(d)).mod(n2)));
                }

                //Encryption via File, String, or Int
                String typeint;
                String typestring;
                fw = new FileWriter(".missing");
                Scanner filein;
                System.out.print("\nFile, Text, or Integer?: ");
                boolean input = true;
                while (input) {
                    switch (userin.nextLine().toLowerCase()){
                        case "file" -> {it = InputType.FILE; input = false;}
                        case "text" -> {it = InputType.TEXT; input = false;}
                        case "integer" -> {it = InputType.INTEGER; input = false;}
                        default -> System.out.println("Statement not recognized. Use \"file\", \"text\", or \"integer\".");
                    }
                }
                if (it == InputType.FILE) {
                    System.out.print("Enter file name (example file is example.txt): ");
                    f = new File(userin.nextLine());
                    filein = new Scanner(f);
                    al = rsaProtocol.encrypt(filein, e, n);
                } else if (it == InputType.TEXT) {
                    f = new File("");
                    System.out.print("Enter text: ");
                    typestring = userin.nextLine();
                    al = rsaProtocol.encrypt(typestring, e, n);
                } else {
                    f = new File("");
                    System.out.print("Enter Integer: ");
                    typeint = userin.nextLine();
                    ArrayList<BigInteger> intin = getIntegers(typeint);
                    al = rsaProtocol.encrypt(intin, e, n);
                }

                if (!f.getName().isEmpty()) {
                    if (f.getName().equals(".missing")) {
                        System.out.println("File not found/accepted.");
                        System.exit(1);
                    }
                    try {
                        fw = new FileWriter(f);
                    } catch (IOException ignored) {
                    }
                    for (ArrayList<BigInteger> i : al) {
                        char c = (char) i.get(0).intValue();
                        fw.write(c);
                    }
                }
                fw.close();

                //Sleep
                System.out.print('.');
                Thread.sleep(500);
                System.out.print('.');
                Thread.sleep(500);
                System.out.print('.');
                Thread.sleep(500);
                System.out.println();
                if (it == InputType.FILE) {
                    System.out.println("Successfully encrypted " + "'" + f.getName() + "'");
                } else if (it == InputType.INTEGER) {
                    StringBuilder sb = new StringBuilder();
                    for (ArrayList<BigInteger> intlist : al) {
                        sb.append(intlist.get(0));
                    }
                    System.out.println("Successfully encrypted " + "'" + sb + "'");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (ArrayList<BigInteger> charlist : al) {
                        sb.append((char) charlist.get(0).intValue());
                    }
                    System.out.println("Successfully encrypted " + "'" + sb + "'");
                }
                System.out.println();
                System.out.println("Private key: [" + d + "," + n + "]");
                Thread.sleep(1000);
                System.out.print("Do you want to copy the key to clipboard? (y/n): ");
                String copy = userin.next();
                if(copy.equalsIgnoreCase("y")){
                    String keyset = "["+ d + "," + n + "]";
                    StringSelection set = new StringSelection(keyset);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(set, set);
                    System.out.println("Copied to clipboard!");
                }
                encr_bug = true;
                Thread.sleep(1500);
                System.out.println("\n");
            }
            //Decryption
            else if (enc_dec.equalsIgnoreCase("d") || enc_dec.equalsIgnoreCase("decrypt")) {
                String typeint;
                String typestring;
                fw = new FileWriter(".missing");
                Scanner filein;
                System.out.print("Insert decrypt key: ");
                String key = userin.nextLine();
                key = key.replaceAll("\\s", "");
                //check key format
                boolean decr = true;
                while(decr) {
                    boolean keytest = true;
                    try {
                        BigInteger.valueOf(Integer.parseInt(key.substring(1, key.indexOf(','))));
                        BigInteger.valueOf(Integer.parseInt(key.substring(key.indexOf(',') + 1, key.length() - 1)));
                    } catch (NumberFormatException | IndexOutOfBoundsException nfe) {
                        keytest = false;
                    }
                    if (key.charAt(0) != '[' || !keytest || key.charAt(key.length()-1) != ']') {
                        System.out.print("Improper format. Please enter key as [p, d] or [p,d]: ");
                        key = userin.nextLine().replaceAll("\\s", "");
                    }
                    else{
                        d = BigInteger.valueOf(Integer.parseInt(key.substring(1, key.indexOf(','))));
                        n = BigInteger.valueOf(Integer.parseInt(key.substring(key.indexOf(',')+1, key.length() - 1)));
                        decr = false;
                    }
                }
                System.out.print("\nFile, Text, or Integer?: ");
                boolean input = true;
                while (input) {
                    switch (userin.nextLine().toLowerCase()){
                        case "file"->{it = InputType.FILE; input = false;}
                        case "text"->{it = InputType.TEXT; input = false;}
                        case "integer"->{it = InputType.INTEGER; input = false;}
                        default -> System.out.println("Statement not recognized. Use \"file\", \"text\", or \"integer\": ");
                    }
                }
                if (it == InputType.FILE) {
                    System.out.print("Enter file name (example file is example.txt): ");
                    f = new File(userin.nextLine());
                    filein = new Scanner(f);
                    al = rsaProtocol.decrypt(filein, d, n);
                } else if (it == InputType.TEXT) {
                    f = new File("");
                    System.out.print("Enter text: ");
                    typestring = userin.nextLine();
                    al = rsaProtocol.decrypt(typestring, d, n);
                } else {
                    f = new File("");
                    System.out.print("Enter Integer: ");
                    typeint = userin.nextLine();
                    ArrayList<BigInteger> intin = getIntegers(typeint);
                    al = rsaProtocol.decrypt(intin, d, n);
                }
                if (!f.getName().isEmpty()) {
                    if (f.getName().equals(".missing")) {
                        System.out.println("File not found/accepted.");
                        System.exit(1);
                    }
                    try {
                        fw = new FileWriter(f);
                    } catch (IOException ignored) {
                    }
                    for (ArrayList<BigInteger> i : al) {
                        char c = (char) i.get(0).intValue();
                        fw.write(c);
                    }
                }
                fw.close();

                System.out.print('.');
                Thread.sleep(500);
                System.out.print('.');
                Thread.sleep(500);
                System.out.print('.');
                Thread.sleep(500);
                System.out.println();
                if (it == InputType.FILE) {
                    System.out.println("Successfully decrypted " + "'" + f.getName() + "'");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (ArrayList<BigInteger> intlist : al) {
                        sb.append((it == InputType.TEXT) ? (char) intlist.get(0).intValue() : intlist.get(0));
                    }
                    System.out.println("Successfully decrypted " + "'" + sb + "'");
                }
                Thread.sleep(2000);
                System.out.println("\n");
            }
            else if(enc_dec.equalsIgnoreCase("exit") || enc_dec.equalsIgnoreCase("q")){
                return;
            }
        }
    }
}
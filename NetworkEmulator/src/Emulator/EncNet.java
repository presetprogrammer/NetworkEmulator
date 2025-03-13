//Networking
package Emulator;

import Encryption.WEPEncoder.WEP;
import Encryption.WPAEncoder.WPA;
import Encryption.WPA2Encoder.WPA2;
import Encryption.WPA3Encoder.WPA3;
//File
import Encryption.RSAEncoder.RSA;
import Encryption.DESEncoder.DES;
import Encryption.AESEncoder.AES;
//Expanded Library
import java.io.IOException;
import java.util.Scanner;

import Networking.Supporting.IllegalAddressException;
import org.jetbrains.annotations.NotNull;

public class EncNet {
    public static void runEmulator(@NotNull Scanner userin, @NotNull String env) throws InterruptedException, IOException, IllegalAddressException {
        System.out.println("Welcome to |INSERT APP NAME|");
        System.out.println("Developed by Sleeping Scales");
        System.out.println("Here are the available encryption protocol areas:\n");
        while (true) {
            System.out.println("Network Packetv4 Encryption");
            System.out.println("File Encryption");
            System.out.print("Select an option from above or q to exit: ");
            boolean first;
            switch (userin.nextLine().toLowerCase()) {
                ////--------------------------------NETWORK PROTOCOLS--------------------------------////
                case "network packet encryption":
                case "network":
                    System.out.println("\nAvailable Network Encryption Protocols:");
                    Thread.sleep(1000);
                    System.out.println("WEP");
                    Thread.sleep(150);
                    System.out.println("WPA");
                    Thread.sleep(150);
                    System.out.println("WPA2");
                    Thread.sleep(150);
                    System.out.println("WPA3");
                    Thread.sleep(500);
                    System.out.print("Select an encryption protocol from above, back to return, or q to exit: ");
                    first = true;
                    boolean network = true;
                    while(network){
                        if(!first){
                            System.out.println("\nAvailable Network Encryption Protocols:");
                            System.out.println("WEP");
                            System.out.println("WPA");
                            System.out.println("WPA2");
                            System.out.println("WPA3");
                            System.out.print("Select an encryption protocol from above, back to return, or q to exit: ");
                        }
                        switch (userin.nextLine().toLowerCase()) {
                            case "wep": //SIMULATE WEP PROTOCOL
                                System.out.println();
                                WEP.useWEP(userin);
                                break;
                            case "wpa": //SIMULATE WPA PROTOCOL
                                System.out.println();
                                WPA.useWPA(userin);
                                break;
                            case "wpa2": //SIMULATE WPA2 PROTOCOL
                                System.out.println();
                                WPA2.useWPA2(userin);
                                break;
                            case "wpa3": //SIMULATE WPA3 PROTOCOL
                                System.out.println();
                                WPA3.useWPA3(userin);
                                break;
                            case "back": //RETURN TO FILE/NETWORK SELECTION
                                System.out.println();
                                network = false;
                                break;
                            case "q":
                            case "exit": //EXIT APPLICATION
                                System.out.println("Bye!");
                                System.exit(0);
                            default:
                                System.out.println("Statement not recognized.");
                        }
                        first = false;
                    }
                    break;
                ////--------------------------------FILE ENCRYPTION--------------------------------////
                case "file encryption":
                case "file":
                    System.out.println("\nAvailable File Encryption Protocols:");
                    Thread.sleep(1000);
                    System.out.println("RSA");
                    Thread.sleep(100);
                    System.out.println("AES");
                    Thread.sleep(100);
                    System.out.println("DES");
                    Thread.sleep(500);
                    System.out.print("Select an encryption protocol from above, back to return, or q to exit: ");
                    first = true;
                    boolean file = true;
                    while(file) {
                        if(!first){
                            System.out.println("\nAvailable File Encryption Protocols:");
                            System.out.println("RSA");
                            System.out.println("AES");
                            System.out.println("DES");
                            System.out.print("Select an encryption protocol from above, back to return, or q to exit: ");
                        }
                        switch (userin.nextLine().toLowerCase()){
                            case "rsa": //USE RSA ENCRYPTION
                                System.out.println();
                                RSA.useRSA(userin);
                                break;
                            case "des": //USE DES ENCRYPTION
                                System.out.println();
                                DES.useDES(userin);
                                break;
                            case "aes": //USE AES ENCRYPTION
                                System.out.println();
                                AES.useAES(userin);
                                break;
                            case "back": //RETURN TO FILE/NETWORK SELECTION
                                System.out.println();
                                file = false;
                                break;
                            case "q":
                            case "exit": //EXIT APPLICATION
                                System.out.println("Bye!");
                                return;
                            default:
                                System.out.println("Statement not recognized.");
                                Thread.sleep(100);
                        }
                        first = false;
                    }
                    break;
                case "q":
                    System.out.println("Bye!");
                    return;
                default:
                    System.out.println("Statement not recognised. Try 'Networking' or 'File'");
            }
        }
    }
}

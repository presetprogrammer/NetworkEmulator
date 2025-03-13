package Emulator;

import Networking.Supporting.IllegalAddressException;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class NetworkEmulator {
    public static void main(String[] args) throws InterruptedException, IOException, IllegalAddressException {
        Scanner userin = new Scanner(System.in);
        boolean first = true;
        while(true) {
            if (first) {
                System.out.println("Welcome to Network Emulator!");
                Thread.sleep(200);
                System.out.println("Created by Sleeping Scales.");
                Thread.sleep(300);
                System.out.println("Would you like to create a new environment, or open one?");
                System.out.print("(create / open): ");
            }
            first = false;
            switch (userin.nextLine().toLowerCase()) {
                case "create":
                    System.out.print("Name?: ");
                    String emName = userin.nextLine();
                    Environment env = new Environment(emName);
                    System.out.print("Run Environment? (y/n): ");
                    if (userin.nextLine().toLowerCase().equals("y")) {
                        Environment.runEnvironment(env, userin);
                    }
                    break;
                case "open":
                    File f = new File("Environments");
                    File[] files = f.listFiles();
                    if (files != null) {
                        if (files.length > 0) {
                            System.out.println("Available Environments:");
                            for (File file : files) {
                                System.out.println(file.getName());
                            }
                            System.out.print("Select an environment: ");
                            String envName = userin.nextLine();
                            Environment e = Environment.getEnvironment(envName);
                            Environment.runEnvironment(e, userin);
                        } else {
                            System.out.println("No environments found.");
                        }
                    }
                    break;
                case "quit":
                case "q":
                    System.out.println("Bye!");
                    System.exit(0);
                default:
                    System.out.println("Not a valid option. Please ");
            }
        }
    }
}

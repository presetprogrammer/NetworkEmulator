package Backend;

import Encryption.EncoderSupport.ServerType;
import Networking.IPv4;
import Networking.IPv6;
import java.util.Scanner;

public class Server{
    String name;
    String password;
    Router router;
    boolean first = true;
    boolean isOnline = false;
    ServerType type;
    IPv4 ipv4;
    IPv6 ipv6;

    public Server(String name, String password, ServerType st){
        this.name = name;
        this.password = password;
        this.type = st;
    }


    public void run(Scanner userin) throws InterruptedException {
        if(!isOnline) {
            System.out.println("Booting " + name);
            isOnline = true;
        }
        else{
            System.out.println("Opening " + name);
        }
        Thread.sleep(1000);
        while(true) {
            System.out.print((first) ? "Password: " : "Incorrect password. Try again: ");
            if (userin.nextLine().equals(password)) {
                accepted(userin);
                return;
            }
            else first = false;
        }
    }
    private void accepted(Scanner userin) throws InterruptedException {
        boolean first1 = true;
        while(true){
            System.out.println("Welcome. Select an option:");
            if(first1) {
                Thread.sleep(300);
                System.out.println("View Logs");
                Thread.sleep(150);
                System.out.println("Settings");
                Thread.sleep(150);
                System.out.println("Shutdown (s)");
                Thread.sleep(150);
            }
            else{
                System.out.println("View Logs");
                System.out.println("Settings");
                System.out.println("Shutdown (s)");
            }
            System.out.println("Exit (e)");
            Thread.sleep(200);
            first1 = false;
            System.out.print("-> ");
            switch(userin.nextLine()){
                case "view logs":
                case "logs":
                    boolean first2 = true;
                    while(true) {
                        System.out.println("Select log:");
                        if(first2) {
                            Thread.sleep(300);
                            System.out.println("Access Logs");
                            Thread.sleep(150);
                        }
                        else{
                            System.out.println("Access Logs");
                        }
                        System.out.println("Return");
                        Thread.sleep(200);
                        first2 = false;
                        System.out.print("-> ");
                        boolean exit = false;
                        switch (userin.nextLine()) {
                            case "access logs":
                            case "access":
                                System.out.println("Access Logs:");
                                break;
                            case "return":
                                exit = true;
                                break;
                            default:
                                System.out.println("Invalid option. Please try again.");
                        }
                        if(exit) break;
                    }
                    break;
                case "settings":
                    System.out.println("Settings:");

                    break;
                case "shutdown":
                case "s":
                    System.out.println("Shutting down...");
                    isOnline = false;
                    Thread.sleep(500);
                    return;
                case "exit":
                case "e":
                    System.out.println("Exiting...");
                    Thread.sleep(500);
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }


    public String getName(){ return name; }
    public void setAddress(IPv4 address){
        ipv4 = address;
    }
    public void setAddress(IPv6 address){
        ipv6 = address;
    }
    public IPv4 getIPv4() {
        return ipv4;
    }
    public IPv6 getIpv6(){
        return ipv6;
    }
}

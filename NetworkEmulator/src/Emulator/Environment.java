package Emulator;

import Backend.*;
import Encryption.EncoderSupport.ServerType;
import Networking.Supporting.IllegalAddressException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Environment {
    File env;
    String name;
    HashMap<String, PC> pcs = new HashMap<>();
    HashMap<String, Server> servers = new HashMap<>();
    HashMap<String, Router> routers = new HashMap<>();
    HashMap<String, Switch> switches = new HashMap<>();

    public Environment(String name) throws IOException {
        env = new File(name);
        if(env.createNewFile()) {
            System.out.println("Environment created: " + env.getName());
        } else {
            System.out.println("Environment already exists.");
        }
    }

    public static void runEnvironment(@NotNull Environment environment , @NotNull Scanner userin) throws InterruptedException, IOException, IllegalAddressException {
        System.out.println("Welcome to " + environment.getName() + "Environment!");
        Thread.sleep(200);
        System.out.println("Here are the available options:");
        Thread.sleep(500);
        System.out.println("PC");
        Thread.sleep(500);
        System.out.println("Server");
        Thread.sleep(500);
        System.out.println("Router");
        Thread.sleep(500);
        System.out.println("Switch");
        Thread.sleep(500);
        System.out.println("Exit (q)");
        Thread.sleep(500);
        char[] passwordArray;
        switch(userin.nextLine().toLowerCase()){
            case "create pc":
            case "c":
                System.out.print("Enter PC Name: ");
                String pcName = userin.nextLine();
                System.out.print("Enter Username: ");
                String username = userin.nextLine();
                System.out.print("Enter Password: ");
                passwordArray = System.console().readPassword();
                String password = new String(passwordArray);
                environment.createPC(pcName, new User(username, password, true));
                System.out.println("Created PC: " + pcName);
                break;
            case "create server":
            case "s":
                System.out.print("Enter Server Name: ");
                String serverName = userin.nextLine();
                System.out.println("Enter Server Password: ");
                passwordArray = System.console().readPassword();
                String serverPassword = new String(passwordArray);
                System.out.print("Enter Server Type (DATA, WEB, EMAIL, DNS, DHCP, FTP, TELNET, SSH, SNMP, VOIP, VPN, or HTTP): ");
                ServerType serverType = ServerType.valueOf(userin.nextLine().toUpperCase());
                environment.createServer(serverName, serverPassword, serverType);
                break;
            case "create router":
            case "r":
                System.out.print("Enter Router Name: ");
                String routerName = userin.nextLine();
                System.out.print("Enter Router Password: ");
                passwordArray = System.console().readPassword();
                String routerPassword = new String(passwordArray);
                environment.createRouter(routerName, routerPassword);
                break;
            case "create switch":
                System.out.print("Enter Switch Name: ");
                String switchName = userin.nextLine();
                System.out.print("Enter")
            case "run pc":
            case "rp":
                System.out.print("Enter PC Name: ");
                environment.runPC(userin.nextLine());
                break;
            case "exit":
            case "q":
                break;
        }
    }
    private void createPC(String name, User username) throws IOException {
        pcs.put(name, new PC(name, username));
    }
    private void createServer(String name, String password, ServerType type) {
        servers.put(name, new Server(name, password, type));
    }
    private void createRouter(String name, String pass) throws IllegalAddressException {
        try {
            routers.put(name, new Router(name, pass));
        }
        catch(IOException ioe){
            System.out.println("Error creating router.");
        }
    }
    private void createSwitch(String name, String range) throws IOException {
        Switch s = new Switch(name);
        switches.put(name, s);

    }

    private void runPC(String name){

    }
    /**
     * Returns the name of the Environment.
     * @return the name of the Environment.
     */
    public String getName(){
        return name;
    }

    @Contract("_ -> new")
    public static @NotNull Environment getEnvironment(String name) throws IOException {
        File file = new File(name);
        if(file.exists()){
            return new Environment(name);
        }
        else throw new FileNotFoundException("Environment does not exist.");
    }
}

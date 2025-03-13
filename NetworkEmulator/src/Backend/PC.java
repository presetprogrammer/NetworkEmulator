package Backend;

import Emulator.EncNet;
import Encryption.EncoderSupport.DeviceType;
import Networking.AddressAssignment.APIPA;
import Networking.IPv4;
import Networking.IPv6;
import Networking.Supporting.IllegalAddressException;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class PC{
    File users;
    ArrayList<User> userList = new ArrayList<>();
    File accessLog;
    String pcName;
    IPv4 ipv4Address;
    IPv6 ipv6Address;
    APIPA apipa;
    boolean isRunning;
    DeviceType type = DeviceType.PC;

    /**
     * Creates a new PC given the PC's name and a User as admin.
     * @param pcName {@link String} Name of the PC.
     * @param user {@link User} Admin of the PC.
     * @throws IOException If file is corrupted.
     */
    public PC(String pcName, User user) throws IOException {
        this.pcName = pcName;
        accessLog = new File(pcName + "\\" + pcName + " accessLog");
        users = new File(pcName + "\\" + pcName + " Users");
        Path path = Paths.get(pcName);
        Files.createDirectory(path);
        boolean test1 = users.createNewFile();
        accessLog.createNewFile();
        Scanner fetchUsers;
        FileWriter addUsers;
        apipa = new APIPA();
        if(test1){
            fetchUsers = new Scanner(users);
            addUsers = new FileWriter(users);
            addUsers.write(user.name + '\n' + user.password + '\n' + Rank.ADMIN + "\n\n");
            addUsers.close();
            fetchUsers.close();
        }
        if(ipv4Address == null) {

        }
    }

    /**
     * Emulates a PC through the given environment.
     * @param userin {@link Scanner} User input.
     * @param env {@link String} Emulator's Environment.
     * @throws InterruptedException If thread is interrupted.
     * @throws IOException If file is corrupted.
     */
    public void runPC(Scanner userin, @NotNull String env) throws InterruptedException, IOException, IllegalAddressException {
        ArrayList<String> userstring = new ArrayList<>();
        System.out.print("Booting up " + pcName);
        Thread.sleep(100);
        System.out.print(".");
        Thread.sleep(100);
        System.out.print(".");
        Thread.sleep(100);
        System.out.println(".");
        Thread.sleep(200);
        System.out.println("Hello! Choose an account to sign into:");
        Scanner filein = new Scanner(users);
        while(filein.hasNextLine()){
            String available = filein.nextLine();
            userstring.add(available);
            System.out.println(available);
            String password = "";
            Rank rank = null;
            for(int x = 0; x < 3; x++) {
                switch(x){
                    case 0:
                        password = filein.nextLine();
                        break;
                    case 1:
                        rank = Rank.valueOf(filein.nextLine());
                        break;
                    default:
                        userList.add(new User(available, password, rank == Rank.ADMIN));
                }
            }
        }
        System.out.print("-> ");
        String login = userin.nextLine();
        for(String user : userstring){
            if(login.equals(user)){

                System.out.print("Password: ");
                String pass = userin.nextLine();
                FileWriter fw = new FileWriter(accessLog);
                String time = String.valueOf(LocalTime.now());
                boolean success = pass.equals(filein.nextLine());
                //1 means successful login, 0 means unsuccessful
                fw.write((success ? "[1]":"[0]") + LocalDate.now() + "  |  " + time.substring(0, time.indexOf('.')) + "  |  " + login + "  |  " + pass + '\n');
                if(success){
                    signedIn(userin, env, user);
                }
                fw.close();
            }
        }
    }
    public void enhancedRunPC(Scanner userin, String env){
        javax.swing.JWindow window = new javax.swing.JWindow();
    }

    /**
     * @return {@link IPv4} address of {@link PC}.
     */
    public IPv4 getIpv4Address(){
        return ipv4Address;
    }

    /**
     * @return {@link IPv6} address of {@link PC}.
     */
    public IPv6 getIpv6Address(){
        return ipv6Address;
    }

    private void signedIn(@NotNull Scanner userin, @NotNull String env, String user) throws InterruptedException, IOException, IllegalAddressException {
        System.out.println("Welcome " + user);
        Thread.sleep(100);
        while(true){
            System.out.println("Available Programs:");
            Thread.sleep(300);
            System.out.println("Encryption/Networking Emulator");
            Thread.sleep(150);
            System.out.println("Settings");
            Thread.sleep(150);
            System.out.println("Shutdown");
            Thread.sleep(150);
            System.out.println("Exit");
            Thread.sleep(300);
            System.out.print("-> ");
            switch(userin.nextLine().toLowerCase()){
                case "encryption/networking emulator":
                case "emulator":
                    EncNet.runEmulator(userin, env);
                    break;
                case "settings":
                    System.out.println("Available Settings:");
                    Thread.sleep(300);
                    System.out.println("Add a user");
                    Thread.sleep(150);
                    System.out.println("Connect to a network");
                    Thread.sleep(150);
                    System.out.println("Print Package Log");
                    Thread.sleep(150);
                    System.out.println("Return");
                    Thread.sleep(300);
                    System.out.print("-> ");
                    switch(userin.nextLine().toLowerCase()){
                        case "add user":
                        case "user":
                        case "add a user":
                            System.out.print("username: ");
                            String newuser = userin.nextLine();
                            Scanner filein = new Scanner(users);
                            ArrayList<String> userlist = new ArrayList<>();
                            while(filein.hasNextLine()){
                                userlist.add(filein.nextLine());
                                for(int x = 0; x < 3; x++) filein.nextLine();
                            }
                            boolean exists = false;
                            for(String u : userlist){
                                if(u.equals(newuser)){System.out.println("Computers.User already exists"); exists = true;}
                                break;
                            }
                            if(!exists) {
                                System.out.print("Password: ");
                                char[] passwordArray = System.console().readPassword();
                                String newpass = new String(passwordArray);
                                boolean valid = false;
                                boolean isAdmin = false;
                                System.out.print("Admin? (y/n):");
                                while (!valid) {
                                    String admin = userin.next().toLowerCase();
                                    if (admin.charAt(0) == 'y' && admin.length() == 1) {
                                        isAdmin = true;
                                        valid = true;
                                    } else if (admin.charAt(0) == 'n' && admin.length() == 1) valid = true;
                                    else System.out.println("Please type 'y' or 'n'");
                                }
                                addUser(newuser, newpass, isAdmin);
                            }
                            break;
                        case "connect to a network":
                        case "network":
                            File file = new File(env + "\\Routers");
                            Scanner routerin = new Scanner(file);
                            ArrayList<String> routers = new ArrayList<>();
                            while(routerin.hasNextLine()){
                                routers.add(routerin.nextLine());
                            }
                            System.out.println("Available Routers:");
                            Thread.sleep(200);
                            for(String router : routers){
                                System.out.println(router);
                                Thread.sleep(150);
                            }
                            System.out.print("-> ");
                            String router = userin.nextLine();
                            if(routers.contains(router)){
                                System.out.print("Password: ");

                                System.out.println("Connecting to " + router);
                                Thread.sleep(100);
                                System.out.println("Connected!");
                                Thread.sleep(200);
                            }
                            else System.out.println("Router not found.");
                            break;
                        case "return":
                            break;
                        default:
                            System.out.println("Statement not recognized.");
                    }
                    break;
                case "shutdown":
                    System.out.println("Shutting down...");
                    isRunning = false;
                    Thread.sleep(500);
                    return;
                case "exit":
                case "e":
                    System.out.println("Exiting...");
                    Thread.sleep(500);
                    return;
            }
        }
    }

    public String getName(){
        return pcName;
    }
    public Object host(){
        if(ipv4Address != null) return ipv4Address;
        else return ipv6Address;
    }
    private void addUser(String username, String password, boolean isAdmin) throws IOException {
        try(FileWriter fw = new FileWriter(users)){
            fw.write(username + "  |  (Secure)" + password + "  |  " + (isAdmin ? Rank.ADMIN : Rank.USER));
        }
    }

    /**
     * Sets the IPv4 address of the PC.
     * @param ipv4 {@link IPv4} IPv4 address to set.
     */
    protected void setAddress(IPv4 ipv4) {
        ipv4Address = ipv4;
    }

    /**
     * Sets the address of the PC in IPv6 format
     * @param ipv6 {@link IPv6} IPv6 address to set.
     */
    protected void setAddress(IPv6 ipv6) {
        ipv6Address = ipv6;
    }
}

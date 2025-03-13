package Backend;

import Encryption.EncoderSupport.DeviceType;
import Networking.AddressAssignment.ARP;
import Networking.AddressAssignment.DHCP;
import Networking.IPv4;
import Networking.IPv6;
import Networking.Supporting.AddressBitStringConverter;
import Networking.Supporting.IllegalAddressException;
import Networking.Supporting.IllegalMaskException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.http.WebSocket;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Router{
    /**
     * {@link File} containing all active connections. Format is (TYPE(IPV4 or IPV6),NAME,ADDRESS). Example: IPV4,Test,169.254.10.1
     */
    public File connected;
    /**
     * {@link File} containing all connection attempts. Can be flushed.
     */
    public File connectLog;
    /**
     * Current active {@link DHCP}. No default given.
     */
    public DHCP dhcp;
    /**
     * Current active {@link ARP}. No default given.
     */
    public ARP arp;
    //Password for Router
    private String Loginpass;
    /**
     * Name of Router
     */
    public String name;
    /**
     * {@link IPv4} or {@link IPv6} range of {@link Router}. If {@link Switch} is being used, range will be ignored.
     */
    public String range;
    /**
     * Enables use of {@link Switch}. Cannot be modified without toggling (and making sure there is at least 1 {@link Switch}. Default is false.
     */
    protected boolean useSwitch = false;
    /**
     * Current active {@link Switch}. Default is null
     */
    public Switch s = null;

    /**
     * Creates a new Router object with the given Name, IPAddress, and Password. If Router is not using Switch, then address designation will fall on the Switch.
     * Otherwise, the Switch will be used to assign addresses.
     * @param name {@link String} Name of the Router.
     * @param pass {@link String} Password of the Router.
     * @throws IllegalAddressException if Address provided is invalid.
     */
    public Router(String name, String pass) throws IllegalAddressException, IOException {
        if(!new File(name).createNewFile()) throw new IOException("Router already exists.");
        dhcp = new DHCP(this);
        if(pass.isBlank()){
            boolean waiting = true;
            String newpass = "";
            System.out.println("Enter a password.");
            try(Scanner userin = new Scanner(System.in)){
                while(waiting){
                    System.out.print("-> ");
                    newpass = userin.nextLine();
                    waiting = !(newpass.equals("q"));
                }
                pass = newpass;
            }
        }
        if(pass.equals("q")){
            boolean waiting = true;
            String newpass = "";
            System.out.println("q is reserved. Try another password.");
            try(Scanner userin = new Scanner(System.in)){
                while(waiting){
                    System.out.print("-> ");
                    newpass = userin.nextLine();
                    waiting = !(newpass.equals("q"));
                }
                pass = newpass;
            }
        }
        this.Loginpass = pass;
        connected = new File(name + "\\" + name + " ConnectedDevices.txt");
    }
    public void useRouter(Scanner userin) throws InterruptedException, IOException {
        FileWriter fw = new FileWriter(connectLog);
        while(true) {
            System.out.print("Password (Press q to exit): ");
            char[] pass = System.console().readPassword();
            String attempt = new String(pass);
            if (Loginpass.equals(attempt)) {
                success(userin);
                fw.write(LocalDateTime.now() + "\t|\tGRANTED");
                fw.close();
                return;
            } else {
                fw.write(LocalDateTime.now() + "\t|\tDENIED");
                System.out.println("Incorrect Password");
            }
        }
    }

    public String getRange(){
        return range;
    }
    public String getBitRange() throws IllegalAddressException, IllegalMaskException {
        if(range.indexOf('.') == -1){
            return AddressBitStringConverter.toBitString(IPv6.toIPv6(range));
        }
        return AddressBitStringConverter.toBitString(IPv4.toIPv4(range));
    }

    public File getConnected(@NotNull DeviceType dt) {
        if(dt.equals(DeviceType.NETWORK_PROTOCOL) || dt.equals(DeviceType.ROUTER) || dt.equals(DeviceType.SWITCH)){
            return connected;
        }
        else{
            Scanner userin = new Scanner(System.in);
            System.out.println("Accessing from " + dt + " requires password."); System.out.print("Password: "); String test = userin.nextLine();
            if(Loginpass.equals(test)){
                return connected;
            }
            throw new SecurityException("Access Denied");
        }
    }

    public void setRange(String range){
        this.range = range;
    }

    private void success(@NotNull Scanner userin) throws InterruptedException, IOException {
        System.out.println("Connected to Router " + name);
        Thread.sleep(200);
        System.out.println("Options:");
        Thread.sleep(200);
        System.out.println("Password (p)");
        Thread.sleep(150);
        System.out.println("Network (n)");
        Thread.sleep(150);
        System.out.println("Shutdown (s)");
        Thread.sleep(150);
        System.out.println("Exit (e)");
        Thread.sleep(150);
        System.out.print("-> ");
        switch(userin.nextLine().toLowerCase()){
            case "password":
            case "p":
                System.out.println("Password options:");
                Thread.sleep(200);
                System.out.println("Change/Set password (c)");
                Thread.sleep(150);
                System.out.println();
                System.out.println("Return (r)");
                char[] newpass = System.console().readPassword();
                Loginpass = new String(newpass);
                break;
            case "network":
            case "n":
                System.out.println("Network options:");
                Thread.sleep(200);
                System.out.println("Show connected devices (s)");
                Thread.sleep(150);
                if(useSwitch) {
                    System.out.println("Disable Switch (d)");
                    Thread.sleep(150);
                    System.out.println("Remove Switch (r)");
                }
                else{
                    if(s == null) {
                        System.out.println("Add Switch (a)");
                        Thread.sleep(150);
                    }
                    else{
                        System.out.println("Enable Switch (e)");
                        Thread.sleep(150);
                    }
                }
                Thread.sleep(150);
                System.out.println("Return (r)");
                Thread.sleep(200);
                System.out.print("-> ");
                switch(userin.nextLine().toLowerCase()){
                    case "show connected devices":
                    case "connected devices":
                    case "s":
                        System.out.println("Connected Devices:");
                        Thread.sleep(200);
                        Scanner filein = new Scanner(connected);
                        while(filein.hasNextLine()){
                            System.out.println(filein.nextLine());
                        }
                        break;
                    case "disable switch":
                    case "disable":
                    case "d":
                        useSwitch = false;
                        break;
                    case "remove":
                    case "remove switch":
                    case "r":
                        s = null;
                        break;
                    case "add":
                    case "add switch":
                    case "a":
                        System.out.print("Switch Name: ");
                        String name = userin.nextLine();
                        s = new Switch(name);
                        break;
                    case "enable":
                    case "enable switch":
                    case "e":
                        useSwitch = true;
                        break;
                }
        }
    }

    public boolean connectv4(PC pc, @NotNull String pass) throws FileNotFoundException, IllegalAddressException, IllegalMaskException {
        if(pass.equals(Loginpass)){
            pc.setAddress(dhcp.getIPv4());
            return true;
        }
        return false;
    }
    public boolean connectv6(PC pc, @NotNull String pass) throws FileNotFoundException, IllegalAddressException {
        if(pass.equals(Loginpass)){
            pc.setAddress(dhcp.getIPv6(this, connected));
            return true;
        }
        return false;
    }
    public boolean connectv4(Server server, @NotNull String pass) throws FileNotFoundException, IllegalAddressException, IllegalMaskException {
        if(pass.equals(Loginpass)){
            server.setAddress(dhcp.getIPv4());
            return true;
        }
        return false;
    }
    public boolean connectv6(Server server, @NotNull String pass) throws FileNotFoundException, IllegalAddressException {
        if(pass.equals(Loginpass)){
            server.setAddress(dhcp.getIPv6(this, connected));
            return true;
        }
        return false;
    }
    public void onDisconnect(@NotNull WebSocket webSocket){
        webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "Disconnected");
    }
}

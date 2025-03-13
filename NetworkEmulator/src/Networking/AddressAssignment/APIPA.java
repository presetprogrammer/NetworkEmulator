package Networking.AddressAssignment;

import Backend.PC;
import Backend.Router;
import Backend.Server;
import Encryption.EncoderSupport.DeviceType;
import Networking.IPv4;
import Networking.Supporting.IllegalAddressException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class APIPA {
    public static final String PREFIX = "169.254.";

    /**
     * Assigns an APIPA standard address to the given computer. If the computer has a router assigned to it but has run ipconfig /release,
     * then the address will be a unique APIPA address based on relative network. Otherwise, if the {@code Object} has no assigned Router or Switch,
     * APIPA will randomly generate a valid APIPA address. If the user uses ipconfig /renew, or has an {@code APIPA} address when connecting to a router,
     * the router will overwrite the computer's current address and replace it with its delegated address protocol(s).
     * @param computer {@link Object} Computer (Any server type or PC)
     * @return {@link IPv4} APIPA address (not connected to a network).
     * @throws IllegalAddressException If address provided does not meet {@code IPv4} or {@code IPv6} standards.
     */
    public static @NotNull IPv4 assignAddress(@NotNull Object computer) throws IllegalAddressException, FileNotFoundException {
        if(computer.getClass().equals(Router.class)){
            Router r = (Router) computer;
            File f = r.getConnected(DeviceType.NETWORK_PROTOCOL);
            ArrayList<IPv4> addresses = new ArrayList<>();
            try(Scanner filein = new Scanner(f)){
                while(filein.hasNextLine()){
                    String add = filein.nextLine();
                    if(add.substring(0, 4).equals("IPV4")){
                        String add2 = (add.substring(add.indexOf(',')).substring(add.indexOf(',')));
                        if(add2.indexOf('\n') != -1) add2 = add2.substring(0, add2.length()-1);
                        addresses.add(IPv4.toIPv4(add2));
                    }
                }
            }
            int[][] temp = new int[addresses.size()][2];
            for (int x = 0; x < addresses.size(); x++){
                temp[x][0] = Integer.parseInt(addresses.get(x).toString().substring(9, 12));
                temp[x][1] = Integer.parseInt(addresses.get(x).toString().substring(13, 16));
            }
            while (true) {
                int x = (int) (Math.random() * 255);
                int y = (int) (Math.random() * 255);
                if (!(x == 0 && y == 0)) {
                    boolean valid = true;
                    for (int[] address : temp) {
                        if (address[0] == x && address[1] == y) {
                            valid = false;
                            break;
                        }
                    }
                    if (valid) return IPv4.toIPv4(PREFIX + x + "." + y);
                }
            }
        }
        else{
            while(true) {
                int x = (int) (Math.random() * 255);
                int y = (int) (Math.random() * 255);
                if(!(x == 0 && y == 0)) return IPv4.toIPv4(PREFIX + x + "." + y);
            }
        }
    }
}

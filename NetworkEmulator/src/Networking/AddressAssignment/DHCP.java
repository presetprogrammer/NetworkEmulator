package Networking.AddressAssignment;

import Backend.Router;
import Backend.Switch;
import Encryption.EncoderSupport.DeviceType;
import Networking.IPv4;
import Networking.IPv6;
import Networking.Supporting.Bit;
import Networking.Supporting.BitString;
import Networking.Supporting.IllegalAddressException;
import Networking.Supporting.IllegalMaskException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DHCP {
    protected IPv4 subnetmaskv4;
    protected String bitmaskv4;
    protected IPv4 addressv4;
    protected IPv6 addressv6;
    protected IPv6 subnetmaskv6;
    protected String bitmaskv6;
    private Router router;

    /**
     * DHCP protocol for {@code Router} class. Creates a range with a given {@code String} or type IPv4 or IPv6 address with extension.
     * @param router {@link Router} address with range to convert to mask.
     */
    public DHCP(@NotNull Router router) {
        this.router = router;
    }

    /**
     * Creates a usable IPv4 address for the assigned router.
     * @return {@link IPv4} address. Does not add the address to the {@code Router}'s reserved addresses file.
     * @throws FileNotFoundException If file does not exist (should not be thrown)
     * @throws IllegalAddressException If the format does not meet IPv4 standard (should not be thrown)
     * @throws IllegalMaskException If the subnet address does not meet IPv4 standard (should not be thrown)
     */
    public IPv4 getIPv4() throws FileNotFoundException, IllegalAddressException, IllegalMaskException {
        File reserved = router.getConnected(DeviceType.NETWORK_PROTOCOL);
        String range = router.getBitRange();
        ArrayList<String> ip4list = new ArrayList<>();
        try(Scanner s = new Scanner(reserved)){
            while(s.hasNextLine()){
                String address = s.nextLine();
                String in = address.substring(address.indexOf(',')).substring(address.indexOf(',')+1);
                if(address.startsWith("IPV4")) ip4list.add(in);
            }
        }
        StringBuilder bitadd = new StringBuilder();
        StringBuilder add = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        while(true) {
            for(int a = 0; a < 32; a++) {
                sb.append(Math.round(Math.random()));
                if(a % 8 == 0 && a != 0){
                    sb.append('.');
                }
            }
            if(sb.indexOf("1") != -1){
                for(int b = 0; b < range.length(); b++){
                    if(range.charAt(b) != '.'){
                        bitadd.append(Integer.parseInt(String.valueOf(range.charAt(b))) & Integer.parseInt(String.valueOf(sb.charAt(b))));
                    }
                    else bitadd.append('.');
                }
                int temp = 0;
                int count = 0;
                for(int c = 0; c < bitadd.length(); c++){
                    if(bitadd.charAt(c) == 1) {temp += 2^(8-count); count++;}
                    else if(bitadd.charAt(c) == 0) count++;
                    else {add.append(temp); add.append('.'); temp = 0; count = 0;}
                }
                boolean newadd = true;
                for(String s : ip4list){
                    if(s.contentEquals(add)) {newadd = false; break;}
                }
                if(newadd) return IPv4.toIPv4(String.valueOf(add));
                else {sb = new StringBuilder(); add = new StringBuilder(); bitadd = new StringBuilder();}
            }
            else {sb = new StringBuilder(); add = new StringBuilder(); bitadd = new StringBuilder();}
        }
    }

    /**
     * Creates a usable IPv4 address for the assigned router.
     * @return {@link IPv6} address. Does not add the address to the {@code Router}'s reserved addresses file.
     * @throws FileNotFoundException If file does not exist (should not be thrown)
     * @throws IllegalAddressException If the format does not meet IPv4 standard (should not be thrown)
     * @throws IllegalMaskException If the subnet address does not meet IPv4 standard (should not be thrown)
     */
    public IPv6 getIPv6() throws FileNotFoundException, IllegalAddressException, IllegalMaskException {
        File reserved = router.getConnected(DeviceType.NETWORK_PROTOCOL);
        String range = router.getBitRange();
        ArrayList<String> ip6list = new ArrayList<>();
        try(Scanner s = new Scanner(reserved)){
            while(s.hasNextLine()){
                String address = s.nextLine();
                String in = address.substring(address.indexOf(',')).substring(address.indexOf(',')+1);
                if(address.startsWith("IPV6")) ip6list.add(in);
            }
        }
        StringBuilder bitadd = new StringBuilder();
        StringBuilder add = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        while(true) {
            for (int a = 0; a < 32; a++) {
                sb.append(Math.round(Math.random() * 15));
                if (a % 8 == 0 && a != 0) {
                    sb.append(':');
                }
            }
        }
    }

    public IPv4 getIPv4(@NotNull Switch s) throws IllegalAddressException, FileNotFoundException {
        s.getRangev4();
        File occupied = s.getOccupied();
        ArrayList<IPv4> takenv4 = new ArrayList<>();
        try(Scanner filein = new Scanner(occupied)){
            while(filein.hasNextLine()){
                int start = 0;
                String file = filein.nextLine();
                if(file.substring(0, file.indexOf(',')).equals("IPV4")) {
                    for (int i = 0; i < file.length(); i++) {
                        start = (file.charAt(i) == ',') ? i : start;
                    }
                    takenv4.add(IPv4.toIPv4(file.substring(start+1)));
                }
            }
        }
        StringBuilder sb = getBitMaskv4(addressv4.toString());
        //Repeats indefinitely until hit
        while(true){
            for(int i = 0; i < sb.length(); i++){
                if(sb.charAt(i) == '0'){
                    sb.replace(i, i+1, Long.toString(Math.round(Math.random())));
                }
            }
            IPv4 test = maskBuilderv4(String.valueOf(sb));
            boolean hit = true;
            for(IPv4 ip : takenv4){
                if(test.equals(ip)) hit = false;
                takenv4.remove(ip);
                break;
            }
            if(hit) return test;
            if(takenv4.isEmpty()) throw new IllegalAddressException("Address space full. Cannot add another device.");
        }
    }
    public IPv6 getIPv6(@NotNull Switch s, @NotNull File addresses) throws FileNotFoundException, IllegalAddressException {
        try(Scanner filein = new Scanner(addresses)){
            return IPv6.toIPv6(filein.nextLine());
        }
    }

    public IPv6 getIPv6(@NotNull Router r, @NotNull File addresses) throws FileNotFoundException, IllegalAddressException {
        try(Scanner filein = new Scanner(addresses)){
            return IPv6.toIPv6(filein.nextLine());
        }
    }

    private static @NotNull IPv4 maskBuilderv4(@NotNull String ipv4) throws IllegalAddressException {
        StringBuilder sb = getBitMaskv4(ipv4);
        String a1 = sb.substring(0, 8);
        String a2 = sb.substring(9, 17);
        String a3 = sb.substring(18, 26);
        String a4 = sb.substring(27, 35);
        int f1 = 0;
        int f2 = 0;
        int f3 = 0;
        int f4 = 0;
        for(int a = 0; a < 8; a++){
            if(a1.charAt(a) == '1'){
                f1 += 2^(8-(a+1));
            }
            if(a2.charAt(a) == '1'){
                f2 += 2^(8-(a+1));
            }
            if(a3.charAt(a) == '1'){
                f2 += 2^(8-(a+1));
            }
            if(a4.charAt(a) == '1'){
                f2 += 2^(8-(a+1));
            }
        }
        String address = f1 + "." + f2 + "." + f3 + "." + f4;
        return IPv4.toIPv4(address);
    }

    private static @NotNull StringBuilder getBitMaskv4(@NotNull String ipv4) {
        int i = Integer.parseInt(ipv4.substring(ipv4.indexOf('/')+1));
        StringBuilder sb = new StringBuilder("00000000.00000000.00000000.00000000");
        if(i < 8) for(int x = 0; x < i; x++) sb.replace(x, x+1, "1");
        else if(i < 16){
            sb.replace(0, 8, "11111111");
            for(int x = 9; x < i+1; x++) sb.replace(x, x+1, "1");
        }
        else if(i < 24){
            sb.replace(0, 8, "11111111");
            sb.replace(9, 17, "11111111");
            for(int x = 18; x < i+2; x++) sb.replace(x, x+1, "1");
        }
        else if(i <= 32){
            sb.replace(0, 8, "11111111");
            sb.replace(9, 17, "11111111");
            sb.replace(18, 26, "11111111");
            for(int x = 27; x < i+3; x++) sb.replace(x, x+1, "1");
        }
        return sb;
    }

    private static @NotNull IPv6 maskBuilderv6(@NotNull String ipv6) throws IllegalAddressException {
        boolean cont = true;
        boolean exists = false;
        BitString bits = new BitString(128*8, false);
        StringBuilder sb = new StringBuilder();
        bits.setBits(0, Integer.parseInt(ipv6.substring(ipv6.indexOf('/'))), Bit.toBit(1));
        String mask = bits.toHex();
        for(int i = 0; i < mask.length(); i++){
            if(i % 4 == 0) if(i > 0){
                if(mask.charAt(i-1) == ':' && !exists) {sb.append(":"); exists = true;}
                else if(mask.charAt(i-1) == ':') sb.append("0:");
                else sb.append(":");
            }
            if(mask.charAt(i) != '0'){
                sb.append(mask.charAt(i));
                cont = false;
            }
            else if(!cont) sb.append("0");
        }
        return IPv6.toIPv6(sb.toString());
    }

    private static @NotNull StringBuilder getBitMaskv6(@NotNull String ipv6){

    }
}

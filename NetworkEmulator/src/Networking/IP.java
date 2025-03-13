package Networking;

import org.jetbrains.annotations.NotNull;

import javax.print.DocFlavor;

public class IP {

    /**
     * Verifies a given {@code String}
     * @param ipv4
     * @return
     */
    public static boolean verifyv4(@NotNull String ipv4){
        if(ipv4.indexOf('.') == -1) return false;
        else{
            for(int c = 0; c < ipv4.length(); c++){
                if(!((ipv4.indexOf(c) < 256 && ipv4.indexOf(c) >= 0) || ipv4.indexOf(c) == '.')){
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean verifyv4(IPv4 ignored) { return true; }
    public static boolean verifyv4(IPv6 ignored) {return true; }
    public static boolean verifyv6(@NotNull String ipv6){
        if(ipv6.indexOf(':') == -1) return false;
        else{
            for(int a = 0; a < ipv6.length(); a++){
                if(!((ipv6.indexOf(a) < 10 && ipv6.indexOf(a) >= 0) || ipv6.indexOf(a) == ':')){
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean verifyv6(IPv6 ignored){ return true; }
    public static boolean verifyv6(IPv4 ignored){ return false; }
}

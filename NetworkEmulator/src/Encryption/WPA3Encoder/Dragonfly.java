package Encryption.WPA3Encoder;

import Networking.*;
import org.jetbrains.annotations.NotNull;

public class Dragonfly {
    public static StringBuilder starget;
    public static StringBuilder shost;

    public static void Simulate(IPv4 host, IPv4 target) throws InterruptedException {
        stringify(host);
        stringify(target);
        System.out.print("Connecting to " + host);
        Thread.sleep(500);
        System.out.print(".");
        Thread.sleep(1000);
    }
    public static void Simulate(IPv6 host, IPv4 target){
        stringify(host);
        stringify(target);

    }
    public static void Simulate(IPv4 host, IPv6 target){
        stringify(host);
        stringify(target);
    }
    public static void Simulate(IPv6 host, IPv6 target){
        stringify(host);
        stringify(target);
    }

    private static void stringify(@NotNull IPv4 al){
        shost = new StringBuilder(al.toString());
        shost.delete(shost.length()-1, shost.length());
    }
    private static void stringify(IPv6 hex){
        for(int x = 0; x < 5; x++){
            starget.append(hex.toString());
        }
    }
}

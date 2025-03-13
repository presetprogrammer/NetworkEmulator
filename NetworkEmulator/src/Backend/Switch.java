package Backend;

import Encryption.EncoderSupport.DeviceType;
import Networking.IPv4;
import Networking.IPv6;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Switch {
    String name;
    File connectedDevices;
    Router r;
    IPv4 range4;
    IPv6 range6;
    DeviceType type = DeviceType.SWITCH;

    public Switch(String name) throws IOException {
        if(!new File(name).createNewFile()) throw new IOException("Switch already exists.");
        this.name = name;
        File sFile = new File(name);
    }

    public void connect(@NotNull PC pc) throws IOException {
        try(Scanner s = new Scanner(connectedDevices)){
            while(s.hasNextLine()){
                String deviceLine = s.nextLine();
                if(deviceLine.contains(pc.getName())) return;
            }
        }
        try(FileWriter fw = new FileWriter(connectedDevices)) {
            fw.write(pc.getName() + ", " + DeviceType.PC.name() + ((pc.getIpv4Address() != null) ? "IPV4" : "IPV6"));
        }
    }
    public void connect(@NotNull Server server) throws IOException {
        try(FileWriter fw = new FileWriter(connectedDevices)) {
            fw.write(server.getName() + ", " + DeviceType.SERVER.name());
        }
    }
    public void connectRouter(Router r){
        this.r = r;
    }
    public void disconnect(PC pc) throws FileNotFoundException {
        try(Scanner s = new Scanner(connectedDevices)){
            while(s.hasNextLine()){
                String deviceLine = s.nextLine();
                if(deviceLine.contains(pc.getName())) break;
            }
        }
    }
    public void disconnect(Server server){
        connectedServers.remove(server);
    }
    public void disconnectRouter(@NotNull DeviceType source){
        if(source.equals(DeviceType.ROUTER) || source.equals(DeviceType.SWITCH)){
            r = null;
        } else{
            throw new SecurityException("Cannot disconnect from " + source.name());
        }
    }

    public IPv4 getRangev4() {
        return range4;
    }
    public IPv6 getRangev6(){
        return range6;
    }
    public File getOccupied(){
        return connectedDevices;
    }
}

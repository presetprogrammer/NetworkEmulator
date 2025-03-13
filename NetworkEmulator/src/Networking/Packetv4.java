package Networking;

import Backend.PC;
import Backend.Server;
import Backend.Router;
import Networking.Supporting.Bit;
import Networking.Supporting.BitString;
import Networking.Supporting.DeviceTypeException;
import org.jetbrains.annotations.NotNull;
import java.util.Dictionary;

public class Packetv4 {
    //Version. Always 4 for IPv4.
    protected BitString version = BitString.toBitString(100);
    //4-bit field. Specifies length of header in 32-bit words. The minimum is 5 and maximum is 15.
    protected BitString ihl = new BitString(4, false);
    //8-bit field. Quality of service (Low Delay, High Throughput, Reliability).
    protected BitString tos = new BitString(8, false);
    //16-bit field. Indicates total length of packet. Minimum is 20, maximum is 65535
    protected BitString totalLength = new BitString(16, false);
    //16-bit field. Identifies fragments of a single IP datagram.
    protected BitString identification = new BitString(16, false);
    //3-bit field. Contains control flags with the first bit reserved as 0, second bit is "Don't Forget", third bit is "More Fragments".
    protected BitString flags = new BitString(3, false);
    //13-bit field. Indicates the offset in the original datagram and is measured in bytes.
    protected BitString fragment = new BitString(13, false);
    //8-bit field. Specifies max number of hops a packet can take before being discarded.
    protected BitString ttl = new BitString(8, false);
    //8-bit field. Indicates the protocol used in the data portion.
    protected BitString protocol = new BitString(8, false);
    //16-bit field. Checks header for errors.
    protected BitString headerCheck = new BitString(16, false);
    //32-bit field. Source address.
    protected BitString source = new BitString(32, false);
    //32-bit field. Target address.
    protected BitString destination = new BitString(32, false);
    //Compact packet format
    protected static Dictionary<String, BitString> packet;

    private Packetv4(BitString version, BitString ihl, BitString tos, BitString totalLength, BitString id, BitString flags,
                     BitString fragment, BitString ttl, BitString protocol, BitString header, BitString source, BitString destination){
        this.version = version;
        this.ihl = ihl;
        this.tos = tos;
        this.totalLength = totalLength;
        this.identification = id;
        this.flags = flags;
        this.fragment = fragment;
        this.ttl = ttl;
        this.protocol = protocol;
        this.headerCheck = header;
        this.source = source;
        this.destination = destination;
    }

    /**
     * Builds a {@link Packetv4} Packet adhering to IPv4 standards.
     * @param o {@link Object} Input computer. Usable computers include {@link PC}, {@link Server}, and {@link Router}.
     * @param build {@link Dictionary}{@code <String, BitString>} used for packet contents.
     * Contains Header Length ("Header"), Type of Service ("ToS"), Total Length ("Length"),
     * Identification ("Identification"), Flags ("Flags"), Fragment ("Fragment"), Protocol ("Protocol"), and Destination ("Destination").
     * @return {@link Packetv4} Packet adhering to IPv4 standards, which is usable by any network protocol that supports IPv4 format.
     */
    public static @NotNull Packetv4 buildpacketv4(@NotNull Object o, Dictionary<String, BitString> build) throws DeviceTypeException {
        BitString header;
        BitString ToS;
        BitString length;
        BitString id;
        BitString flag;
        BitString frag;
        BitString proto;
        BitString source;
        if(o.getClass().equals(PC.class)) {
            PC pc = (PC) o;
            //Create IPv4 Address as BitString
            BitString bitadd = new BitString();
            String tempstr = pc.getIpv4Address().toString();
            header = build.get("Header");
            ToS = build.get("ToS");
            length = build.get("Length");
            id = build.get("Identification");
            flag = build.get("Flags");
            frag = build.get("Fragment");
            proto = build.get("Protocol");
            source = build.get("Source");
            build.get("Destination");
            while(!tempstr.isEmpty()){
                int num = Integer.parseInt(tempstr.substring(0, tempstr.indexOf(0, tempstr.indexOf('.'))));
                int power = 8;
                while(num > 0){
                    if(num - (2^power) > 0) {bitadd.addBit(Bit.toBit(1)); num -= (2^power);}
                    else bitadd.addBit(Bit.toBit(0));
                    power--;
                }
            }

            Packetv4 temppacket = new Packetv4(new BitString(100, true), new BitString(10100000, true), header, ToS, length, id, flag, frag, new BitString(1010, true), proto, source, bitadd);
            packet.put("Version", temppacket.getVersion());
            packet.put("Header Length", temppacket.getIhl());
            packet.put("Type of Service", temppacket.getTos());
            packet.put("Total Length", temppacket.getTotalLength());
            packet.put("Identification", temppacket.getIdentification());
            packet.put("Flags", temppacket.getFlags());
            packet.put("Fragment", temppacket.getFragment());
            packet.put("Time to Live", temppacket.getTtl());
            packet.put("Protocol", temppacket.getProtocol());
            packet.put("Header Checksum", temppacket.getHeaderCheck());
            packet.put("Source", temppacket.getSource());
            packet.put("Destination", temppacket.getDestination());
            return temppacket;
        }
        else if(o.getClass().equals(Server.class)){
            Server server = (Server) o;
            //Create IPv4 Address as BitString
            BitString bitadd = new BitString();
            String tempstr = server.getIPv4().toString();
            header = build.get("Header");
            ToS = build.get("ToS");
            length = build.get("Length");
            id = build.get("Identification");
            flag = build.get("Flags");
            frag = build.get("Fragment");
            proto = build.get("Protocol");
            source = build.get("Source");
            build.get("Destination");
            while(!tempstr.isEmpty()){
                int num = Integer.parseInt(tempstr.substring(0, tempstr.indexOf(0, tempstr.indexOf('.'))));
                int power = 8;
                while(num > 0){
                    if(num - (2^power) > 0) {bitadd.addBit(Bit.toBit(1)); num -= (2^power);}
                    else bitadd.addBit(Bit.toBit(0));
                    power--;
                }
            }

            Packetv4 temppacket = new Packetv4(new BitString(100, true), new BitString(10100000, true), header, ToS, length, id, flag, frag, new BitString(1010, true), proto, source, bitadd);
            packet.put("Version", temppacket.getVersion());
            packet.put("Header Length", temppacket.getIhl());
            packet.put("Type of Service", temppacket.getTos());
            packet.put("Total Length", temppacket.getTotalLength());
            packet.put("Identification", temppacket.getIdentification());
            packet.put("Flags", temppacket.getFlags());
            packet.put("Fragment", temppacket.getFragment());
            packet.put("Time to Live", temppacket.getTtl());
            packet.put("Protocol", temppacket.getProtocol());
            packet.put("Header Checksum", temppacket.getHeaderCheck());
            packet.put("Source", temppacket.getSource());
            packet.put("Destination", temppacket.getDestination());
            return temppacket;
        }
        else{
            throw new DeviceTypeException();
        }
        Packetv4 temppacket = new Packetv4(new BitString(100, true), new BitString(10100000, true), header, ToS, length, id, flag, frag, new BitString(1010, true), proto, source, bitadd);
    }
    public Dictionary<String, BitString> getPacket(){
        return packet;
    }
    private BitString getVersion(){
        return version;
    }
    private BitString getIhl(){
        return ihl;
    }
    private BitString getTos(){
        return tos;
    }
    private BitString getTotalLength(){
        return totalLength;
    }
    private BitString getIdentification(){
        return identification;
    }
    private BitString getFlags(){
        return flags;
    }
    private BitString getFragment(){
        return fragment;
    }
    private BitString getTtl(){
        return ttl;
    }
    private BitString getProtocol(){
        return protocol;
    }
    private BitString getHeaderCheck(){
        return headerCheck;
    }
    private BitString getSource(){
        return source;
    }
    private BitString getDestination(){
        return destination;
    }
}

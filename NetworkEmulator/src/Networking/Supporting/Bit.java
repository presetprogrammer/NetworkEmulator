package Networking.Supporting;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Bit {
    private int bit;
    public Bit(Integer i){
        bit = i;
    }
    @Contract("_ -> new")
    public static @NotNull Bit toBit(String s){
        return new Bit(checkBit(Integer.parseInt(s)));
    }
    @Contract("_ -> new")
    public static @NotNull Bit toBit(int i){
        return new Bit(checkBit(i));
    }
    @Contract("_ -> new")
    public static @NotNull Bit toBit(Byte b){
        return new Bit(checkBit(Integer.valueOf(b)));
    }
    private static int checkBit(Integer i){
        if(Integer.toString(i).length() > 1) throw new NumberFormatException("Multiple bits used. Use 'Bitstring' for multiple bits.");
        if(!(i == 0 || i == 1)) throw new NumberFormatException("Outside bit range. Expected: 0, 1. Actual: " + i);
        return i;
    }
    public void setBit(int i){
        bit = checkBit(i);
    }
    public Integer toInteger(){
        return bit;
    }
}
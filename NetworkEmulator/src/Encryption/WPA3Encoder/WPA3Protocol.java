package Encryption.WPA3Encoder;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class WPA3Protocol {
    //--------------------------------ENCRYPT--------------------------------//
    public static @NotNull BigInteger encrypt(@NotNull Scanner filein){
        String line = filein.nextLine();
        return BigInteger.valueOf(line.charAt(0));
    }
    public static @NotNull BigInteger encrypt(@NotNull String text){
        return BigInteger.valueOf(text.charAt(0));
    }
    @Contract(pure = true)
    public static BigInteger encrypt(@NotNull ArrayList<BigInteger> al){
        return al.get(0);
    }
    //--------------------------------DECRYPT--------------------------------//
    public static @NotNull BigInteger decrypt(@NotNull Scanner filein){
        String line = filein.nextLine();
        return BigInteger.valueOf(line.charAt(0));
    }
    public static @NotNull BigInteger decrypt(@NotNull String text){
        return BigInteger.valueOf(text.charAt(0));
    }
    @Contract(pure = true)
    public static BigInteger decrypt(@NotNull ArrayList<BigInteger> al){
        return al.get(0);
    }
    //--------------------------------HANDSHAKE--------------------------------//
    @Contract(pure = true)
    public static @NotNull BigInteger handshake(){
        return BigInteger.valueOf(3);
    }
}

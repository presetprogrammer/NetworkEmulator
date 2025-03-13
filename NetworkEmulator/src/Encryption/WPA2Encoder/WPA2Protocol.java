package Encryption.WPA2Encoder;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

public class WPA2Protocol {
    @Contract(pure = true)
    public static @NotNull BigInteger encrypt(){
        return BigInteger.valueOf(3);
    }
    @Contract(pure = true)
    public static @NotNull BigInteger decrypt(){
        return BigInteger.valueOf(3);
    }
    @Contract(pure = true)
    public static @NotNull BigInteger handshake(){
        return BigInteger.valueOf(3);
    }
}

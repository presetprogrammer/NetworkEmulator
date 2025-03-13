package Encryption.EncoderSupport;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.ArrayList;

public class IntegerPlay {
    /**
     * Translates symbols to decimal Unicode using {@code BigInteger}.
     * @param typeint {@code String} input to translate.
     * @return {@code ArrayList<BigInteger>} of translated {@code BigIntegers}.
     */
    public static @NotNull ArrayList<BigInteger> getIntegers(@NotNull String typeint) {
        StringBuilder intset = new StringBuilder();
        ArrayList<Integer> intin = new ArrayList<>();
        for(int x = 0; x < typeint.length(); x++){
            if(typeint.charAt(x) != ' '){
                intset.append(typeint.charAt(x));
            }
            else{
                intin.add(Integer.parseInt(intset.toString()));
                intset = new StringBuilder();
            }
        }
        if(!intset.isEmpty()){
            intin.add(Integer.parseInt(intset.toString()));
        }
        ArrayList<BigInteger> retval = new ArrayList<>();
        for(Integer val : intin){
            retval.add(BigInteger.valueOf(val));
        }
        return retval;
    }

    /**
     * Returns Greatest Common Divisor of two numbers.
     * @param x first {@code Integer}
     * @param y second {@code Integer}
     * @return {@code Integer} Greatest Common Divisor for x and y.
     */
    public static BigInteger gcd(BigInteger x, @NotNull BigInteger y){
        return (y.equals(BigInteger.valueOf(0))) ? x : gcd(y, x.mod(y));
    }
}

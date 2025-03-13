package Networking.Supporting;

import org.jetbrains.annotations.NotNull;

public class BitString{
    private Bit[] bitstring;

    public BitString(int sizeVal, boolean isValue) {
        if(isValue){
            String s = String.valueOf(sizeVal);
            for(int i = 0; i < s.length(); i++) if(!(s.charAt(i) == 0 || s.charAt(i) == 1)) throw new NumberFormatException("Illegal Format for Bit.");
            bitstring = new Bit[s.length()];
            for(int i = 0; i < s.length(); i++) bitstring[i] = Bit.toBit(s.charAt(i));
        }
        else {
            bitstring = new Bit[sizeVal];
            for (int a = 0; a < bitstring.length; a++) {
                bitstring[a] = Bit.toBit(0);
            }
        }
    }
    public static @NotNull BitString toBits(Byte b){
        BitString bs = new BitString();
        int a = Byte.toUnsignedInt(b);
        int check = 1;
        int count = 0;
        while(check < a) {check *= 2; count++;}
        if(check > a) check /= 2;
        while(a != 0){
            if(a - check >= 0) {bs.addBit(Bit.toBit(1)); a -= check;
            }
            else {bs.addBit(Bit.toBit(0));
            }
            check /= 2;
            count--;
        }
        if(count != 0){
            while(count > 0){
                bs.addBit(Bit.toBit(0));
                count--;
            }
        }
        return bs;
    }

    /**
     * Creates {@link BitString} with a default size of 0.
     * Note: Using setBit or setBits without first adding a {@link Bit} will result in an {@link IndexOutOfBoundsException}
     */
    public BitString(){
        bitstring = new Bit[0];
    }

    public static BitString toBitString(int num){
        String s = String.valueOf(num);
        for(int i = 0; i < s.length(); i++) if(!(s.charAt(i) == 0 || s.charAt(i) == 1)) throw new NumberFormatException("Illegal Format for Bit.");
        return new BitString(num, true);
    }

    /**
     * Adds {@link Bit} to the end of the {@link BitString}.
     * @param b {@link Bit} to append to {@link BitString}.
     */
    public void addBit(Bit b){
        BitString tempstring = new BitString(bitstring.length+1, false);
        tempstring.setBit(tempstring.length()-1, b);
        bitstring = tempstring.bitstring;
    }

    /**
     * Add multiple {@link Bit} Objects to the end of the given {@link BitString}.
     * @param b {@code Bit[]} to append to {@link BitString}.
     */
    public void addBits(Bit[] b){
        int count = 0;
        for(Bit bit : bitstring){
            if (bit == null) {
                count++;
            }
        }
        if(count < b.length){
            int size = (bitstring.length-count) + b.length;
            Bit[] temp = new Bit[size];
            System.arraycopy(bitstring, 0, temp, 0, bitstring.length);
            bitstring = appendBits(temp, b);
        }
        else{
            appendBits(bitstring, b);
        }
    }


    /**
     * Sets {@link Bit} at given location to the given {@link Bit}.
     * @param loc {@code int} index to set {@link Bit} at.
     * @param val {@link Bit} value to set.
     * @throws IndexOutOfBoundsException if {@code loc} is greater than or equal to the length of the {@link BitString}.or if {@link BitString} is initialized without parameters.
     */
    public void setBit(int loc, Bit val){
        bitstring[loc] = val;
    }

    /**
     * Sets {@link Bit} values starting at the given start index and ending at the given end index.
     * Bits are set to the {@link Bit} value given.
     * @param start {@code int} start index (inclusive).
     * @param end {@code int} end index (exclusive).
     * @param val {@link Bit} value to set.
     * @throws IndexOutOfBoundsException if {@code start} is less than 0, or {@code start} or {@code end} is greater than or equal to the length of the {@link BitString}.
     */
    public void setBits(int start, int end, Bit val){
        for(; start < end; start++){
            bitstring[start] = val;
        }
    }


    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Bit bit : bitstring) {
            sb.append(bit.toInteger());
        }
        return sb.toString();
    }
    public int toInteger(){
        int total = 0;
        for(int a = 0; a < bitstring.length; a++){
            total += (bitstring[a].equals(Bit.toBit(1)) ? ((int) Math.pow(2, bitstring.length-(a+1))) : 0);
        }
        return total;
    }
    public String toHex(){
        int i = toInteger();
        int f = 0;
        StringBuilder sb = new StringBuilder();
        while(i > 15){
            i -= 16;
            sb.append(f);
        }
        switch(i){
            case 0:
                break;
            case 10:
                sb.append("a");
                break;
            case 11:
                sb.append("b");
                break;
            case 12:
                sb.append("c");
                break;
            case 13:
                sb.append("d");
                break;
            case 14:
                sb.append("e");
                break;
            case 15:
                sb.append("f");
        }
        return sb.toString();
    }

    /**
     * Returns the {@link Bit} array of the {@link BitString}.
     * @return {@code Bit[]}  {@link Bit} array.
     */
    public Bit[] getBitstring() {
        return bitstring;
    }

    /**
     * Returns the length of the {@link BitString}.
     * @return {@code int} length.
     */
    public int length(){
        return bitstring.length;
    }


    @org.jetbrains.annotations.Contract("_, _ -> param1")
    private Bit[] appendBits(Bit @NotNull [] src, Bit[] vals){
        int count = 0;
        for(int i = 0; i < src.length; i++){
            if(src[i] == null){
                src[i] = vals[count];
            }
        }
        return src;
    }
}

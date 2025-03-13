package Networking;

import Networking.Supporting.IllegalAddressException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class IPv6 extends IP{
    public String tempaddr;
    private IPv6(String address){
        tempaddr = address;
    }

    /**
     * Creates IPv6 address based on what is given. NetworkEmulator purpose of the function is to verify IPv6 format with its uses.
     * @param hex {@link String} Properly formatted {@code String} to convert.
     * @return {@link IPv6} verified address.
     * @throws IllegalAddressException if address is not valid.
     */
    @Contract("_ -> new")
    public static @NotNull IPv6 toIPv6(@NotNull String hex) throws IllegalAddressException {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        int classes = 0;
        for(int x = 0; x < hex.length(); x++){
            if(Character.isDigit(hex.charAt(x)) || (hex.charAt(x) > 97 && hex.charAt(x) < 102) || hex.charAt(x) > 65 && hex.charAt(x) < 70) temp.append(hex.charAt(x));
            else if(hex.charAt(x) == ':'){
                if(!temp.isEmpty()) {
                    if(!(temp.length() == 4)) throw new IllegalAddressException("Invalid format at " + temp);
                    stringBuilder.append(temp);
                    classes++;
                    temp = new StringBuilder();
                }
                stringBuilder.append(':');
            }
            else if(hex.charAt(x) == '%' && classes == 4){
                break;
            }
            else throw new IllegalAddressException("Invalid character format.");
        }
        if(!temp.isEmpty()){
            if(!(temp.length() == 4)) throw new IllegalAddressException("Invalid format at " + temp);
            else{
                stringBuilder.append(temp);
                classes++;
            }
        }
        if(classes != 5) throw new IllegalAddressException("Bad number of classes. Expected: 5, Actual: " + classes);
        return new IPv6(stringBuilder.toString());
    }

    /**
     * Reformats the address into a {@code String}.
     * @return {@link String} value of address.
     */
    public String toString(){ return tempaddr; }
}
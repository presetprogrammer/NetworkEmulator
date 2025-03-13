package Networking;

import Networking.Supporting.IllegalAddressException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class IPv4 extends IP{
    private String tempaddr;
    private IPv4(String address){
        tempaddr = address;
    }
    @Contract("_ -> new")
    public static @NotNull IPv4 toIPv4(@NotNull String address) throws IllegalAddressException {
        if(address.indexOf('.') == -1){
            throw new IllegalAddressException("Bad formatting. Use '.' for address delimiter.");
        }
        else{
            StringBuilder num = new StringBuilder();
            int classes = 0;
            for(int x = 0; x < address.length(); x++){
                if(Character.isDigit(address.charAt(x))){
                    num.append(address.charAt(x));
                }
                else if(address.charAt(x) == '.'){
                    if(Integer.parseInt(num.toString()) > 255 || Integer.parseInt(num.toString()) < 0){
                        throw new IllegalAddressException("");
                    }
                    classes++;
                }
                else{
                    throw new IllegalAddressException("Invalid character: " + address.charAt(x) + ".");
                }
            }
            if(!num.isEmpty()){
                if(Integer.parseInt(num.toString()) > 255 || Integer.parseInt(num.toString()) < 0){
                    throw new IllegalAddressException("Character exceeds limit. Expected: 0-255, Actual: " + num);
                }
                classes++;
            }
            if(classes != 4){
                throw new IllegalAddressException("Incorrect number of classes. Expected: 4, Actual: " + classes);
            }
        }
        return new IPv4(address);
    }

    @Override
    public String toString() {
        return tempaddr;
    }
}

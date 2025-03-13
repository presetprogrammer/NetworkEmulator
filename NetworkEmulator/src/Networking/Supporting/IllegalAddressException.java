package Networking.Supporting;

public class IllegalAddressException extends NetworkingException{
    public IllegalAddressException(){
        super();
    }
    public IllegalAddressException(String error){
        super(error);
    }
}

package Backend;

import Encryption.EncoderSupport.ServerType;

public class WebServer extends Server{

    public WebServer(String name, String pass){
        super(name, pass, ServerType.WEB);
    }
    public String getName(){
        return name;
    }
    public void setRouter(){

    }
}

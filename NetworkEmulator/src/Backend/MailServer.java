package Backend;

import Encryption.EncoderSupport.ServerType;

public class MailServer extends Server{
    public MailServer(String name, String password) {
        super(name, password, ServerType.EMAIL);
    }

    public void setRouter(Router r) {
        this.router = r;
    }

    public ServerType getType() {
        return type;
    }
}
package Backend;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Authenticator{
    private Authenticator(String authKey, AAAServer server) throws FileNotFoundException {
        Scanner passCheck = new Scanner(server.getPassList(authKey));
        try{
            passCheck = new Scanner(server.getPassList(authKey));
        }
        catch (SecurityException se){
            System.out.println("Access Denied.");
        }
        String actual = passCheck.nextLine();
        if(authKey.equals(actual));
        else throw new SecurityException("UNAUTHORIZED ACCESS.");
    }
}

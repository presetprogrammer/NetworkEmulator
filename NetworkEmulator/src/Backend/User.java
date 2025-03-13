package Backend;

public class User {
    String name = null;
    String password = null;
    boolean isAdmin = false;
    public User(String username, String password, boolean administrator){
        this.name = username;
        this.password = password;
        this.isAdmin = administrator;
    }
    public String getUsername(){
        return name;
    }
}

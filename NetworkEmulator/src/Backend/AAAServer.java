package Backend;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class AAAServer {
    private String authKey;
    private File passList;

    public File getPassList(@NotNull String authKey) {
        if(authKey.equals(this.authKey)) return passList;
        else throw new SecurityException();
    }
}

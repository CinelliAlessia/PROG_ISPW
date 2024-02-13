package engineering.exceptions;

import java.io.Serial;

public class UserDoesNotExist extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public UserDoesNotExist() {
        super("Nessun account registrato con questa email !");
    }
}
package engineering.exceptions;

import java.io.Serial;

public class UserDoesNotExistException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public UserDoesNotExistException() {
        super("Nessun account registrato con questa email !");
    }
}
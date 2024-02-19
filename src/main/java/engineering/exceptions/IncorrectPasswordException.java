package engineering.exceptions;

import java.io.Serial;

public class IncorrectPasswordException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public IncorrectPasswordException() {
        super("Incorrect password!");
    }
}

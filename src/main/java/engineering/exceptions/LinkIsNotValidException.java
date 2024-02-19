package engineering.exceptions;

import java.io.Serial;

public class LinkIsNotValidException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public LinkIsNotValidException() {
        super("The link is not valid!");
    }
}

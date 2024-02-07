package engineering.exceptions;

import java.io.Serial;

public class LinkIsNotValid extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public LinkIsNotValid(String message) {
        super(message);
    }
}

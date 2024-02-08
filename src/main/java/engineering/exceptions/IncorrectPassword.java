package engineering.exceptions;

import java.io.Serial;

public class IncorrectPassword extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public IncorrectPassword() {
        super("Password errata!");
    }
}

package engineering.exceptions;

import java.io.Serial;

public class PasswordErrata extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public PasswordErrata() {
        super("Le password non corrispondo!");
    }
}

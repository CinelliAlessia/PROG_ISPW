package engineering.exceptions;

import java.io.Serial;

public class InvalidEmailException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidEmailException() {
        super("Invalid email entered !");
    }

}

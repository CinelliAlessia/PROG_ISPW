package engineering.exceptions;

import java.io.Serial;

public class EmailIsNotValid extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;

    public EmailIsNotValid() {
        super("Email inserita non valida!");
    }

}

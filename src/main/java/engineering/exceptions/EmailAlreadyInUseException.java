package engineering.exceptions;

import java.io.Serial;

public class EmailAlreadyInUseException extends Exception {
        @Serial
        private static final long serialVersionUID = 1L;

        public EmailAlreadyInUseException() {
            super("Email already registered!");
        }
}


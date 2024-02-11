package engineering.exceptions;

import java.io.Serial;

public class EmailAlreadyInUse extends Exception {
        @Serial
        private static final long serialVersionUID = 1L;

        public EmailAlreadyInUse() {
            super("Email già registrata!");
        }
}


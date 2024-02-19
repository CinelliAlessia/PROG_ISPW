package engineering.exceptions;

public class EmailAlreadyInUseException extends Exception {
        public EmailAlreadyInUseException() {
            super("Email already registered!");
        }
}


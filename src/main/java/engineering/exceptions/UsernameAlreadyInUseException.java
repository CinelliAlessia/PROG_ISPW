package engineering.exceptions;

public class UsernameAlreadyInUseException extends Exception {
    public UsernameAlreadyInUseException() {
        super("Username already in use!");
    }
}

package engineering.exceptions;

public class UserDoesNotExistException extends Exception {
    public UserDoesNotExistException() {
        super("No accounts registered with this email!");
    }
}
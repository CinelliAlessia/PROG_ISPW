package engineering.exceptions;

public class IncorrectPasswordException extends Exception {
    public IncorrectPasswordException() {
        super("The provided password is wrong!");
    }
}

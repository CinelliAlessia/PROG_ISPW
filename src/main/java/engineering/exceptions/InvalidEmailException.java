package engineering.exceptions;

public class InvalidEmailException extends Exception{
    public InvalidEmailException() {
        super("Invalid email entered!");
    }

}

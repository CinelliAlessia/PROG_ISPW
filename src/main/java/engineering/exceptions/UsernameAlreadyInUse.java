package engineering.exceptions;

public class UsernameAlreadyInUse extends Exception {
    public UsernameAlreadyInUse() {
        super("Username already in use!");
    }
}

package engineering.exceptions;

public class PlaylistNameAlreadyInUseException extends Exception {
    public PlaylistNameAlreadyInUseException() {
        super("This title is already used!");
    }
}

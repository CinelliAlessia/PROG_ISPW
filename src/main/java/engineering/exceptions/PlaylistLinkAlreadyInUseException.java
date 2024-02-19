package engineering.exceptions;

public class PlaylistLinkAlreadyInUseException extends Exception {
    public PlaylistLinkAlreadyInUseException() {
        super("This link is already used!");
    }
}

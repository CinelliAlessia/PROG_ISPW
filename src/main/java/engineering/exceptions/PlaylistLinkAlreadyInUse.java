package engineering.exceptions;

public class PlaylistLinkAlreadyInUse extends Exception {
    public PlaylistLinkAlreadyInUse() {
        super("This link is already in use!");
    }
}

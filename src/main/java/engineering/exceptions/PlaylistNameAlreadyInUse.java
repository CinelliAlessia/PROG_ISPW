package engineering.exceptions;

public class PlaylistNameAlreadyInUse extends Exception {
    public PlaylistNameAlreadyInUse() {
        super("This title is already used!");
    }
}

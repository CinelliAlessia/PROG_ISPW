package engineering.exceptions;

public class PlaylistNameAlreadyInUse extends Throwable {
    public PlaylistNameAlreadyInUse(String message) {
        super(message);
    }
}

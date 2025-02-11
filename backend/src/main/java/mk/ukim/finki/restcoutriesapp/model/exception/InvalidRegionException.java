package mk.ukim.finki.restcoutriesapp.model.exception;

public class InvalidRegionException extends RuntimeException {
    public InvalidRegionException(String message) {
        super(message);
    }

    public InvalidRegionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRegionException(Throwable cause) {
        super(cause);
    }
}

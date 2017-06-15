import java.lang.Exception;

public class InvalidMoveException extends Exception {
    public InvalidMoveException() {
        super();
        }

    public InvalidMoveException(String msg) {
        super(msg);
    }

    public InvalidMoveException(String msg, Throwable thrbl) {
        super(msg, thrbl);
    }

    public InvalidMoveException(Throwable thrbl) {
        super(thrbl);
    }
}
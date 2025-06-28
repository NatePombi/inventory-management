package exceptions;

public class NoItemPresentException extends RuntimeException {
    public NoItemPresentException() {
        super("No item present!");
    }
}

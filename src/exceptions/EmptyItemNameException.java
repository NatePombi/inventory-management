package exceptions;

public class EmptyItemNameException extends RuntimeException {
    public EmptyItemNameException() {
        super("No item name present!");
    }
}

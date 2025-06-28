package exceptions;

public class ItemAlreadyExistsException extends RuntimeException {
    public ItemAlreadyExistsException() {
        super("Item already exists!");
    }
}

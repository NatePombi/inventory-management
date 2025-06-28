package exceptions;

public class DataCorruptionException extends RuntimeException{

    public DataCorruptionException(String message){
        super(message);
    }

    public DataCorruptionException(String message, Throwable cause){
        super(message,cause);
    }

}

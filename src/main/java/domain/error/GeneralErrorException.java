package domain.error;

public class GeneralErrorException extends RuntimeException{
    public GeneralErrorException(String message) {
        super(message);
    }
}

package domain;

public class GeneralErrorException extends RuntimeException{
    public GeneralErrorException(String message) {
        super(message);
    }
}

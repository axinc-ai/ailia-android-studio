package axip.ailia;

public class AiliaInvalidArgumentException extends AiliaException {
    public AiliaInvalidArgumentException(String message) {
        super(message, AiliaStatus.INVALID_ARGUMENT);
    }
}

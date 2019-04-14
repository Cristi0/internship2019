package Except;

/**
 * Exception for the card
 */
public class CardException extends Exception {
    String message;

    public CardException(String message) {
        super(message);
        this.message=message;
    }

    /**
     * Get the message
     * @return
     */
    @Override
    public String getMessage() {
        return message;
    }
}

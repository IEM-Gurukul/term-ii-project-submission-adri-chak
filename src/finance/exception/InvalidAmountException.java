package finance.exception;

// CUSTOM EXCEPTION - extends Exception
// This is INHERITANCE - we are extending Java's built-in Exception class
public class InvalidAmountException extends Exception {

    // Constructor - passes the error message to parent Exception class
    public InvalidAmountException(String message) {
        super(message);
    }
}
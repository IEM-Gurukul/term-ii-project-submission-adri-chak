package finance.exception;

// CUSTOM EXCEPTION - extends Exception
// Same pattern as InvalidAmountException
public class InvalidDateException extends Exception {

    // Constructor - passes the error message to parent Exception class
    public InvalidDateException(String message) {
        super(message);
    }
}
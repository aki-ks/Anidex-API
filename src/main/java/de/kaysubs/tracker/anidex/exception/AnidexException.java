package de.kaysubs.tracker.anidex.exception;

public class AnidexException extends RuntimeException {

    public AnidexException() {
        super();
    }

    public AnidexException(String message) {
        super(message);
    }

    public AnidexException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnidexException(Throwable cause) {
        super(cause);
    }

    public AnidexException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

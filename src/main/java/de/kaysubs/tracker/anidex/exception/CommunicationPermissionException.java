package de.kaysubs.tracker.anidex.exception;

public class CommunicationPermissionException extends AnidexException {

    public CommunicationPermissionException() {
        super();
    }

    public CommunicationPermissionException(String message) {
        super(message);
    }

    public CommunicationPermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommunicationPermissionException(Throwable cause) {
        super(cause);
    }

    public CommunicationPermissionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

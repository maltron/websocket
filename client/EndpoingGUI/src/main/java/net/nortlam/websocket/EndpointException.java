package net.nortlam.websocket;

public class EndpointException extends Exception {

    public EndpointException() {
    }

    public EndpointException(String message) {
        super(message);
    }

    public EndpointException(String message, Throwable cause) {
        super(message, cause);
    }

    public EndpointException(Throwable cause) {
        super(cause);
    }

    public EndpointException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

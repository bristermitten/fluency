package me.bristermitten.fluency.button.distribution;

public class DistributionNotInitialisedException extends RuntimeException {
    public DistributionNotInitialisedException() {
    }

    public DistributionNotInitialisedException(String message) {
        super(message);
    }

    public DistributionNotInitialisedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DistributionNotInitialisedException(Throwable cause) {
        super(cause);
    }

    public DistributionNotInitialisedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package fr.esgi.rocket.add;

public class StagingException extends RuntimeException {

    public StagingException(final String message) {
        super(message);
    }

    public StagingException(final Throwable throwable) {
        super(throwable);
    }
}

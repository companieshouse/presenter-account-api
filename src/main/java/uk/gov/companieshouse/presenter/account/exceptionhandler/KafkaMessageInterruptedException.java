package uk.gov.companieshouse.presenter.account.exceptionhandler;

public class KafkaMessageInterruptedException extends RuntimeException {

    private static final long serialVersionUID = 8631969689253132097L;

    public KafkaMessageInterruptedException() {
        super();
    }

    public KafkaMessageInterruptedException(final String message) {
        super(message);
    }

    public KafkaMessageInterruptedException(final Throwable cause) {
        super(cause);
    }

    public KafkaMessageInterruptedException(final String message, Throwable cause) {
        super(message, cause);
    }
}
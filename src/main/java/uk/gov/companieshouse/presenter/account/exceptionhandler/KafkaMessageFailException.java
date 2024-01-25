package uk.gov.companieshouse.presenter.account.exceptionhandler;

public class KafkaMessageFailException extends RuntimeException {

    private static final long serialVersionUID = 8631969689253132097L;

    public KafkaMessageFailException() {
        super();
    }

    public KafkaMessageFailException(final String message) {
        super(message);
    }

    public KafkaMessageFailException(final Throwable cause) {
        super(cause);
    }

    public KafkaMessageFailException(final String message, Throwable cause) {
        super(message, cause);
    }
}

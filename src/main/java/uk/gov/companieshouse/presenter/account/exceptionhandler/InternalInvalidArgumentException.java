package uk.gov.companieshouse.presenter.account.exceptionhandler;

public class InternalInvalidArgumentException extends RuntimeException {
    
    private static final long serialVersionUID = 8631969689253132097L;

    public InternalInvalidArgumentException() {
        super();
    }

    public InternalInvalidArgumentException(final String message) {
        super(message);
    }

    public InternalInvalidArgumentException(final Throwable cause) {
        super(cause);
    }

    public InternalInvalidArgumentException(final String message, Throwable cause) {
        super(message, cause);
    }
    
}

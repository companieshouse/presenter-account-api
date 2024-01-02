package uk.gov.companieshouse.presenter.account.exceptionhandler;

public class ValidationException extends RuntimeException {
    
    private static final long serialVersionUID = 8631969689253132097L;

    public ValidationException() {
        super();
    }

    public ValidationException(final String message) {
        super(message);
    }

    public ValidationException(final Throwable cause) {
        super(cause);
    }

    public ValidationException(final String message, Throwable cause) {
        super(message, cause);
    }

}

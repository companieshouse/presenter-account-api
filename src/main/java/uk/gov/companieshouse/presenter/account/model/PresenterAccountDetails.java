package uk.gov.companieshouse.presenter.account.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;
import uk.gov.companieshouse.presenter.account.validation.utils.EmailValidator;
import uk.gov.companieshouse.presenter.account.validation.utils.StringValidator;

@Document("presenter_account")
public record PresenterAccountDetails(
        @Id String presenterDetailsId,
        @Field("chsUserId") String userId,
        @Field("email") String email,
        @Field("name") PresenterAccountName name,
        @Field("address") PresenterAccountAddress address) {

    private static final int MAX_EMAIL_LENGTH = 80;
    private static final int USER_ID_LENGTH = 40;

    public PresenterAccountDetails(
            final String presenterDetailsId,
            final String userId,
            final String email,
            final PresenterAccountName name,
            final PresenterAccountAddress address) {
        this.presenterDetailsId = presenterDetailsId;
        this.userId = validateUserId(userId);
        this.email = validateEmail(email);
        this.name = validateName(name);
        this.address = validateAddress(address);
    }

    private String validateUserId(final String id) {

        if (StringValidator.validateString(id, USER_ID_LENGTH)) {
            return id;
        } else {
            throw new ValidationException("user id failed validation");
        }
    }

    private String validateEmail(final String email) {

        if (EmailValidator.validateEmail(email, MAX_EMAIL_LENGTH)) {
            return email;
        } else {
            throw new ValidationException("email failed validation");
        }
    }

    private PresenterAccountName validateName(final PresenterAccountName name) {
        if (name != null) {
            return name;
        } else {
            throw new ValidationException("presenter name can not be null");
        }
    }

    private PresenterAccountAddress validateAddress(final PresenterAccountAddress address) {
        if (address != null) {
            return address;
        } else {
            throw new ValidationException("presenter address can not be null");
        }
    }

    @Override
    public String toString() {
        return "PresenterAccountDetails []";
    }

}

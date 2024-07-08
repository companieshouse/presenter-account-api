package uk.gov.companieshouse.presenter.account.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;
import uk.gov.companieshouse.presenter.account.validation.utils.EmailValidator;

@Document("presenter_account")
public record PresenterAccountDetails(
        @Id String presenterDetailsId,
        @Field("chsUserId") String userId,
        @Field("lang") String lang,
        @Field("email") String email,
        @Field("name") PresenterAccountName name,
        @Field("address") PresenterAccountAddress address) {

    private static final int MAX_EMAIL_LENGTH = 80;
    private static final int USER_ID_LENGTH = 40;

    public PresenterAccountDetails(
            final String presenterDetailsId,
            final String userId,
            final String lang,
            final String email,
            final PresenterAccountName name,
            final PresenterAccountAddress address) {
        this.presenterDetailsId = presenterDetailsId;
        this.userId = validateUserId(userId);
        this.lang = validateLang(lang);
        this.email = validateEmail(email);
        this.name = validateName(name);
        this.address = validateAddress(address);
    }

    private String validateUserId(final String id) {

        if (isIdCorrectFormat(id)) {
            return id;
        } else {
            throw new ValidationException("user id is invalid");
        }
    }

    private String validateEmail(final String email) {

        if (EmailValidator.validateEmail(email, MAX_EMAIL_LENGTH)) {
            return email;
        } else {
            throw new ValidationException("email failed validation");
        }
    }

    private String validateLang(final String lang) {

        if (lang.equalsIgnoreCase("en") || lang.equalsIgnoreCase("cy")) {
            return lang;
        } else {
            throw new ValidationException("language validation is failed");
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

    private boolean isIdCorrectFormat(String id){
        return id != null && !id.isBlank() && id.length() <= USER_ID_LENGTH;
    }

    @Override
    public String toString() {
        return "PresenterAccountDetails []";
    }

}

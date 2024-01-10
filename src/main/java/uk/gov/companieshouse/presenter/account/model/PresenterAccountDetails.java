package uk.gov.companieshouse.presenter.account.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;
import uk.gov.companieshouse.presenter.account.validation.utils.EmailValidator;
import uk.gov.companieshouse.presenter.account.validation.utils.StringValidator;

@Document("presenter_account")
public class PresenterAccountDetails {

    private static final int MAX_EMAIL_LENGTH = 80;
    private static final int USER_ID_LENGTH = 40;

    @Id
    private String presenterDetailsId;

    @Field("chsUserId")
    private String userId;

    @Field("email")
    private String email;

    @Field("name")
    private PresenterAccountName name;

    @Field("address")
    private PresenterAccountAddress address;

    public PresenterAccountDetails() {

    }

    public PresenterAccountDetails(final String userId, final String email, final PresenterAccountName name,
                                   final PresenterAccountAddress address) {
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

    public String getPresenterDetailsId() {
        return presenterDetailsId;
    }

    public void setPresenterDetailsId(String presenterDetailsId) {
        this.presenterDetailsId = presenterDetailsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PresenterAccountName getName() {
        return name;
    }

    public void setName(PresenterAccountName name) {
        this.name = name;
    }

    public PresenterAccountAddress getAddress() {
        return address;
    }

    public void setAddress(PresenterAccountAddress address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((presenterDetailsId == null) ? 0 : presenterDetailsId.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PresenterAccountDetails other = (PresenterAccountDetails) obj;
        if (presenterDetailsId == null) {
            if (other.presenterDetailsId != null)
                return false;
        } else if (!presenterDetailsId.equals(other.presenterDetailsId))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PresenterAccountDetails []";
    }

}

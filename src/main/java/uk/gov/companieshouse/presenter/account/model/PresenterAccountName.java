package uk.gov.companieshouse.presenter.account.model;

import java.util.Optional;

import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;
import uk.gov.companieshouse.presenter.account.model.request.presenter.account.PresenterName;
import uk.gov.companieshouse.presenter.account.validation.utils.StringValidator;

public class PresenterAccountName {

    private static final int MAX_FORENAME_LENGTH = 32;

    private static final int MAX_SURNAME_LENGTH = 40;

    private String forename;
    
    private String surname;

    public static PresenterAccountName createPresenterAccountName(final PresenterName presenterAccountName){
        if(presenterAccountName != null){
            return new PresenterAccountName(presenterAccountName.forename(), presenterAccountName.surname());
        }
        else {
            throw new ValidationException("Presenter full name is missing");
        }
    }

    public PresenterAccountName(final String forename, final String surname) {
        this.forename = validateName(forename, MAX_FORENAME_LENGTH);
        this.surname = validateName(surname, MAX_SURNAME_LENGTH);
    }

    private String validateName(final String name, final int maxLength) {
        Optional<String> optionalName = StringValidator.validateString(name, maxLength);
        if (optionalName.isPresent()){
            return optionalName.get();
        } else {
            throw new ValidationException("Invalid Name");
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((forename == null) ? 0 : forename.hashCode());
        result = prime * result + ((surname == null) ? 0 : surname.hashCode());
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
        PresenterAccountName other = (PresenterAccountName) obj;
        if (forename == null) {
            if (other.forename != null)
                return false;
        } else if (!forename.equals(other.forename))
            return false;
        if (surname == null) {
            if (other.surname != null)
                return false;
        } else if (!surname.equals(other.surname))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PresenterName []";
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    
}

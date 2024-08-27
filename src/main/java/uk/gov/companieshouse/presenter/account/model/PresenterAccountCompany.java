package uk.gov.companieshouse.presenter.account.model;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.lang.Nullable;

import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;
import uk.gov.companieshouse.presenter.account.validation.utils.StringValidator;

public record PresenterAccountCompany(
    @Field("companyName") String companyName,
    @Field("companyNumber") @Nullable String companyNumber
    ) {
    
    private static final int MAX_COMPANY_NAME_LENGTH = 80;
    private static final int MAX_COMPANY_NUMBER_LENGTH = 8;

    public PresenterAccountCompany(final String companyName, final String companyNumber) {
        this.companyName = validateCompanyName(companyName);
        this.companyNumber = validateCompanyNumber(companyNumber);

    }

    private String validateCompanyName(final String name) {
        if(StringValidator.validateString(name, MAX_COMPANY_NAME_LENGTH)) {
            return name;
        }
        throw new ValidationException("Invalid company name");
    }

    private String validateCompanyNumber(final String number) {
        if (number == null || StringValidator.validateString(number, MAX_COMPANY_NUMBER_LENGTH)) {
            return number;
        }

        throw new ValidationException("Invalid company number");
    }
}   

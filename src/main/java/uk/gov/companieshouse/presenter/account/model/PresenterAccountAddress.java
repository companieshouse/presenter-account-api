package uk.gov.companieshouse.presenter.account.model;

import org.springframework.data.mongodb.core.mapping.Field;

import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;
import uk.gov.companieshouse.presenter.account.validation.utils.StringValidator;

public class PresenterAccountAddress {

    private static final int PREMISES_MAX_LENGTH = 40;
    private static final int LINE_1_MAX_LENGTH = 40;
    private static final int LINE_2_MAX_LENGTH = 40;
    private static final int COUNTY_MAX_LENGTH = 40;
    private static final int COUNTRY_MAX_LENGTH = 40;
    private static final int POSTCODE_MAX_LENGTH = 10;

    @Field
    private final String premises;

    @Field
    private final String addressLine1;

    @Field
    private final String addressLine2;

    @Field
    private final String county;

    @Field
    private final String country;

    @Field("postCode")
    private final String postcode;

    public PresenterAccountAddress(String premises, String addressLine1, String addressLine2, String county,
            String country,
            String postcode) {
        this.premises = validatePremises(premises);
        this.addressLine1 = validateAddressLine1(addressLine1);
        this.addressLine2 = validateAddressLine2(addressLine2);
        this.county = validatedCounty(county);
        this.country = validateCountry(country);
        this.postcode = validatePostcode(postcode);
    }

    private String validatePremises(String premises) {
        return getValidatedLine(premises, PREMISES_MAX_LENGTH, "premises failed validation");
    }

    private String validateAddressLine1(String line) {
        return getValidatedLine(line, LINE_1_MAX_LENGTH, "address line 1 failed validation");
    }

    private String validateAddressLine2(String line) {
        if (line == null || line.isBlank()) {
            return "";
        } else {
            return getValidatedLine(line, LINE_2_MAX_LENGTH, "address line 2 failed validation");
        }
    }

    private String validatedCounty(String line) {
        if (line == null || line.isBlank()) {
            return "";
        } else {
            return getValidatedLine(line, COUNTY_MAX_LENGTH, "county failed validation");
        }
    }

    private String validateCountry(String line) {
        return getValidatedLine(line, COUNTRY_MAX_LENGTH, "country failed validation");
    }

    private String validatePostcode(String line) {
        return getValidatedLine(line, POSTCODE_MAX_LENGTH, "postcode failed validation");
    }

    private String getValidatedLine(String line, int maxLength, String validationExceptionMessage) {
        if (StringValidator.validateString(line, maxLength)) {
            return line;
        } else {
            throw new ValidationException(validationExceptionMessage);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((premises == null) ? 0 : premises.hashCode());
        result = prime * result + ((addressLine1 == null) ? 0 : addressLine1.hashCode());
        result = prime * result + ((addressLine2 == null) ? 0 : addressLine2.hashCode());
        result = prime * result + ((county == null) ? 0 : county.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((postcode == null) ? 0 : postcode.hashCode());
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
        PresenterAccountAddress other = (PresenterAccountAddress) obj;
        if (premises == null) {
            if (other.premises != null)
                return false;
        } else if (!premises.equals(other.premises))
            return false;
        if (addressLine1 == null) {
            if (other.addressLine1 != null)
                return false;
        } else if (!addressLine1.equals(other.addressLine1))
            return false;
        if (addressLine2 == null) {
            if (other.addressLine2 != null)
                return false;
        } else if (!addressLine2.equals(other.addressLine2))
            return false;
        if (county == null) {
            if (other.county != null)
                return false;
        } else if (!county.equals(other.county))
            return false;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        if (postcode == null) {
            if (other.postcode != null)
                return false;
        } else if (!postcode.equals(other.postcode))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PresenterAddress";
    }

    public String getPremises() {
        return premises;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getCounty() {
        return county;
    }

    public String getCountry() {
        return country;
    }

    public String getPostcode() {
        return postcode;
    }

}

package uk.gov.companieshouse.presenter.account.model;

import org.springframework.data.mongodb.core.mapping.Field;
import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;
import uk.gov.companieshouse.presenter.account.validation.utils.StringValidator;

public record PresenterAccountAddress(
        @Field("premises") String premises,
        @Field("addressLine1") String addressLine1,
        @Field("addressLine2") String addressLine2,
        @Field("townOrCity") String townOrCity,
        @Field("country") String country,
        @Field("postCode") String postcode) {

    private static final int PREMISES_MAX_LENGTH = 40;
    private static final int LINE_1_MAX_LENGTH = 40;
    private static final int LINE_2_MAX_LENGTH = 40;
    private static final int COUNTY_MAX_LENGTH = 40;
    private static final int COUNTRY_MAX_LENGTH = 40;
    private static final int POSTCODE_MAX_LENGTH = 10;

    public PresenterAccountAddress(
            final String premises,
            final String addressLine1,
            final String addressLine2,
            final String townOrCity,
            final String country,
            final String postcode) {
        this.premises = validatePremises(premises);
        this.addressLine1 = validateAddressLine1(addressLine1);
        this.addressLine2 = validateAddressLine2(addressLine2);
        this.townOrCity = validatedTownOrCity(townOrCity);
        this.country = validateCountry(country);
        this.postcode = validatePostcode(postcode);
    }

    private String validatePremises(final String premises) {
        return getValidatedLine(premises, PREMISES_MAX_LENGTH, "premises failed validation");
    }

    private String validateAddressLine1(final String line) {
        return getValidatedLine(line, LINE_1_MAX_LENGTH, "address line 1 failed validation");
    }

    private String validateAddressLine2(final String line) {
        if (line == null || line.isBlank()) {
            return "";
        } else {
            return getValidatedLine(line, LINE_2_MAX_LENGTH, "address line 2 failed validation");
        }
    }

    private String validatedTownOrCity(final String line) {
        if (line == null || line.isBlank()) {
            return "";
        } else {
            return getValidatedLine(line, COUNTY_MAX_LENGTH, "townOrCity failed validation");
        }
    }

    private String validateCountry(final String line) {
        return getValidatedLine(line, COUNTRY_MAX_LENGTH, "country failed validation");
    }

    private String validatePostcode(final String line) {
        return getValidatedLine(line, POSTCODE_MAX_LENGTH, "postcode failed validation");
    }

    private String getValidatedLine(final String line, final int maxLength, final String validationExceptionMessage) {
        if (StringValidator.validateString(line, maxLength)) {
            return line;
        } else {
            throw new ValidationException(validationExceptionMessage);
        }
    }

    @Override
    public String toString() {
        return "PresenterAccountAddress []";
    }

}

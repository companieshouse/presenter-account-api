package uk.gov.companieshouse.presenter.account.model;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;

class PresenterAccountAddressTest {

    private final static String PREMISES = "premise";
    private final static String FIRST_LINE = "first line";
    private final static String SECOND_LINE = "second line";
    private final static String TOWN_OR_CITY = "townOrCity";
    private final static String COUNTRY = "country";
    private final static String POSTCODE = "postcode";
    private final static String STRING_TOO_LONG = "a".repeat(41);
    private final static String INVALID_STRING = "bad string ~";

    PresenterAccountAddress address;

    @BeforeEach
    void BeforeEach() {

    }

    @Test
    @DisplayName("Create a valid presenter address")
    void testValidPresenterAddress() {
        address = new PresenterAccountAddress(PREMISES, FIRST_LINE, SECOND_LINE, TOWN_OR_CITY, COUNTRY, POSTCODE);
    }

    @Test
    @DisplayName("Create a valid presenter address with both second line empty null")
    void testValidPresenterAddressWithoutSecondLine() {
        address = new PresenterAccountAddress(PREMISES, FIRST_LINE, "", TOWN_OR_CITY, COUNTRY, POSTCODE);
    }

    @Test
    @DisplayName("Create a valid presenter address with second line null")
    void testValidPresenterAddressWithSecondLineAsNull() {
        address = new PresenterAccountAddress(PREMISES, FIRST_LINE, null, TOWN_OR_CITY, COUNTRY, POSTCODE);
    }

    @Test
    @DisplayName("Throw ValidationException by creating an invalid presenter address with empty premises")
    void testInvalidPresenterAddressWithoutPremises() {
        Exception e = assertThrows(ValidationException.class, () -> {
            new PresenterAccountAddress("", FIRST_LINE, SECOND_LINE, TOWN_OR_CITY, COUNTRY, POSTCODE);
        });
        assertTrue(e.getMessage().contains("premises"));
    }

    @Test
    @DisplayName("Throw ValidationException by creating an invalid presenter address with empty first line")
    void testInvalidPresenterAddressWithoutFirstLine() {
        Exception e = assertThrows(ValidationException.class, () -> {
            new PresenterAccountAddress(PREMISES, "", SECOND_LINE, TOWN_OR_CITY, COUNTRY, POSTCODE);
        });
        assertTrue(e.getMessage().contains("address line 1"));
    }

    @Test
    @DisplayName("Throw ValidationException by creating an invalid presenter address with empty town or city")
    void testInvalidPresenterAddressWithoutTownOrCity() {
        Exception e = assertThrows(ValidationException.class, () -> {
            new PresenterAccountAddress(PREMISES, FIRST_LINE, SECOND_LINE, "", COUNTRY, POSTCODE);
        });
        assertTrue(e.getMessage().contains("town or city"));
    }

    @Test
    @DisplayName("Throw ValidationException by creating an invalid presenter address with empty country")
    void testInvalidPresenterAddressWithoutCountry() {
        Exception e = assertThrows(ValidationException.class, () -> {
            new PresenterAccountAddress(PREMISES, FIRST_LINE, SECOND_LINE, TOWN_OR_CITY, "", POSTCODE);
        });
        assertTrue(e.getMessage().contains("country"));
    }

    @Test
    @DisplayName("Throw ValidationException by creating an invalid presenter address with empty postcode")
    void testInvalidPresenterAddressWithoutPostcode() {
        Exception e = assertThrows(ValidationException.class, () -> {
            new PresenterAccountAddress(PREMISES, FIRST_LINE, SECOND_LINE, TOWN_OR_CITY, COUNTRY, "");
        });
        assertTrue(e.getMessage().contains("postcode"));
    }

    @Test
    @DisplayName("Throw ValidationException by creating an invalid presenter address with too long premises")
    void testInvalidPresenterAddressWithTooLongPremises() {
        Exception e = assertThrows(ValidationException.class, () -> {
            new PresenterAccountAddress(STRING_TOO_LONG, FIRST_LINE, SECOND_LINE, TOWN_OR_CITY, COUNTRY, POSTCODE);
        });
        assertTrue(e.getMessage().contains("premises"));
    }

    @Test
    @DisplayName("Throw ValidationException by creating an invalid presenter address with too long first line")
    void testInvalidPresenterAddressWithTooLongFirstLine() {
        Exception e = assertThrows(ValidationException.class, () -> {
            new PresenterAccountAddress(PREMISES, STRING_TOO_LONG, SECOND_LINE, TOWN_OR_CITY, COUNTRY, POSTCODE);
        });
        assertTrue(e.getMessage().contains("address line 1"));
    }

    @Test
    @DisplayName("Throw ValidationException by creating an invalid presenter address with too long second line")
    void testInvalidPresenterAddressWithTooLongSecondLine() {
        Exception e = assertThrows(ValidationException.class, () -> {
            new PresenterAccountAddress(PREMISES, FIRST_LINE, STRING_TOO_LONG, TOWN_OR_CITY, COUNTRY, POSTCODE);
        });
        assertTrue(e.getMessage().contains("address line 2"));
    }

    @Test
    @DisplayName("Throw ValidationException by creating an invalid presenter address with too long country")
    void testInvalidPresenterAddressWithTooLongCountry() {
        Exception e = assertThrows(ValidationException.class, () -> {
            new PresenterAccountAddress(PREMISES, FIRST_LINE, SECOND_LINE, TOWN_OR_CITY, STRING_TOO_LONG, POSTCODE);
        });
        assertTrue(e.getMessage().contains("country"));
    }

    @Test
    @DisplayName("Throw ValidationException by creating an invalid presenter address with too long postcode")
    void testInvalidPresenterAddressWithTooLongPostcode() {
        Exception e = assertThrows(ValidationException.class, () -> {
            new PresenterAccountAddress(PREMISES, FIRST_LINE, SECOND_LINE, TOWN_OR_CITY, COUNTRY, STRING_TOO_LONG);
        });
        assertTrue(e.getMessage().contains("postcode"));
    }

    @Test
    @DisplayName("Throw ValidationException by creating an invalid presenter addres with field with invalid character")
    void testInvalidPresenterAddressWithInvalidCharacter() {
        Exception e = assertThrows(ValidationException.class, () -> {
            new PresenterAccountAddress(PREMISES, INVALID_STRING, SECOND_LINE, TOWN_OR_CITY, COUNTRY, POSTCODE);
        });
        assertTrue(e.getMessage().contains("address line 1"));
    }
}

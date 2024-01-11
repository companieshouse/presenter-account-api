package uk.gov.companieshouse.presenter.account.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;
import uk.gov.companieshouse.presenter.account.mapper.request.PresenterAccountNameMapper;
import uk.gov.companieshouse.presenter.account.model.request.PresenterNameRequest;

public class PresenterAccountNameMapperTest {

    PresenterAccountNameMapper mapper;

    private final static String first = "a";
    private final static String last = "b";

    @BeforeEach
    void before() {
        mapper = new PresenterAccountNameMapper();
    }

    @Test
    @DisplayName("Mapping from presenter name to presenter account name")
    void mapperTest() {
        PresenterNameRequest name = new PresenterNameRequest(first, last);
        var accountName = mapper.map(name);
        assertEquals(first, accountName.forename());
        assertEquals(last, accountName.surname());
    }

    @Test
    @DisplayName("Mapping null to presenter account name return ValidationException")
    void mapNullTest() {
        assertThrows(ValidationException.class, () -> mapper.map(null));
    }
}

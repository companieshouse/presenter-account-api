package uk.gov.companieshouse.presenter.account.serialiser;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uk.gov.companieshouse.presenter_account.PresenterCreated;

class PresenterCreatedSerialiserTest {

    private static final String id = "ID";

    PresenterCreatedSerialiser presenterCreatedSerialiser;

    @BeforeEach
    void beforeEach() {
        presenterCreatedSerialiser = new PresenterCreatedSerialiser();
    }

    @Test
    @DisplayName("serialize test")
    void SerializeTest() {
        PresenterCreated presenterCreated = new PresenterCreated();
        presenterCreated.setId(id);
        byte[] serialisedPresenterCreaded = presenterCreatedSerialiser.serialize(presenterCreated);
        assertTrue(serialisedPresenterCreaded instanceof byte[]);
    }
}

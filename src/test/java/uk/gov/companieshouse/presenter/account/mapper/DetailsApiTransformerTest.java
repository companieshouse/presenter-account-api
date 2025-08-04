package uk.gov.companieshouse.presenter.account.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.companieshouse.api.model.presenteraccount.PresenterAccountDetailsApi;
import uk.gov.companieshouse.presenter.account.mapper.response.DetailsApiTransformer;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountAddress;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountCompany;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountDetails;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountName;

class DetailsApiTransformerTest {

    private PresenterAccountDetails presenterAccountDetails;
    private DetailsApiTransformer detailsApiTransformer;

    @BeforeEach
    void before() {
        detailsApiTransformer = new DetailsApiTransformer();
        // Replace these parameters with the actual parameters for PresenterAccountDetails' constructor
        String presenterDetailsId = "9c60fa56-d5c0-4c34-8e53-17699af1191f";
        presenterAccountDetails = new PresenterAccountDetails(
                presenterDetailsId,
                "userId",
                "en",
                "test@example.com",
                new PresenterAccountCompany("company Name", "00006400"),
                new PresenterAccountName("forename", "surname"),
                new PresenterAccountAddress("premises",
                        "addressLine1",
                        "addressLine2",
                        "townOrCity",
                        "country",
                        "postcode"));

    }

    @Test
    void testResponseTransformer() {
        Optional<PresenterAccountDetails> optionalPresenterAccountDetails = Optional.of(
                presenterAccountDetails);
        Optional<PresenterAccountDetailsApi> PresenterAccountDetailsApiOptional = detailsApiTransformer.responseTransformer(
                optionalPresenterAccountDetails);
        assertTrue(PresenterAccountDetailsApiOptional.isPresent());
        assertEquals(presenterAccountDetails.presenterDetailsId(),
                PresenterAccountDetailsApiOptional.get().getPresenterDetailsId());
        assertNull(PresenterAccountDetailsApiOptional.get().getCompany().getCompanyNumber());
        assertNull(PresenterAccountDetailsApiOptional.get().getUserId());
    }

    @Test
    void testResponseTransformerWhenResponseIsEmpty() {
        Optional<PresenterAccountDetails> optionalPresenterAccountDetails = Optional.empty();
        Optional<PresenterAccountDetailsApi> PresenterAccountDetailsApiOptional = detailsApiTransformer.responseTransformer(
                optionalPresenterAccountDetails);
        assertFalse(PresenterAccountDetailsApiOptional.isPresent());
    }
}

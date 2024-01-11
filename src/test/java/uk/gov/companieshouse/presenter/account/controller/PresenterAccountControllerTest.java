package uk.gov.companieshouse.presenter.account.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountAddress;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountDetails;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountName;
import uk.gov.companieshouse.presenter.account.model.request.PresenterAccountDetailsRequest;
import uk.gov.companieshouse.presenter.account.service.PresenterAccountService;

@ExtendWith(MockitoExtension.class)
class PresenterAccountControllerTest {

    private static final String PRESENTER_ACCOUNT = "/presenter-account/";

    PresenterAccountController presenterAccountController;

    @Mock
    PresenterAccountService presenterAccountService;

    @Mock
    PresenterAccountDetailsRequest presenterAccountDetailsRequest;

    @Mock
    Logger logger;

    @BeforeEach
    void setUp() {
        presenterAccountController = new PresenterAccountController(presenterAccountService, logger);
    }

    @Test
    @DisplayName("Return 200 on successfully health check")
    void testHealthCheckEndpointSuccessResponse() {
        var response = presenterAccountController.healthCheck();
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    @DisplayName("Return 201 when valid presenter details is submitted")
    void testCreatePresenterAccountSuccessResponse() {
        final String id = "id";
        when(presenterAccountService.createPresenterAccount(presenterAccountDetailsRequest)).thenReturn(id);

        var response = presenterAccountController.createPresenterAccount(presenterAccountDetailsRequest);
        var header = response.getHeaders().getFirst("Location");
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertNotNull(header);
        assertTrue(header.contains(PRESENTER_ACCOUNT + id));
    }

    // NOT TESTING MISSING NON-OPTIONAL PARAMETER AS THEY ARE CATCH BY MODEL.
    // Will test ValidationException handling
    @Test
    @DisplayName("ValidationException thrown return 400")
    void testThrowValidationExcetionBadRequestResponse() {
        ValidationException e = new ValidationException();

        ResponseEntity<?> response = presenterAccountController.validationException(e);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    @DisplayName("HttpMessageNotReadableException thrown return 400")
    void testThrowHttpMessageNotReadableExceptionBadRequestResponse() {
        HttpMessageNotReadableException e = mock(HttpMessageNotReadableException.class);

        ResponseEntity<?> response = presenterAccountController.httpMessageNotReadableException(e);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    @DisplayName("Exception thrown returns 500")
    void testThrowExcetionInternalError() {
        Exception e = new Exception();

        ResponseEntity<?> response = presenterAccountController.exceptionHandler(e);

        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    @DisplayName("Return 200 when presenter account is found")
    void testGetPresenterAccountSuccessResponse() {
        final String id = "9c60fa56-d5c0-4c34-8e53-17699af1191f";
        PresenterAccountDetails presenterAccountDetails = new PresenterAccountDetails(
                id,
                "userId",
                "test@example.com",
                new PresenterAccountName("forename", "surname"),
                new PresenterAccountAddress("premises",
                        "addressLine1",
                        "addressLine2",
                        "county",
                        "country",
                        "postcode"));
        Optional<PresenterAccountDetails> optionalPresenterAccountDetails = Optional.of(presenterAccountDetails);

        when(presenterAccountService.getPresenterAccount(id)).thenReturn(optionalPresenterAccountDetails);

        var response = presenterAccountController.getPresenterAccount(id);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(presenterAccountDetails));
    }

    @Test
    @DisplayName("Return 404 when presenter account is not found")
    void testGetPresenterAccountNotFoundResponse() {
        final String id = "id";
        Optional<PresenterAccountDetails> optionalPresenterAccountDetails = Optional.empty();

        when(presenterAccountService.getPresenterAccount(id)).thenReturn(optionalPresenterAccountDetails);

        var response = presenterAccountController.getPresenterAccount(id);

        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }
}

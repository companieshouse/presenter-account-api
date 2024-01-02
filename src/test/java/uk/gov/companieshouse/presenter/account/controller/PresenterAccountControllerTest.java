package uk.gov.companieshouse.presenter.account.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

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
import uk.gov.companieshouse.presenter.account.model.request.presenter.account.PresenterAddress;
import uk.gov.companieshouse.presenter.account.model.request.presenter.account.PresenterName;
import uk.gov.companieshouse.presenter.account.model.request.presenter.account.PresenterRequest;
import uk.gov.companieshouse.presenter.account.service.PresenterAccountService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;

@ExtendWith(MockitoExtension.class)
class PresenterAccountControllerTest {

    private static final String PRESENTER_ACCOUNT_REGEX = "/presenter-account/[0-9a-z]{1,100}";
    private static final String PRENETER_ACCOUNT_NULL = "/preneter-account/null";
    private static final String PRESENTER_ACCOUNT = "/presenter-account/";
    private final static String USER_ID = "userId";
    private final static String EMAIL = "test@test.test";
    private final static String FIRST_NAME = "test";
    private final static String LAST_NAME = "last";
    private final static String PREMISES = "premise";
    private final static String FIRST_LINE = "first line";
    private final static String SECOND_LINE = "second line";
    private final static String COUNTY = "county";
    private final static String COUNTRY = "country";
    private final static String POSTCODE = "postcode";


    PresenterAccountController presenterAccountController;

    @Mock
    PresenterAccountService presenterAccountService;

    @Mock
    Logger logger;

    @BeforeEach
    void setUp() {
        presenterAccountController = new PresenterAccountController(presenterAccountService, logger);
    }

    @Test
    @DisplayName("Return 200 on successfully health check")
    void testHealthCheckEndpointSuccessResponse (){
        var response = presenterAccountController.healthCheck();
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    @DisplayName("Return 201 when valid presenter details is submitted")
    void testCreatePresenterAccountSuccessResponse() {
        var presenterDetails = createPresenterDetails(
            USER_ID,
            EMAIL,
            FIRST_NAME,
            LAST_NAME,
            PREMISES,
            FIRST_LINE,
            SECOND_LINE,
            COUNTY,
            COUNTRY,
            POSTCODE);
        var response = presenterAccountController.createPresenterAccount(presenterDetails);
        var header = response.getHeaders().getFirst("Location");
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertTrue(header.contains(PRESENTER_ACCOUNT));
        assertFalse(header.contains(PRENETER_ACCOUNT_NULL));
        assertThat(matchesPattern(PRESENTER_ACCOUNT_REGEX).matches(header), is(true));
    }

    @Test
    @DisplayName("Return 201 when valid presenter details with second line is missing is submitted")
    void testCreatePresenterAccountMissingSecondLineSuccessResponse() {
        var presenterDetails = createPresenterDetails(
            USER_ID,
            EMAIL,
            FIRST_NAME,
            LAST_NAME,
            PREMISES,
            FIRST_LINE,
            "",
            COUNTY,
            COUNTRY,
            POSTCODE);
        var response = presenterAccountController.createPresenterAccount(presenterDetails);
        var header = response.getHeaders().getFirst("Location");
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertTrue(header.contains(PRESENTER_ACCOUNT));
        assertFalse(header.contains(PRENETER_ACCOUNT_NULL));
        assertThat(matchesPattern(PRESENTER_ACCOUNT_REGEX).matches(header), is(true));
    }

    @Test
    @DisplayName("Return 201 when valid presenter details with county is missing is submitted")
    void testCreatePresenterAccountMissingCountySuccessResponse() {
        var presenterDetails = createPresenterDetails(
            USER_ID,
            EMAIL,
            FIRST_NAME,
            LAST_NAME,
            PREMISES,
            FIRST_LINE,
            SECOND_LINE,
            "",
            COUNTRY,
            POSTCODE);
        var response = presenterAccountController.createPresenterAccount(presenterDetails);
        var header = response.getHeaders().getFirst("Location");
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertTrue(header.contains(PRESENTER_ACCOUNT));
        assertFalse(header.contains(PRENETER_ACCOUNT_NULL));
        assertThat(matchesPattern(PRESENTER_ACCOUNT_REGEX).matches(header), is(true));
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

    private PresenterRequest createPresenterDetails(
        String userId,
        String email,
        String firstName,
        String lastName,
        String premises, 
        String addressLine1, 
        String addressLine2, 
        String county, 
        String country,
        String postcode
    ) {
        var address = new PresenterAddress(premises, addressLine1, addressLine2, county, country, postcode);
        var name = new PresenterName(firstName, lastName);
        return new PresenterRequest(userId, email, name, address);
    }

}

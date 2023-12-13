package uk.gov.companieshouse.presenter.account.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@ExtendWith(MockitoExtension.class)
public class PresenterAccountControllerTest {

    @InjectMocks
    PresenterAccountController presenterAccountController;

    @BeforeEach
    void setUp() {
        presenterAccountController = new PresenterAccountController();
    }

    @Test
    @DisplayName("Return 200 on successfully health check")
    void test_HealthCheck_Endpoint_for_SuccessResponse (){
        var response = presenterAccountController.healthCheck();
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

}

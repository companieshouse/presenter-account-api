package uk.gov.companieshouse.presenter.account.controller;

import java.net.URI;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.presenter.account.exceptionhandler.InternalInvalidArgumentException;
import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountDetails;
import uk.gov.companieshouse.presenter.account.model.request.PresenterAccountDetailsRequest;
import uk.gov.companieshouse.presenter.account.service.PresenterAccountService;

@Controller
@RequestMapping("/presenter-account")
public class PresenterAccountController {

    private Logger logger;
    private PresenterAccountService presenterAccountService;

    @Autowired
    public PresenterAccountController(PresenterAccountService presenterAccountService, Logger logger) {
        this.logger = logger;
        this.presenterAccountService = presenterAccountService;
    }

    @GetMapping("/healthcheck")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok().body("OK");
    }

    @PostMapping("/")
    public ResponseEntity<String> createPresenterAccount(@RequestBody PresenterAccountDetailsRequest presenterAccountDetailsRequest) {
        String id = presenterAccountService.createPresenterAccount(presenterAccountDetailsRequest);
        final String uri = "/presenter-account/" + id;
        return ResponseEntity.created(URI.create(uri)).build();
    }

    @GetMapping("/{presenterDetailsId}")
    public ResponseEntity<PresenterAccountDetails> getPresenterAccount(@PathVariable("presenterDetailsId") String presenterDetailsId) {
        Optional<PresenterAccountDetails> presenterAccountDetailsOptional = presenterAccountService.getPresenterAccount(presenterDetailsId);
        return presenterAccountDetailsOptional
                .map(presenterAccountDetails -> ResponseEntity.ok().body(presenterAccountDetails))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ExceptionHandler(ValidationException.class)
    ResponseEntity<String> validationException(final ValidationException e) {
        return ResponseEntity.badRequest().body("Validation failed.");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<String> httpMessageNotReadableException(final HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body("Input format issue.");

    }

    @ExceptionHandler(InternalInvalidArgumentException.class)
    ResponseEntity<String> internalInvalidArgumentException(final InternalInvalidArgumentException e) {
        return exceptionHandler(e);
    }

    @ExceptionHandler
    ResponseEntity<String> exceptionHandler(Exception ex) {
        logger.error("Unhandled exception", ex);
        return ResponseEntity.internalServerError().build();
    }

}
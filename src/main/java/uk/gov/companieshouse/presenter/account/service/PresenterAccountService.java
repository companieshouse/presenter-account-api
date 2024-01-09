package uk.gov.companieshouse.presenter.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.presenter.account.mapper.Mapper;
import uk.gov.companieshouse.presenter.account.mapper.request.PresenterAccountDetailsMapper;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountDetails;
import uk.gov.companieshouse.presenter.account.model.request.PresenterAccountDetailsRequest;
import uk.gov.companieshouse.presenter.account.repository.PresenterAccountDetailsRepository;

import java.util.Optional;

@Service
public class PresenterAccountService {

    private static final String DB_ERROR_MSG = "Error occurred while fetching presenter account from the DB.";

    private Logger logger;

    private Mapper<PresenterAccountDetails, PresenterAccountDetailsRequest> detailsMapper;

    private PresenterAccountDetailsRepository repository;

    @Autowired
    public PresenterAccountService(Logger logger, PresenterAccountDetailsRepository repository, PresenterAccountDetailsMapper detailsMapper) {
        this.logger = logger;
        this.repository = repository;
        this.detailsMapper = detailsMapper;
    }

    public String createPresenterAccount(PresenterAccountDetailsRequest presenterAccountDetailsRequest) {
        PresenterAccountDetails presenterDetails = detailsMapper.map(presenterAccountDetailsRequest);

        // PLEASE REMOVE WHEN DB IS CONNECTED
        logger.info("Presenter Account has been added to the DB.");
        // Add to db
        // Once db is in place a real id can be returned.
        return "0000000000-0000-0000-0000-00000000";
    }

    public Optional<PresenterAccountDetails> getPresenterAccount(String presenterAccountId) {
        try {
            return repository.findById(presenterAccountId);
        } catch (Exception e) {
            logger.error(DB_ERROR_MSG, e);
            throw e;
        }
    }
}

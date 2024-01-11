package uk.gov.companieshouse.presenter.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.presenter.account.mapper.request.PresenterAccountDetailsMapper;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountDetails;
import uk.gov.companieshouse.presenter.account.model.mapper.presenter.account.mapper.base.AdditionalIdMapper;
import uk.gov.companieshouse.presenter.account.model.request.PresenterAccountDetailsRequest;
import uk.gov.companieshouse.presenter.account.repository.PresenterAccountRepository;
import uk.gov.companieshouse.presenter.account.utils.UuidGenerator;

import java.util.Optional;
import java.util.UUID;

@Service
public class PresenterAccountService {

    private static final String DB_ERROR_MSG = "Error occurred while fetching presenter account from the DB.";

    private Logger logger;

    private AdditionalIdMapper<PresenterAccountDetails, PresenterAccountDetailsRequest> detailsMapper;

    private PresenterAccountRepository presenterAccountRepository;

    private UuidGenerator uuidGenerator;

    @Autowired
    public PresenterAccountService(Logger logger, PresenterAccountDetailsMapper detailsMapper,
            PresenterAccountRepository presenterAccountRepository, UuidGenerator uuidGenerator) {
        this.logger = logger;
        this.detailsMapper = detailsMapper;
        this.presenterAccountRepository = presenterAccountRepository;
        this.uuidGenerator = uuidGenerator;
    }

    public String createPresenterAccount(PresenterAccountDetailsRequest presenterAccountDetailsRequest) {
        PresenterAccountDetails presenterDetails = detailsMapper.map(uuidGenerator.createUUID().toString(),
                presenterAccountDetailsRequest);
        presenterAccountRepository.save(presenterDetails);
        logger.info("Presenter account details has been added to the database.");
        return presenterDetails.presenterDetailsId();
    }

    public Optional<PresenterAccountDetails> getPresenterAccount(String presenterDetailsId) {
        try {
            return presenterAccountRepository.findById(presenterDetailsId);
        } catch (Exception e) {
            logger.error(DB_ERROR_MSG, e);
            throw e;
        }
    }
}
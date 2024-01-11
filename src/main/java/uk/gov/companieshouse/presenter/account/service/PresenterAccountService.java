package uk.gov.companieshouse.presenter.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.presenter.account.mapper.request.PresenterAccountDetailsMapper;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountDetails;
import uk.gov.companieshouse.presenter.account.model.mapper.presenter.account.mapper.base.AdditionalIdMapper;
import uk.gov.companieshouse.presenter.account.model.request.PresenterAccountDetailsRequest;
import uk.gov.companieshouse.presenter.account.repository.PresenterAccountRepository;
import uk.gov.companieshouse.presenter.account.utils.IdGenerator;

import java.util.Optional;

@Service
public class PresenterAccountService {

    private static final String DB_ERROR_MSG = "Error occurred while fetching presenter account from the DB.";

    private Logger logger;

    private AdditionalIdMapper<PresenterAccountDetails, PresenterAccountDetailsRequest> detailsMapper;

    private PresenterAccountRepository presenterAccountRepository;

    private IdGenerator idGenerator;

    @Autowired
    public PresenterAccountService(Logger logger, PresenterAccountDetailsMapper detailsMapper,
            PresenterAccountRepository presenterAccountRepository, IdGenerator idGenerator) {
        this.logger = logger;
        this.detailsMapper = detailsMapper;
        this.presenterAccountRepository = presenterAccountRepository;
        this.idGenerator = idGenerator;
    }

    public String createPresenterAccount(PresenterAccountDetailsRequest presenterAccountDetailsRequest) {
        PresenterAccountDetails presenterDetails = detailsMapper.map(idGenerator.createUUID(),
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
package uk.gov.companieshouse.presenter.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.presenter.account.mapper.Mapper;
import uk.gov.companieshouse.presenter.account.mapper.request.PresenterAccountDetailsMapper;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountDetails;
import uk.gov.companieshouse.presenter.account.model.request.PresenterAccountDetailsRequest;
import uk.gov.companieshouse.presenter.account.repository.PresenterAccountRepository;

import java.util.Optional;
import java.util.UUID;


@Service
public class PresenterAccountService {

    private static final String DB_ERROR_MSG = "Error occurred while fetching presenter account from the DB.";

    private Logger logger;

    private Mapper<PresenterAccountDetails, PresenterAccountDetailsRequest> detailsMapper;


    private PresenterAccountRepository presenterAccountRepository;

    @Autowired
    public PresenterAccountService(Logger logger, PresenterAccountDetailsMapper detailsMapper,
                                   PresenterAccountRepository presenterAccountRepository) {
        this.logger = logger;
        this.detailsMapper = detailsMapper;
        this.presenterAccountRepository = presenterAccountRepository;
    }

    public String createPresenterAccount(PresenterAccountDetailsRequest presenterRequest) {
        PresenterAccountDetails presenterDetails = detailsMapper.map(presenterRequest);
        presenterDetails.setPresenterDetailsId(UUID.randomUUID().toString());
        presenterAccountRepository.save(presenterDetails);
        logger.info("Presenter account details has been added to the database.");
        return presenterDetails.getPresenterDetailsId();
    }

    public Optional<PresenterAccountDetails> getPresenterAccount(String presenterAccountId) {
        try {
            return presenterAccountRepository.findById(presenterAccountId);
        } catch (Exception e) {
            logger.error(DB_ERROR_MSG, e);
            throw e;
        }
    }
}
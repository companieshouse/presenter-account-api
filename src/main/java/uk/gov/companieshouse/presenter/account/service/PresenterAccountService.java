package uk.gov.companieshouse.presenter.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.api.model.presenteraccount.*;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.presenter.account.mapper.AdditionalIdMapper;
import uk.gov.companieshouse.presenter.account.mapper.request.PresenterAccountDetailsMapper;
import uk.gov.companieshouse.presenter.account.mapper.response.DetailsApiTransformer;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountDetails;
import uk.gov.companieshouse.presenter.account.model.request.PresenterAccountDetailsRequest;
import uk.gov.companieshouse.presenter.account.repository.PresenterAccountRepository;
import uk.gov.companieshouse.presenter.account.utils.IdGenerator;

import java.util.Optional;

@Service
public class PresenterAccountService {

    private static final String DB_ERROR_MSG = "Error occurred while fetching presenter account from the DB.";

    private final Logger logger;

    private final AdditionalIdMapper<PresenterAccountDetails, PresenterAccountDetailsRequest> detailsMapper;

    private final PresenterAccountRepository presenterAccountRepository;

    private final IdGenerator idGenerator;

    private final DetailsApiTransformer detailsApiTransformer;

    @Autowired
    public PresenterAccountService(final Logger logger, final PresenterAccountDetailsMapper detailsMapper,
                                   final PresenterAccountRepository presenterAccountRepository, final IdGenerator idGenerator, DetailsApiTransformer detailsApiTransformer) {
        this.logger = logger;
        this.detailsMapper = detailsMapper;
        this.presenterAccountRepository = presenterAccountRepository;
        this.idGenerator = idGenerator;
        this.detailsApiTransformer = detailsApiTransformer;
    }

    public String createPresenterAccount(final PresenterAccountDetailsRequest presenterAccountDetailsRequest) {
        final PresenterAccountDetails presenterDetails = detailsMapper.map(idGenerator.createUUID(),
                presenterAccountDetailsRequest);
        presenterAccountRepository.save(presenterDetails);
        logger.info("Presenter account details has been added to the database.");
        return presenterDetails.presenterDetailsId();
    }

    public Optional<PresenterAccountDetailsApi> getPresenterAccount(final String presenterDetailsId) {
        try {
            return detailsApiTransformer.responseTransformer(presenterAccountRepository.findById(presenterDetailsId));
        } catch (final Exception e) {
            logger.error(DB_ERROR_MSG, e);
            throw e;
        }
    }
}
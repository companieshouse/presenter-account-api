package uk.gov.companieshouse.presenter.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountDetails;
import uk.gov.companieshouse.presenter.account.model.mapper.presenter.account.mapper.PresenterAccountDetailsMapper;
import uk.gov.companieshouse.presenter.account.model.mapper.presenter.account.mapper.base.Mapper;
import uk.gov.companieshouse.presenter.account.model.request.presenter.account.PresenterRequest;
import uk.gov.companieshouse.presenter.account.repository.PresenterAccountRepository;

import java.util.UUID;

@Service
public class PresenterAccountService {

    private Logger logger;

    private Mapper<PresenterAccountDetails, PresenterRequest> detailsMapper;

    private PresenterAccountRepository presenterAccountRepository;

    @Autowired
    public PresenterAccountService(Logger logger, PresenterAccountDetailsMapper detailsMapper, PresenterAccountRepository presenterAccountRepository) {
        this.logger = logger;
        this.detailsMapper = detailsMapper;
        this.presenterAccountRepository = presenterAccountRepository;
    }

    public String createPresenterAccount(PresenterRequest presenterRequest) {
        PresenterAccountDetails presenterDetails = detailsMapper.map(presenterRequest);
        presenterDetails.setPresenterDetailsId(UUID.randomUUID().toString());
        presenterAccountRepository.save(presenterDetails);
        // PLEASE REMOVE WHEN DB IS CONNECTED
        logger.info("Presenter account details has been added to the database.");
        return presenterDetails.getPresenterDetailsId();
    }
}
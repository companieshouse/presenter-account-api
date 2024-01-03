package uk.gov.companieshouse.presenter.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountDetails;
import uk.gov.companieshouse.presenter.account.model.mapper.presenter.account.mapper.PresenterAccountDetailsMapper;
import uk.gov.companieshouse.presenter.account.model.mapper.presenter.account.mapper.base.Mapper;
import uk.gov.companieshouse.presenter.account.model.request.presenter.account.PresenterRequest;

@Service
public class PresenterAccountService {
    
    private Logger logger;

    private Mapper<PresenterAccountDetails, PresenterRequest> detailsMapper;

    @Autowired
    public PresenterAccountService(Logger logger, PresenterAccountDetailsMapper detailsMapper) {
        this.logger = logger;
        this.detailsMapper = detailsMapper;
    }

    public String createPresenterAccount(PresenterRequest presenterRequest){
        PresenterAccountDetails presenterDetails = detailsMapper.map(presenterRequest);

        // PLEASE REMOVE WHEN DB IS CONNECTED
        logger.info("Presenter Account has been added to the DB.");
        // Add to db
        // Once db is in place a real id can be returned.
        return "0000000000-0000-0000-0000-00000000";
    }

}

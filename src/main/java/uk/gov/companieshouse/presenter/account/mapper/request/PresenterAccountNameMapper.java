package uk.gov.companieshouse.presenter.account.mapper.request;

import org.springframework.stereotype.Component;

import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountName;
import uk.gov.companieshouse.presenter.account.mapper.Mapper;
import uk.gov.companieshouse.presenter.account.model.request.PresenterNameRequest;

@Component
public class PresenterAccountNameMapper implements Mapper<PresenterAccountName, PresenterNameRequest> {

    @Override
    public PresenterAccountName map(PresenterNameRequest value) {
        if (value != null) {
            return new PresenterAccountName(value.forename(), value.surname());
        } else {
            throw new ValidationException("Presenter full name is missing");
        }
    }
    
}

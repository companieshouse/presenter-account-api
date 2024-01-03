package uk.gov.companieshouse.presenter.account.model.mapper.presenter.account.mapper;

import org.springframework.stereotype.Component;

import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountName;
import uk.gov.companieshouse.presenter.account.model.mapper.presenter.account.mapper.base.Mapper;
import uk.gov.companieshouse.presenter.account.model.request.presenter.account.PresenterName;

@Component
public class PresenterAccountNameMapper implements Mapper<PresenterAccountName, PresenterName> {

    @Override
    public PresenterAccountName map(PresenterName value) {
        if (value != null) {
            return new PresenterAccountName(value.forename(), value.surname());
        } else {
            throw new ValidationException("Presenter full name is missing");
        }
    }
    
}

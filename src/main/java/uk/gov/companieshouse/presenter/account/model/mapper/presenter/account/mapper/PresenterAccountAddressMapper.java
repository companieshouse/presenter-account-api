package uk.gov.companieshouse.presenter.account.model.mapper.presenter.account.mapper;

import org.springframework.stereotype.Component;

import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountAddress;
import uk.gov.companieshouse.presenter.account.model.mapper.presenter.account.mapper.base.Mapper;
import uk.gov.companieshouse.presenter.account.model.request.presenter.account.PresenterAddress;

@Component
public class PresenterAccountAddressMapper implements Mapper<PresenterAccountAddress, PresenterAddress> {

    @Override
    public PresenterAccountAddress map(PresenterAddress value) {
        if (value != null) {
            return new PresenterAccountAddress(value.premises(),
                    value.addressLine1(),
                    value.addressLine2(),
                    value.county(),
                    value.country(),
                    value.postcode());
        } else {
            throw new ValidationException("Presenter address info is missing");
        }
    }
    
}

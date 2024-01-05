package uk.gov.companieshouse.presenter.account.mapper.request;

import org.springframework.stereotype.Component;

import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountAddress;
import uk.gov.companieshouse.presenter.account.mapper.Mapper;
import uk.gov.companieshouse.presenter.account.model.request.PresenterAddressRequest;

@Component
public class PresenterAccountAddressMapper implements Mapper<PresenterAccountAddress, PresenterAddressRequest> {

    @Override
    public PresenterAccountAddress map(PresenterAddressRequest value) {
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

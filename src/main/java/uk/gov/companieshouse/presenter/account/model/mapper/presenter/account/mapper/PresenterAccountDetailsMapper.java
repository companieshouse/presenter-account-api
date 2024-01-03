package uk.gov.companieshouse.presenter.account.model.mapper.presenter.account.mapper;

import org.springframework.stereotype.Component;

import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountAddress;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountDetails;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountName;
import uk.gov.companieshouse.presenter.account.model.mapper.presenter.account.mapper.base.Mapper;
import uk.gov.companieshouse.presenter.account.model.request.presenter.account.PresenterAddress;
import uk.gov.companieshouse.presenter.account.model.request.presenter.account.PresenterName;
import uk.gov.companieshouse.presenter.account.model.request.presenter.account.PresenterRequest;

@Component
public class PresenterAccountDetailsMapper implements Mapper<PresenterAccountDetails, PresenterRequest> {

    private Mapper<PresenterAccountAddress, PresenterAddress> addressMapper;
    private Mapper<PresenterAccountName, PresenterName> nameMapper;

    public PresenterAccountDetailsMapper(Mapper<PresenterAccountAddress,PresenterAddress> addressMapper, Mapper<PresenterAccountName,PresenterName> nameMapper) {
        this.addressMapper = addressMapper;
        this.nameMapper = nameMapper;
    }

    @Override
    public PresenterAccountDetails map(PresenterRequest value) {
        if (value != null) {
            PresenterAccountName name = nameMapper.map(value.name());
            PresenterAccountAddress address = addressMapper.map(value.address());
            return new PresenterAccountDetails(
                value.userId(),
                value.email(), 
                name, 
                address);
        } else {
            throw new ValidationException("Presenter can not be null");
        }
    }
    
}

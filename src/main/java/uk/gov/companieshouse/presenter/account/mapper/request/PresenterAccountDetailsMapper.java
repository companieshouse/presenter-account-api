package uk.gov.companieshouse.presenter.account.mapper.request;

import org.springframework.stereotype.Component;

import uk.gov.companieshouse.presenter.account.exceptionhandler.ValidationException;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountAddress;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountDetails;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountName;
import uk.gov.companieshouse.presenter.account.model.mapper.presenter.account.mapper.base.AdditionalIdMapper;
import uk.gov.companieshouse.presenter.account.mapper.Mapper;
import uk.gov.companieshouse.presenter.account.model.request.PresenterAddressRequest;
import uk.gov.companieshouse.presenter.account.model.request.PresenterNameRequest;
import uk.gov.companieshouse.presenter.account.model.request.PresenterAccountDetailsRequest;

@Component
public class PresenterAccountDetailsMapper
        implements AdditionalIdMapper<PresenterAccountDetails, PresenterAccountDetailsRequest> {

    private final Mapper<PresenterAccountAddress, PresenterAddressRequest> addressMapper;
    private final Mapper<PresenterAccountName, PresenterNameRequest> nameMapper;

    public PresenterAccountDetailsMapper(Mapper<PresenterAccountAddress, PresenterAddressRequest> addressMapper,
            Mapper<PresenterAccountName, PresenterNameRequest> nameMapper) {
        this.addressMapper = addressMapper;
        this.nameMapper = nameMapper;
    }

    @Override
    public PresenterAccountDetails map(String id, PresenterAccountDetailsRequest value) {
        if (value != null) {
            PresenterAccountName name = nameMapper.map(value.name());
            PresenterAccountAddress address = addressMapper.map(value.address());
            return new PresenterAccountDetails(
                    id,
                    value.userId(),
                    value.email(),
                    name,
                    address);
        } else {
            throw new ValidationException("Presenter can not be null");
        }
    }

}

package uk.gov.companieshouse.presenter.account.mapper.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.presenteraccount.PresenterAccountDetailsApi;
import uk.gov.companieshouse.api.model.presenteraccount.PresenterAccountDetailsApiBuilder;
import uk.gov.companieshouse.presenter.account.mapper.Mapper;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountDetails;

@Component
public class DetailsApiMapper implements Mapper<PresenterAccountDetailsApi, PresenterAccountDetails> {
    AddressApiMapper addressMapper;
    NameApiMapper nameMapper;
    CompanyApiMapper companyMapper;


    @Autowired
    public DetailsApiMapper(AddressApiMapper addressMapper, NameApiMapper nameMapper, CompanyApiMapper companyMapper) {
        this.addressMapper = addressMapper;
        this.nameMapper = nameMapper;
        this.companyMapper = companyMapper;
    }

    @Override
    public PresenterAccountDetailsApi map(PresenterAccountDetails value) {
        return PresenterAccountDetailsApiBuilder.createPresenterAccountDetailsApi()
                .withUserId(value.userId())
                .withPresenterDetailsId(value.presenterDetailsId())
                .withEmail(value.email())
                .withCompany(companyMapper.map(value.companyDetails()))
                .withName(nameMapper.map(value.name()))
                .withAddress(addressMapper.map(value.address()))
                .build();
    }
}

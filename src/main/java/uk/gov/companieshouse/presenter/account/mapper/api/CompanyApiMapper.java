package uk.gov.companieshouse.presenter.account.mapper.api;

import uk.gov.companieshouse.presenter.account.mapper.Mapper;
import uk.gov.companieshouse.presenter.account.model.PresenterAccountCompany;

import org.springframework.stereotype.Component;

import uk.gov.companieshouse.api.model.presenteraccount.PresenterAccountCompanyApi;
import uk.gov.companieshouse.api.model.presenteraccount.PresenterAccountCompanyApiBuilder;

@Component
public class CompanyApiMapper implements Mapper<PresenterAccountCompanyApi, PresenterAccountCompany> {

    @Override
    public PresenterAccountCompanyApi map(PresenterAccountCompany value) {
        return PresenterAccountCompanyApiBuilder.createPresenterAccountCompanyApi(value.companyName())
                .withCompanyNumber(value.companyNumber())
                .build();
    }
    
}

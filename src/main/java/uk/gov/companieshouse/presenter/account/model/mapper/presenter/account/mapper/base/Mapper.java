package uk.gov.companieshouse.presenter.account.model.mapper.presenter.account.mapper.base;

public interface Mapper<E, M>  {

    E map(M value);
    
}

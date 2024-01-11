package uk.gov.companieshouse.presenter.account.mapper;

public interface Mapper<E, M>  {

    E map(M value);

}

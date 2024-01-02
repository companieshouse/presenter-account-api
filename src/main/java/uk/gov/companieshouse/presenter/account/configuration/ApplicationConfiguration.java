package uk.gov.companieshouse.presenter.account.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import uk.gov.companieshouse.api.interceptor.InternalUserInterceptor;

import uk.gov.companieshouse.api.InternalApiClient;
import uk.gov.companieshouse.api.http.ApiKeyHttpClient;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

@Configuration
public class ApplicationConfiguration {

    @Value("${application.namespace}")
    private String applicationNameSpace;
    
    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(applicationNameSpace);
    }

    @Bean
    public InternalApiClient internalApiClient(
            @Value("${api.base.path}") String apiBasePath,
            @Value("${internal.api.base.path}") String internalApiBasePath,
            @Value("${internal.api.key}") String internalApiKey
    ) {
        ApiKeyHttpClient httpClient = new ApiKeyHttpClient(internalApiKey);
        InternalApiClient internalApiClient = new InternalApiClient(httpClient);

        internalApiClient.setBasePath(internalApiBasePath);
        internalApiClient.setInternalBasePath(internalApiBasePath);

        return internalApiClient;
    }

    /**
     * Creates InternalUserInterceptor which checks the Api key has internal app privileges to access the application
     *
     * @return the internal user interceptor
     */
    @Bean
    public InternalUserInterceptor internalUserInterceptor() {
        return new InternalUserInterceptor(applicationNameSpace);
    }
}

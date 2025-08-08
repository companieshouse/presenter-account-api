package uk.gov.companieshouse.presenter.account.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import uk.gov.companieshouse.api.interceptor.InternalUserInterceptor;
import uk.gov.companieshouse.api.interceptor.TokenPermissionsInterceptor;
import uk.gov.companieshouse.presenter.account.interceptor.LoggingInterceptor;
import uk.gov.companieshouse.presenter.account.interceptor.UserAuthenticationInterceptor;

@Component
public class WebMvcConfig implements WebMvcConfigurer {

    private final LoggingInterceptor loggingInterceptor;
    private final UserAuthenticationInterceptor userAuthenticationInterceptor;
    private final InternalUserInterceptor internalUserInterceptor;

    @Autowired
    public WebMvcConfig(final LoggingInterceptor loggingInterceptor,
                        final UserAuthenticationInterceptor userAuthenticationInterceptor,
                        final InternalUserInterceptor internalUserInterceptor) {
        this.loggingInterceptor = loggingInterceptor;
        this.userAuthenticationInterceptor = userAuthenticationInterceptor;
        this.internalUserInterceptor = internalUserInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor);
        registry.addInterceptor(getTokenPermissionsInterceptor());
        registry.addInterceptor(userAuthenticationInterceptor)
                .excludePathPatterns("/presenter-account-api/healthcheck");
        registry.addInterceptor(internalUserInterceptor)
                .excludePathPatterns("/presenter-account-api/healthcheck")
                .excludePathPatterns("/presenter-account/");
    }

    private TokenPermissionsInterceptor getTokenPermissionsInterceptor() {
        return new TokenPermissionsInterceptor();
    }

}

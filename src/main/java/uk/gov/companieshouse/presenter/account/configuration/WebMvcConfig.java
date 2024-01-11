package uk.gov.companieshouse.presenter.account.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import uk.gov.companieshouse.api.interceptor.TokenPermissionsInterceptor;
import uk.gov.companieshouse.presenter.account.interceptor.LoggingInterceptor;
import uk.gov.companieshouse.presenter.account.interceptor.UserAuthenticationInterceptor;

@Component
public class WebMvcConfig implements WebMvcConfigurer {

    private final LoggingInterceptor loggingInterceptor;
    private final UserAuthenticationInterceptor userAuthenticationInterceptor;

    @Autowired
    public WebMvcConfig(final LoggingInterceptor loggingInterceptor,
                        final UserAuthenticationInterceptor userAuthenticationInterceptor) {
        this.loggingInterceptor = loggingInterceptor;
        this.userAuthenticationInterceptor = userAuthenticationInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor);
        registry.addInterceptor(getTokenPermissionsInterceptor());
        registry.addInterceptor(userAuthenticationInterceptor)
                .excludePathPatterns(
                        "/presenter-account/healthcheck");
    }

    private TokenPermissionsInterceptor getTokenPermissionsInterceptor() {
        return new TokenPermissionsInterceptor();
    }

}

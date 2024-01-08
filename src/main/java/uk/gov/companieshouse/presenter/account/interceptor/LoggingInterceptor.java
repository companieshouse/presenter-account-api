package uk.gov.companieshouse.presenter.account.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.util.RequestLogger;

@Component
public class LoggingInterceptor implements AsyncHandlerInterceptor, RequestLogger {
        private final Logger logger;

    /**
     * Sets the logger used to log out request information.
     *
     * @param logger the configured logger
     */
    @Autowired
    public LoggingInterceptor(final Logger logger) {
        this.logger = logger;
    }

    @Override
    public boolean preHandle(@NonNull final HttpServletRequest request,
                             @NonNull final HttpServletResponse response,
                             @NonNull final Object handler) {
        logStartRequestProcessing(request, logger);
        return true;
    }

    @Override
    public void postHandle(@NonNull final HttpServletRequest request,
                           @NonNull final HttpServletResponse response,
                           @NonNull final Object handler,
                           final ModelAndView modelAndView) {
        logEndRequestProcessing(request, response, logger);
    }
}

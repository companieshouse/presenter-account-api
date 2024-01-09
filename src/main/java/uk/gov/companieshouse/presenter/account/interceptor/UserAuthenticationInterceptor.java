package uk.gov.companieshouse.presenter.account.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import uk.gov.companieshouse.api.util.security.Permission.Key;
import uk.gov.companieshouse.api.util.security.Permission.Value;
import uk.gov.companieshouse.api.util.security.TokenPermissions;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.util.RequestLogger;

import org.springframework.web.servlet.AsyncHandlerInterceptor;

/**
 * Checks each request to the service to verify that the user is authorised to
 * use the service.
 */
@Component
public class UserAuthenticationInterceptor implements AsyncHandlerInterceptor, RequestLogger {

    private static final Key USER_PRESENTER = Key.USER_PRESENTER;
    private static final String CREATE = Value.CREATE;

    final Logger logger;
    private final AuthenticationHelper authHelper;

    /**
     * Constructor which configures the logger and authentication helper class.
     *
     * @param authHelper the {@link AuthenticationHelper}
     * @param logger     the specified logger
     */
    @Autowired
    public UserAuthenticationInterceptor(final AuthenticationHelper authHelper, final Logger logger) {
        this.authHelper = authHelper;
        this.logger = logger;
    }

    /**
     * Ensure requests are authenticated for a user.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) {

        final String requestMethod = authHelper.getRequestMethod(request);
        final boolean isGetRequest = authHelper.isGetMethod(requestMethod);
        if (isGetRequest) {
            logger.debugRequest(request, "UserAuthenticationInterceptor info: Request is GET - ignore permissions.",
                    null);
            return true;
        }

        if (!(validateIdentity(request) && validateIdentityType(request))) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        } else if (!validateUserPresenterPermission(request)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        } else {
            return true;
        }

    }

    private boolean validateIdentity(@NotNull HttpServletRequest request) {
        final String identity = authHelper.getAuthorisedIdentity(request);

        if (identity == null || identity.isEmpty()) {
            logger.debugRequest(request, "UserAuthenticationInterceptor error: no authorised identity", null);
            return false;
        } else {
            return true;
        }
    }

    private boolean validateIdentityType(@NotNull HttpServletRequest request) {
        final String identityType = authHelper.getAuthorisedIdentityType(request);

        if (identityType == null || identityType.isEmpty()) {
            logger.debugRequest(request, "UserAuthenticationInterceptor error: no authorised identity type", null);
            return false;
        }

        if (authHelper.isOauth2IdentityType(identityType)) {
            return validateOAuth2Identity(request);
        } else {
            logger.debugRequest(request, "UserAuthenticationInterceptor error: identity type not oauth2",
                    null);
            return false;
        }
    }

    private boolean validateOAuth2Identity(@NotNull HttpServletRequest request) {
        final String authorisedUser = authHelper.getAuthorisedUser(request);

        if (authorisedUser == null || authorisedUser.trim().length() == 0) {
            logger.debugRequest(request, "UserAuthenticationInterceptor error: no authorised user", null);

            return false;
        }

        request.setAttribute("user_email", authHelper.getAuthorisedUserEmail(request));
        return true;
    }

    private boolean validateUserPresenterPermission(@NotNull HttpServletRequest request) {
        TokenPermissions tokenPermissions = authHelper.getTokenPermissions(request);

        boolean validPermission = authHelper.validTokenPermissions(tokenPermissions, USER_PRESENTER, CREATE);

        if (!validPermission) {
            logger.debugRequest(request, "UserAuthenticationInterceptor error: required permission are not set.", null);
        }

        return validPermission;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        // cleanup request attributes to ensure user details are never leaked
        // into another request
        request.setAttribute("user_email", null);
    }

}
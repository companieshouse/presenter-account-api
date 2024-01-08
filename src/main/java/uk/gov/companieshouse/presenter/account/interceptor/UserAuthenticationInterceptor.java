package uk.gov.companieshouse.presenter.account.interceptor;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

        final String identityType = authHelper.getAuthorisedIdentityType(request);
        boolean validIdentity = false;
        boolean validPermission = false;
        boolean shouldContinue = false;

        if (identityType == null || identityType.isEmpty()) {
            logger.debugRequest(request, "UserAuthenticationInterceptor error: no authorised identity type", null);
        }

        final String identity = authHelper.getAuthorisedIdentity(request);

        if (identity == null || identity.isEmpty()) {
            logger.debugRequest(request, "UserAuthenticationInterceptor error: no authorised identity", null);
        }

        if (authHelper.isOauth2IdentityType(identityType)) {
            validIdentity = validateOAuth2Identity(request, identity);
        } else {
            logger.debugRequest(request, "UserAuthenticationInterceptor error: identity type not oauth2",
                    null);
        }

        validPermission = validateUserPresenterPermission(request);

        if (!validPermission) {
            logger.debugRequest(request, "UserAuthenticationInterceptor error: required permission are not set.", null);
        }

        shouldContinue = Stream.of(validIdentity, validPermission).allMatch(validValue -> validValue);

        if (!shouldContinue) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        return shouldContinue;
    }

    private boolean validateOAuth2Identity(HttpServletRequest request, String identity) {

        request.setAttribute("user_id", identity);

        final String authorisedUser = authHelper.getAuthorisedUser(request);
        if (authorisedUser == null || authorisedUser.trim().length() == 0) {
            logger.debugRequest(request, "UserAuthenticationInterceptor error: no authorised user", null);

            return false;
        }

        // decode user details and set on session
        request.setAttribute("user_email", authHelper.getAuthorisedUserEmail(request));

        String userForename = authHelper.getAuthorisedUserForename(request);
        String userSurname = authHelper.getAuthorisedUserSurname(request);

        if (userForename != null) {
            request.setAttribute("user_forename", userForename);
        }
        if (userSurname != null) {
            request.setAttribute("user_surname", userSurname);
        }

        return true;
    }

    private boolean validateUserPresenterPermission(HttpServletRequest request) {
        TokenPermissions tokenPermissions = authHelper.getTokenPermissions(request);
        if (tokenPermissions != null) {
            return authHelper.validTokenPermissions(tokenPermissions, USER_PRESENTER, CREATE);
        } else {
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        // cleanup request attributes to ensure user details are never leaked
        // into another request
        request.setAttribute("user_id", null);
        request.setAttribute("user_email", null);
        request.setAttribute("user_forename", null);
        request.setAttribute("user_surname", null);
    }

}
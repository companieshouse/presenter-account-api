package uk.gov.companieshouse.presenter.account.interceptor;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import uk.gov.companieshouse.api.util.security.Permission.Key;
import uk.gov.companieshouse.api.util.security.TokenPermissions;

@Component
public class AuthenticationHelper {
    public static final String OAUTH2_IDENTITY_TYPE = "oauth2";

    public static final int USER_EMAIL_INDEX = 0;

    private static final String ERIC_IDENTITY = "ERIC-Identity";
    private static final String ERIC_IDENTITY_TYPE = "ERIC-Identity-Type";
    private static final String ERIC_AUTHORISED_USER = "ERIC-Authorised-User";

    private static final String TOKEN_PERMISSIONS_ATTRIBUTE = "token_permissions";

    public String getAuthorisedIdentity(HttpServletRequest request) {
        return getRequestHeader(request, ERIC_IDENTITY);
    }

    public String getAuthorisedIdentityType(HttpServletRequest request) {
        return getRequestHeader(request, ERIC_IDENTITY_TYPE);
    }

    public boolean isOauth2IdentityType(final String identityType) {
        return OAUTH2_IDENTITY_TYPE.equals(identityType);
    }

    public String getAuthorisedUser(HttpServletRequest request) {
        return getRequestHeader(request, ERIC_AUTHORISED_USER);
    }

    public String getAuthorisedUserEmail(HttpServletRequest request) {
        final String authorisedUser = getAuthorisedUser(request);

        if (authorisedUser == null || authorisedUser.trim().length() == 0) {
            return null;
        } else {
            final String[] details = authorisedUser.split(";");

            return indexExists(details, USER_EMAIL_INDEX) ? details[USER_EMAIL_INDEX].trim() : null;
        }
    }

    public TokenPermissions getTokenPermissions(HttpServletRequest request) {
        if (request.getAttribute(TOKEN_PERMISSIONS_ATTRIBUTE) instanceof TokenPermissions tokenPermissions) {
            return tokenPermissions;
        } else {
            return null;
        }
    }

    boolean validTokenPermissions(TokenPermissions tokenPermissions, Key key, String permission) {
        if (tokenPermissions != null) {
            return tokenPermissions.hasPermission(key, permission);
        } else {
            return false;
        }
    }

    String getRequestMethod(HttpServletRequest request) {
        return request == null ? null : request.getMethod();
    }

    boolean isGetMethod(String method) {
        return HttpMethod.GET.name().equals(method);
    }

    private String getRequestHeader(HttpServletRequest request, String header) {
        return request == null ? null : request.getHeader(header);
    }

    private boolean indexExists(final String[] list, final int index) {
        return index >= 0 && index < list.length;
    }

}

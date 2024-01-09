package uk.gov.companieshouse.presenter.account.interceptor;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.http.HttpServletRequest;
import uk.gov.companieshouse.api.util.security.TokenPermissions;
import uk.gov.companieshouse.api.util.security.Permission.Key;
import uk.gov.companieshouse.api.util.security.Permission.Value;
import uk.gov.companieshouse.logging.Logger;

@ExtendWith(MockitoExtension.class)
class UserAuthenticationInterceptorTest {

    private static final String IDENTITY = "identity";
    private static final String USER = "user";

    private UserAuthenticationInterceptor interceptor;

    @Mock
    private HttpServletRequest request;

    @Mock
    private AuthenticationHelper helper;

    @Mock
    private Logger logger;

    @Mock
    private TokenPermissions token;

    @BeforeEach
    void setUp() {
        interceptor = new UserAuthenticationInterceptor(helper, logger);
    }

    @Test
    void validateIdentityWithInvalidIdentity() {

        assertFalse(interceptor.validateIdentity(request));
    }

    @Test
    void validateIdentity() {
        when(helper.getAuthorisedIdentity(request)).thenReturn(IDENTITY);
        assertTrue(interceptor.validateIdentity(request));
    }

    @Test
    void validateIndentityType() {
        final String oauth2 = "oauth2";
        when(helper.getAuthorisedIdentityType(request)).thenReturn(oauth2);
        when(helper.isOauth2IdentityType(oauth2)).thenReturn(true);
        when(helper.getAuthorisedUser(request)).thenReturn(USER);
        assertTrue(interceptor.validateIdentityType(request));
    }

    @Test
    void validateIdentityTypeNullIdentityType() {
        when(helper.getAuthorisedIdentityType(request)).thenReturn(null);
        assertFalse(interceptor.validateIdentityType(request));
    }

    @Test
    void validateIdentityTypeNotAllowedIdentityType() {
        final String noType = "noType";
        when(helper.getAuthorisedIdentityType(request)).thenReturn(noType);
        when(helper.isOauth2IdentityType(noType)).thenReturn(false);
        assertFalse(interceptor.validateIdentityType(request));
    }

    @Test
    void validateOAuth2Identity() {
        when(helper.getAuthorisedUser(request)).thenReturn(USER);
        assertTrue(interceptor.validateOAuth2Identity(request));
    }

    @Test
    void validateOAuth2IdentityNoAuthUser() {
        assertFalse(interceptor.validateOAuth2Identity(request));
    }

    @Test
    void validateUserPresenterPermission() {
        when(helper.getTokenPermissions(request)).thenReturn(token);
        when(helper.validTokenPermissions(token, Key.USER_PRESENTER, Value.CREATE)).thenReturn(true);
        assertTrue(interceptor.validateUserPresenterPermission(request));
    }

    @Test
    void validateUserPresenterPermissionWithWrongToken() {
        when(helper.getTokenPermissions(request)).thenReturn(token);
        when(helper.validTokenPermissions(token, Key.USER_PRESENTER, Value.CREATE)).thenReturn(false);
        assertFalse(interceptor.validateUserPresenterPermission(request));
    }

    @Test
    void validateUserPresenterPermissionWithNoToken() {
        when(helper.getTokenPermissions(request)).thenReturn(null);
        when(helper.validTokenPermissions(null, Key.USER_PRESENTER, Value.CREATE)).thenReturn(false);
        assertFalse(interceptor.validateUserPresenterPermission(request));
    }
}

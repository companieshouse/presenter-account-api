package uk.gov.companieshouse.presenter.account.interceptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.gov.companieshouse.api.util.security.TokenPermissions;
import uk.gov.companieshouse.api.util.security.Permission.Key;
import uk.gov.companieshouse.api.util.security.Permission.Value;
import uk.gov.companieshouse.logging.Logger;

@ExtendWith(MockitoExtension.class)
class UserAuthenticationInterceptorTest {

    private static final String IDENTITY = "identity";
    private static final String USER = "user";
    private static final String OAUTH2 = "oauth2";

    private UserAuthenticationInterceptor interceptor;

    @Mock
    private HttpServletRequest request;

    @Spy
    private HttpServletResponse response;

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
    void preHandle() {
        when(helper.getAuthorisedIdentity(request)).thenReturn(IDENTITY);
        when(helper.getAuthorisedIdentityType(request)).thenReturn(OAUTH2);
        when(helper.isOauth2IdentityType(OAUTH2)).thenReturn(true);
        when(helper.getAuthorisedUser(request)).thenReturn(USER);
        when(helper.getTokenPermissions(request)).thenReturn(token);
        when(helper.validTokenPermissions(token, Key.USER_PRESENTER, Value.CREATE)).thenReturn(true);
        
        assertTrue(interceptor.preHandle(request, response, null));
    }

    @Test
    void preHandleNullIndentity() {
        when(helper.getAuthorisedIdentity(request)).thenReturn(null);
        
        assertFalse(interceptor.preHandle(request, response, null));
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    void preHandleNullIdentityType() {
        when(helper.getAuthorisedIdentity(request)).thenReturn(IDENTITY);
        when(helper.getAuthorisedIdentityType(request)).thenReturn(null);
        
        assertFalse(interceptor.preHandle(request, response, null));
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    void preHandleDisallowedIdentityType() {
        final String disallowed = "disallowed";
        when(helper.getAuthorisedIdentity(request)).thenReturn(IDENTITY);
        when(helper.getAuthorisedIdentityType(request)).thenReturn(disallowed);
        when(helper.isOauth2IdentityType(disallowed)).thenReturn(false);

        assertFalse(interceptor.preHandle(request, response, null));
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    void preHandleGetRequest() {
        final String get = "GET";
        when(helper.getRequestMethod(request)).thenReturn(get);
        when(helper.isGetMethod(get)).thenReturn(true);

        assertTrue(interceptor.preHandle(request, response, null));
    }

    @Test
    void preHandleInvalidOAuth2Identity() {
        when(helper.getAuthorisedIdentity(request)).thenReturn(IDENTITY);
        when(helper.getAuthorisedIdentityType(request)).thenReturn(OAUTH2);
        when(helper.isOauth2IdentityType(OAUTH2)).thenReturn(true);
        when(helper.getAuthorisedUser(request)).thenReturn(null);
        
        assertFalse(interceptor.preHandle(request, response, null));
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    void preHandleInvalidPermission() {
        when(helper.getAuthorisedIdentity(request)).thenReturn(IDENTITY);
        when(helper.getAuthorisedIdentityType(request)).thenReturn(OAUTH2);
        when(helper.isOauth2IdentityType(OAUTH2)).thenReturn(true);
        when(helper.getAuthorisedUser(request)).thenReturn(USER);
        when(helper.getTokenPermissions(request)).thenReturn(token);
        when(helper.validTokenPermissions(token, Key.USER_PRESENTER, Value.CREATE)).thenReturn(false);
        
        assertFalse(interceptor.preHandle(request, response, null));
        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    void preHandleNullPermission() {
        when(helper.getAuthorisedIdentity(request)).thenReturn(IDENTITY);
        when(helper.getAuthorisedIdentityType(request)).thenReturn(OAUTH2);
        when(helper.isOauth2IdentityType(OAUTH2)).thenReturn(true);
        when(helper.getAuthorisedUser(request)).thenReturn(USER);
        when(helper.getTokenPermissions(request)).thenReturn(null);
        when(helper.validTokenPermissions(null, Key.USER_PRESENTER, Value.CREATE)).thenReturn(false);
        
        assertFalse(interceptor.preHandle(request, response, null));
        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

}

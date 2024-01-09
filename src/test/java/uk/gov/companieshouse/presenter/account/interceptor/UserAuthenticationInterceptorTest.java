package uk.gov.companieshouse.presenter.account.interceptor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.http.HttpServletRequest;
import uk.gov.companieshouse.api.util.security.TokenPermissions;
import uk.gov.companieshouse.api.util.security.Permission.Key;

import java.text.MessageFormat;

@ExtendWith(MockitoExtension.class)
class UserAuthenticationInterceptorTest {
    public static final String AUTHORISED_ROLES = "ERIC-Authorised-Roles";
    public static final String AUTHORISED_USER = "ERIC-Authorised-User";
    public static final String USER_FORMAT = "{0};forename={1};surname={2}";
    public static final String AUTHORISED_KEY_ROLES = "ERIC-Authorised-Key-Roles";
    public static final String ROLE_1_ROLE_2 = "role-1 role-2";
    public static final String ROLE_1 = "role-1";
    public static final String ROLE_2 = "role-2";
    private static final String USER_EMAIL = "qschaden@somewhere.email.com";
    private static final String USER_FORENAME = "Quentin";
    private static final String USER_SURNAME = "Schaden";
    private static final Key PRESENTER_KEY = Key.USER_PRESENTER;
    private static final String CREATE = "create";
    private static final String TOKEN_PERMISSIONS_ATTRIBUTE = "token_permissions";

    private AuthenticationHelper testHelper;

    @Mock
    private HttpServletRequest request;

    @Mock
    private TokenPermissions tokenPermissions;

    @BeforeEach
    void setUp() {
        testHelper = new AuthenticationHelper();
    }

    @Test
    void getAuthorisedIdentityWhenRequestNull() {
        assertThat(testHelper.getAuthorisedIdentity(null), is(nullValue()));
    }

    @Test
    void validTokenPermissions() {
        when(tokenPermissions.hasPermission(PRESENTER_KEY, CREATE)).thenReturn(true);

        assertTrue(testHelper.validTokenPermissions(tokenPermissions, PRESENTER_KEY, CREATE));
    }

    @Test
    void validTokenPermissionsNoUserPresenter() {
        assertFalse(testHelper.validTokenPermissions(tokenPermissions, PRESENTER_KEY, TOKEN_PERMISSIONS_ATTRIBUTE));
    }

    @Test
    void getAuthorisedIdentityWhenRequestNotNull() {
        String expected = "identity";

        when(request.getHeader("ERIC-Identity")).thenReturn(expected);

        assertThat(testHelper.getAuthorisedIdentity(request), is(expected));
    }

    @Test
    void getAuthorisedIdentityType() {
        String expected = "identity-type";

        when(request.getHeader("ERIC-Identity-Type")).thenReturn(expected);

        assertThat(testHelper.getAuthorisedIdentityType(request), is(expected));
    }

    @Test
    void isOauth2IdentityTypeWhenItIs() {
        assertThat(testHelper.isOauth2IdentityType("oauth2"), is(true));
    }

    @Test
    void isOauth2IdentityTypeWhenItIsNot() {
        assertThat(testHelper.isOauth2IdentityType("Oauth2"), is(false));
    }

    @Test
    void getAuthorisedUser() {
        String expected = "authorised-user";

        when(request.getHeader(AUTHORISED_USER)).thenReturn(expected);

        assertThat(testHelper.getAuthorisedUser(request), is(expected));
    }

    @Test
    void getAuthorisedUserEmail() {
        when(request.getHeader(AUTHORISED_USER)).thenReturn(
                MessageFormat.format(USER_FORMAT, USER_EMAIL, USER_FORENAME, USER_SURNAME));

        assertThat(testHelper.getAuthorisedUserEmail(request), is(USER_EMAIL));
    }

    @Test
    void getAuthorisedUserEmailWhenUserNul() {
        assertThat(testHelper.getAuthorisedUserEmail(request), is(nullValue()));
    }

    @Test
    void getAuthorisedUserEmailWhenUserMissing() {
        when(request.getHeader(AUTHORISED_USER)).thenReturn("");

        assertThat(testHelper.getAuthorisedUserEmail(request), is(nullValue()));
    }

    @Test
    void getAuthorisedUserEmailWhenEmpty() {
        when(request.getHeader(AUTHORISED_USER)).thenReturn(";");

        assertThat(testHelper.getAuthorisedUserEmail(request), is(nullValue()));
    }

    @Test
    void getAuthorisedUserEmailWhenNull() {
        assertThat(testHelper.getAuthorisedUserEmail(request), is(nullValue()));
    }

    @Test
    void getAuthorisedUserForename() {
        when(request.getHeader(AUTHORISED_USER)).thenReturn(
                MessageFormat.format(USER_FORMAT, USER_EMAIL, USER_FORENAME, USER_SURNAME));

        assertThat(testHelper.getAuthorisedUserForename(request), is(USER_FORENAME));
    }

    @Test
    void getAuthorisedUserForenameWhenUserNull() {
        assertThat(testHelper.getAuthorisedUserForename(request), is(nullValue()));
    }

    @Test
    void getAuthorisedUserForenameWhenUserEmpty() {
        when(request.getHeader(AUTHORISED_USER)).thenReturn("");

        assertThat(testHelper.getAuthorisedUserForename(request), is(nullValue()));
    }

    @Test
    void getAuthorisedUserForenameWhenMissing() {
        when(request.getHeader(AUTHORISED_USER)).thenReturn(MessageFormat.format("{0}", USER_EMAIL));

        assertThat(testHelper.getAuthorisedUserForename(request), is(nullValue()));
    }

    @Test
    void getAuthorisedUserForenameWhenUnnamed() {
        when(request.getHeader(AUTHORISED_USER)).thenReturn(
                MessageFormat.format("{0};{1}", USER_EMAIL, USER_FORENAME));

        assertThat(testHelper.getAuthorisedUserForename(request), is(nullValue()));
    }

    @Test
    void getAuthorisedUserSurname() {
        when(request.getHeader(AUTHORISED_USER)).thenReturn(
                MessageFormat.format(USER_FORMAT, USER_EMAIL, USER_FORENAME, USER_SURNAME));

        assertThat(testHelper.getAuthorisedUserSurname(request), is(USER_SURNAME));
    }

    @Test
    void getAuthorisedUserSurnameWhenMissing() {
        when(request.getHeader(AUTHORISED_USER)).thenReturn(
                MessageFormat.format("{0};forename={1}", USER_EMAIL, USER_FORENAME));

        assertThat(testHelper.getAuthorisedUserSurname(request), is(nullValue()));
    }

    @Test
    void getAuthorisedUserSurnameWhenUnnamed() {
        when(request.getHeader(AUTHORISED_USER)).thenReturn(
                MessageFormat.format("{0};forename={1};{2}", USER_EMAIL, USER_FORENAME, USER_SURNAME));

        assertThat(testHelper.getAuthorisedUserSurname(request), is(nullValue()));
    }

    @Test
    void getTokenPermissions() {
        TokenPermissions tokenPermissions = mock(TokenPermissions.class);

        when(request.getAttribute(TOKEN_PERMISSIONS_ATTRIBUTE)).thenReturn(tokenPermissions);

        assertThat(testHelper.getTokenPermissions(request), is(tokenPermissions));
    }

    @Test
    void getTokenPermissionsNoToken() {
        assertNull(testHelper.getTokenPermissions(request));
    }

    @Test
    void getRequestMethod() {

        when(request.getMethod()).thenReturn("GET");
        
        assertEquals("GET", testHelper.getRequestMethod(request));
    }


    @Test
    void getRequestMethodPost() {

        when(request.getMethod()).thenReturn("POST");

        assertEquals("POST", testHelper.getRequestMethod(request));
    }

    @Test
    void getRequestMethodRequestIsNull() {
        assertEquals(null, testHelper.getRequestMethod(null));
    }

    @Test
    void isGetMethod() {

        assertTrue(testHelper.isGetMethod("GET"));
    }


    @Test
    void isGetMethodButRequestIsPost() {

        assertFalse(testHelper.isGetMethod("POST"));
    }

    @Test
    void isGetMethodNoRequest() {
        assertFalse(testHelper.isGetMethod(null));
    }

}

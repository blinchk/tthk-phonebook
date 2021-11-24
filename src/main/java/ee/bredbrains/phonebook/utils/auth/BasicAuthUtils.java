package ee.bredbrains.phonebook.utils.auth;

import ee.bredbrains.phonebook.exception.auth.AuthorizationException;
import ee.bredbrains.phonebook.model.payload.request.header.BasicAuthLogicCredentials;
import org.apache.tomcat.websocket.AuthenticationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BasicAuthUtils {
    private static final int HEADER_SUBSTRING_START_INDEX = "Basic".length();
    private static final int USERNAME_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;

    public static BasicAuthLogicCredentials decode(String authorizationHeader) {
        if (authorizationHeader.length() < HEADER_SUBSTRING_START_INDEX) {
            throw new AuthorizationException("Cannot authorize without Basic authentication.");
        }
        final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
        String base64Credentials = authorizationHeader.substring(HEADER_SUBSTRING_START_INDEX).trim();
        byte[] credentialsDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credentialsDecoded, DEFAULT_CHARSET);
        final String[] values = credentials.split(":", 2);
        String username = values[USERNAME_INDEX];
        String password = values[PASSWORD_INDEX];
        return new BasicAuthLogicCredentials(username, password);
    }
}

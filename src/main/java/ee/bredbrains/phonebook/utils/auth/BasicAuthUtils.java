package ee.bredbrains.phonebook.utils.auth;

import ee.bredbrains.phonebook.model.payload.request.header.BasicAuthLogicCredentials;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BasicAuthUtils {
    public static BasicAuthLogicCredentials decode(String authorizationHeader) {
        final int HEADER_SUBSTRING_START_INDEX = "Basic".length();
        final int USERNAME_INDEX = 0;
        final int PASSWORD_INDEX = 1;
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

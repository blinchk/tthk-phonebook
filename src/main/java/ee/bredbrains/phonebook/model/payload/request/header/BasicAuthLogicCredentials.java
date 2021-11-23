package ee.bredbrains.phonebook.model.payload.request.header;

import ee.bredbrains.phonebook.model.payload.request.Request;

public class BasicAuthLogicCredentials {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BasicAuthLogicCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

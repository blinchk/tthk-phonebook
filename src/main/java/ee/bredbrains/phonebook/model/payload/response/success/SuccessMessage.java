package ee.bredbrains.phonebook.model.payload.response.success;

public class SuccessMessage {
    private final boolean success = true;
    private String message;

    public SuccessMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }
}

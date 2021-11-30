package ee.bredbrains.phonebook.model.payload.response.success.contact;

import ee.bredbrains.phonebook.model.payload.response.success.SuccessMessage;

public class DeleteContactSuccessMessage extends SuccessMessage {
    public DeleteContactSuccessMessage(Long id) {
        super(String.format("Contact with ID %d deleted successfully", id));
    }
}

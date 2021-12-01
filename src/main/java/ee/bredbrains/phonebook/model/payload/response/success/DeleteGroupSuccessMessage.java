package ee.bredbrains.phonebook.model.payload.response.success;

public class DeleteGroupSuccessMessage extends SuccessMessage {
    public DeleteGroupSuccessMessage(Long id) {
        super(String.format("Group with ID %d deleted successfully", id));
    }
}

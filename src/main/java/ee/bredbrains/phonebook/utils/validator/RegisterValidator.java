package ee.bredbrains.phonebook.utils.validator;

import ee.bredbrains.phonebook.exception.user.UserConflictException;
import ee.bredbrains.phonebook.model.payload.request.RegisterRequest;
import ee.bredbrains.phonebook.repository.UserRepository;

public class RegisterValidator implements RequestValidator<RegisterRequest> {
    private final UserRepository repository;

    public RegisterValidator(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validate(RegisterRequest request) {
        if (repository.existsByUsername(request.getUsername()))
            throw new UserConflictException("username", request.getUsername());
        if (repository.existsByEmail(request.getEmail()))
            throw new UserConflictException("email", request.getEmail());
    }
}

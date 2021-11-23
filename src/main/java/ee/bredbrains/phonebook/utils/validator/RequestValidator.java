package ee.bredbrains.phonebook.utils.validator;

import ee.bredbrains.phonebook.model.payload.request.Request;

public interface RequestValidator<T extends Request> {
    void validate(T request) throws Exception;
}

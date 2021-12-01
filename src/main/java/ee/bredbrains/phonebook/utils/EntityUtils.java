package ee.bredbrains.phonebook.utils;

import ee.bredbrains.phonebook.exception.contact.InvalidIdException;

public class EntityUtils {
    public static Long parseId(String id) throws InvalidIdException {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new InvalidIdException(id);
        }
    }
}

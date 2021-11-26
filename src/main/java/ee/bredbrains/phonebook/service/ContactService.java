package ee.bredbrains.phonebook.service;

import ee.bredbrains.phonebook.model.Contact;
import ee.bredbrains.phonebook.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final ContactRepository repository;
    private String username;

    public ContactService(ContactRepository repository) {
        this.repository = repository;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Contact> findAll() {
        return repository.findAllByCreatedBy_Username(username);
    }

    public List<Contact> findAllByFirstNameStartsWith(String query) {
        return repository.findAllByCreatedBy_UsernameAndFirstNameStartsWithIgnoreCase(username, query);
    }

    public List<Contact> findAllByPhone(String phone) {
        return repository.findAllByCreatedBy_UsernameAndPhoneContains(username, phone);
    }
}

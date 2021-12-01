package ee.bredbrains.phonebook.service;

import ee.bredbrains.phonebook.exception.contact.ContactNotFoundException;
import ee.bredbrains.phonebook.exception.permission.PermissionException;
import ee.bredbrains.phonebook.model.Contact;
import ee.bredbrains.phonebook.repository.ContactRepository;
import ee.bredbrains.phonebook.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Facade service for {@link ContactRepository}.
 */
@Service
public class ContactService extends AuthenticatedService<ContactRepository> {
    public ContactService(ContactRepository contactRepository, UserRepository userRepository) {
        super(contactRepository, userRepository);
    }

    public List<Contact> findAll() {
        return repository.findAllByCreatedBy_Username(currentUser.getUsername());
    }

    public List<Contact> findAllByFirstNameStartsWith(String query) {
        return repository.findAllByCreatedBy_UsernameAndFirstNameStartsWithIgnoreCase(currentUser.getUsername(), query);
    }

    public List<Contact> findAllByPhone(String phone) {
        return repository.findAllByCreatedBy_UsernameAndPhoneContains(currentUser.getUsername(), phone);
    }

    public Contact save(Contact contact) {
        contact.setCreatedBy(currentUser);
        return repository.save(contact);
    }

    public void delete(Long id) {
        Contact contact = repository.findById(id).orElseThrow(() -> new ContactNotFoundException(id));
        if (!Objects.equals(contact.getCreatedBy().getId(), currentUser.getId())) {
            throw new PermissionException();
        }
        repository.delete(contact);
    }
}

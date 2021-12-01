package ee.bredbrains.phonebook.service;

import ee.bredbrains.phonebook.exception.contact.EntityNotFoundException;
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
public class ContactService extends AuthenticatedService<ContactRepository, Contact> {
    public ContactService(ContactRepository repository, UserRepository userRepository) {
        super(repository, userRepository);
    }

    public List<Contact> findAll() {
        return repository.findAllByCreatedBy_Username(currentUser.getUsername());
    }

    public Contact save(Contact contact) {
        contact.setCreatedBy(currentUser);
        return super.save(contact);
    }

    public void delete(Long id) {
        Contact contact = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        if (!Objects.equals(contact.getCreatedBy().getId(), currentUser.getId())) {
            throw new PermissionException();
        }
        repository.delete(contact);
    }
}

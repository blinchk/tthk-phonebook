package ee.bredbrains.phonebook.service;

import ee.bredbrains.phonebook.exception.auth.AuthorizationException;
import ee.bredbrains.phonebook.exception.permission.PermissionException;
import ee.bredbrains.phonebook.model.Contact;
import ee.bredbrains.phonebook.model.User;
import ee.bredbrains.phonebook.repository.ContactRepository;
import ee.bredbrains.phonebook.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

/**
 * Facade service for {ContactRepository}
 */
@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final UserRepository userRepository;
    private User currentUser;

    public ContactService(ContactRepository contactRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    public void findCurrentUser(Principal principal) {
        if (principal == null) throw new AuthorizationException();
        String username = principal.getName();
        var user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new AuthorizationException();
        }
        this.currentUser = user.get();
    }

    public List<Contact> findAll() {
        return contactRepository.findAllByCreatedBy_Username(currentUser.getUsername());
    }

    public List<Contact> findAllByFirstNameStartsWith(String query) {
        return contactRepository.findAllByCreatedBy_UsernameAndFirstNameStartsWithIgnoreCase(currentUser.getUsername(), query);
    }

    public List<Contact> findAllByPhone(String phone) {
        return contactRepository.findAllByCreatedBy_UsernameAndPhoneContains(currentUser.getUsername(), phone);
    }

    public Contact update(Contact contact) {
        if (!Objects.equals(contactRepository.getById(contact.getId()).getCreatedBy().getUsername(), currentUser.getUsername())) {
            throw new PermissionException();
        }
        return contactRepository.save(contact);
    }

    public Contact save(Contact contact) {
        contact.setCreatedBy(currentUser);
        return contactRepository.save(contact);
    }
}

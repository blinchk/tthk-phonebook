package ee.bredbrains.phonebook.service;

import ee.bredbrains.phonebook.exception.contact.EntityNotFoundException;
import ee.bredbrains.phonebook.exception.contact.MalformedDuplicateException;
import ee.bredbrains.phonebook.exception.permission.PermissionException;
import ee.bredbrains.phonebook.model.Contact;
import ee.bredbrains.phonebook.model.Group;
import ee.bredbrains.phonebook.model.User;
import ee.bredbrains.phonebook.repository.ContactRepository;
import ee.bredbrains.phonebook.repository.GroupRepository;
import ee.bredbrains.phonebook.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Facade service for {@link ee.bredbrains.phonebook.repository.GroupRepository}.
 */
@Service
public class GroupService extends AuthenticatedService<GroupRepository, Group> {
    private final ContactRepository contactRepository;

    public GroupService(GroupRepository repository, UserRepository userRepository, ContactRepository contactRepository) {
        super(repository, userRepository);
        this.contactRepository = contactRepository;
    }

    public List<Group> findAll() {
        return repository.findAllByCreatedBy_Username(currentUser.getUsername());
    }

    public Group save(Group group) {
        group.setCreatedBy(currentUser);
        return super.save(group);
    }

    public void delete(Long id) {
        Group group = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        if (!Objects.equals(group.getCreatedBy().getId(), currentUser.getId())) {
            throw new PermissionException();
        }
        repository.delete(group);
    }

    public Group sharedSave(Long id) {
        Group group = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        Group newGroup = Group.of(group);
        newGroup.setCreatedBy(currentUser);
        Group savedGroup = repository.save(newGroup);
        List<Contact> contacts = reassignContacts(group, savedGroup, currentUser);
        contactRepository.saveAll(contacts);
        return repository.findById(savedGroup.getId()).orElseThrow(MalformedDuplicateException::new);
    }

    private List<Contact> reassignContacts(Group group, Group newGroup, User newUser) {
        List<Contact> updatedContacts = new ArrayList<>();
        for (Contact contact : group.getContacts()) {
            Contact newContact = Contact.of(contact);
            newContact.setGroup(newGroup);
            newContact.setCreatedBy(newUser);
            updatedContacts.add(newContact);
        }
        return updatedContacts;
    }
}

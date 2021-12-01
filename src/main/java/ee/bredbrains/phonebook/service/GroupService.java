package ee.bredbrains.phonebook.service;

import ee.bredbrains.phonebook.exception.contact.EntityNotFoundException;
import ee.bredbrains.phonebook.exception.permission.PermissionException;
import ee.bredbrains.phonebook.model.Group;
import ee.bredbrains.phonebook.model.User;
import ee.bredbrains.phonebook.repository.ContactRepository;
import ee.bredbrains.phonebook.repository.GroupRepository;
import ee.bredbrains.phonebook.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Facade service for {@link ee.bredbrains.phonebook.repository.GroupRepository}.
 */
@Service
public class GroupService extends AuthenticatedService<GroupRepository, Group> {
    public GroupService(GroupRepository repository, UserRepository userRepository) {
        super(repository, userRepository);
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
}

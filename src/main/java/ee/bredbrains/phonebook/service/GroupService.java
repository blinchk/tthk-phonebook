package ee.bredbrains.phonebook.service;

import ee.bredbrains.phonebook.model.Group;
import ee.bredbrains.phonebook.model.User;
import ee.bredbrains.phonebook.repository.ContactRepository;
import ee.bredbrains.phonebook.repository.GroupRepository;
import ee.bredbrains.phonebook.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Facade service for {@link ee.bredbrains.phonebook.repository.GroupRepository}.
 */
@Service
public class GroupService extends AuthenticatedService<GroupRepository> {
    public GroupService(GroupRepository repository, UserRepository userRepository) {
        super(repository, userRepository);
    }
}

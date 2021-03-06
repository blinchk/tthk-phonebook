package ee.bredbrains.phonebook.service;

import ee.bredbrains.phonebook.exception.auth.AuthorizationException;
import ee.bredbrains.phonebook.model.User;
import ee.bredbrains.phonebook.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.security.Principal;

public abstract class AuthenticatedService<R extends JpaRepository<T, Long>, T> extends BaseService<R, T> {
    protected final UserRepository userRepository;
    protected User currentUser;

    public AuthenticatedService(R repository, UserRepository userRepository) {
        super(repository);
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
}

package ee.bredbrains.phonebook.service;

import ee.bredbrains.phonebook.model.User;
import ee.bredbrains.phonebook.model.UserDetailsImpl;
import ee.bredbrains.phonebook.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with username %s is not found.", username))
        );

        return UserDetailsImpl.build(user);
    }
}

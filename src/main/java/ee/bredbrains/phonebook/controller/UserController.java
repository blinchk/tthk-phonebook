package ee.bredbrains.phonebook.controller;

import ee.bredbrains.phonebook.exception.user.UserNotFoundException;
import ee.bredbrains.phonebook.model.User;
import ee.bredbrains.phonebook.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User current(Principal principal) {
        return repository.findByUsername(principal.getName()).orElseThrow(UserNotFoundException::new);
    }
}

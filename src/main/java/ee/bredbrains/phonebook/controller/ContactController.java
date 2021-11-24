package ee.bredbrains.phonebook.controller;

import ee.bredbrains.phonebook.exception.contact.InvalidIdException;
import ee.bredbrains.phonebook.exception.contact.ContactNotFoundException;
import ee.bredbrains.phonebook.exception.user.UserNotFoundException;
import ee.bredbrains.phonebook.model.Contact;
import ee.bredbrains.phonebook.repository.ContactRepository;
import ee.bredbrains.phonebook.repository.UserRepository;
import ee.bredbrains.phonebook.service.UserDetailsServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/contact")
public class ContactController {
    private final ContactRepository repository;
    private final UserRepository userRepository;

    public ContactController(ContactRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<Contact> search(String query) {
        Pattern pattern = Pattern.compile("(\\+)?[0-9]+$");
        if (pattern.matcher(query).matches()) {
            return repository.findAllByPhone(query);
        } else {
            return repository.findAllByNameContains(query);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Contact> all(@RequestParam() Optional<String> query) {
        if (query.isPresent()) {
            return search(query.get());
        } else {
            return repository.findAll();
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Contact one(@PathVariable String id) {
        long parsedId;
        try {
            parsedId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new InvalidIdException(id);
        }
        Optional<Contact> contact = repository.findById(parsedId);
        return contact.orElseThrow(() -> new ContactNotFoundException(parsedId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Contact add(@RequestBody Contact contact, Principal principal) {
        contact.setCreatedBy(userRepository
                .findByUsername(principal.getName())
                .orElseThrow(UserNotFoundException::new));
        return repository.save(contact);
    }
}

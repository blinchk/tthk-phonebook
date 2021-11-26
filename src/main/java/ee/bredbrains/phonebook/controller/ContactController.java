package ee.bredbrains.phonebook.controller;

import ee.bredbrains.phonebook.exception.contact.ContactNotFoundException;
import ee.bredbrains.phonebook.exception.contact.InvalidIdException;
import ee.bredbrains.phonebook.exception.user.UserNotFoundException;
import ee.bredbrains.phonebook.model.Contact;
import ee.bredbrains.phonebook.repository.ContactRepository;
import ee.bredbrains.phonebook.repository.UserRepository;
import ee.bredbrains.phonebook.service.ContactService;
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
    private final ContactService service;
    private final UserRepository userRepository;

    public ContactController(ContactRepository repository, ContactService service, UserRepository userRepository) {
        this.repository = repository;
        this.service = service;
        this.userRepository = userRepository;
    }

    public List<Contact> search(String query, String username) {
        Pattern pattern = Pattern.compile("(\\+)?[0-9]+$");
        service.setUsername(username);
        if (pattern.matcher(query).matches()) {
            return service.findAllByPhone(query);
        } else {
            return service.findAllByFirstNameStartsWith(query);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Contact> all(@RequestParam() Optional<String> query, Principal principal) {
        service.setUsername(principal.getName());
        if (query.isPresent()) {
            System.out.println(query.get());
            return search(query.get(), principal.getName());
        } else {
            return service.findAll();
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

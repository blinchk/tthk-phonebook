package ee.bredbrains.phonebook.controller;

import ee.bredbrains.phonebook.exception.contact.InvalidIdException;
import ee.bredbrains.phonebook.exception.user.UserNotFoundException;
import ee.bredbrains.phonebook.model.Contact;
import ee.bredbrains.phonebook.repository.ContactRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/contact")
public class ContactController {
    private final ContactRepository repository;

    public ContactController(ContactRepository repository) {
        this.repository = repository;
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
        return contact.orElseThrow(() -> new UserNotFoundException(parsedId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Contact add(@RequestBody Contact contact) {
        return repository.save(contact);
    }
}

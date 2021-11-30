package ee.bredbrains.phonebook.controller;

import ee.bredbrains.phonebook.exception.contact.ContactNotFoundException;
import ee.bredbrains.phonebook.exception.contact.InvalidIdException;
import ee.bredbrains.phonebook.model.Contact;
import ee.bredbrains.phonebook.model.payload.response.success.contact.DeleteContactSuccessMessage;
import ee.bredbrains.phonebook.model.payload.response.success.SuccessMessage;
import ee.bredbrains.phonebook.repository.ContactRepository;
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

    public ContactController(ContactRepository repository, ContactService service) {
        this.repository = repository;
        this.service = service;
    }

    private List<Contact> search(String query) {
        Pattern pattern = Pattern.compile("(\\+)?[0-9]+$");
        if (pattern.matcher(query).matches()) {
            return service.findAllByPhone(query);
        } else {
            return service.findAllByFirstNameStartsWith(query);
        }
    }

    private Long parseId(String stringId) throws InvalidIdException {
        try {
            return Long.parseLong(stringId);
        } catch (NumberFormatException e) {
            throw new InvalidIdException(stringId);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Contact> all(@RequestParam() Optional<String> query, Principal principal) {
        service.findCurrentUser(principal);
        if (query.isPresent()) {
            System.out.println(query.get());
            return search(query.get());
        } else {
            return service.findAll();
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Contact one(@PathVariable String id) {
        Long parsedId = parseId(id);
        Optional<Contact> contact = repository.findById(parsedId);
        return contact.orElseThrow(() -> new ContactNotFoundException(parsedId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Contact save(@RequestBody Contact contact, Principal principal) {
        service.findCurrentUser(principal);
        return service.save(contact);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessMessage delete(@PathVariable String id, Principal principal) {
        service.findCurrentUser(principal);
        Long parsedId = parseId(id);
        service.delete(parsedId);
        return new DeleteContactSuccessMessage(parsedId);
    }
}

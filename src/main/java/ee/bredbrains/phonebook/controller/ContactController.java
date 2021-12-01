package ee.bredbrains.phonebook.controller;

import ee.bredbrains.phonebook.exception.contact.EntityNotFoundException;
import ee.bredbrains.phonebook.model.Contact;
import ee.bredbrains.phonebook.model.payload.response.success.DeleteContactSuccessMessage;
import ee.bredbrains.phonebook.model.payload.response.success.SuccessMessage;
import ee.bredbrains.phonebook.repository.ContactRepository;
import ee.bredbrains.phonebook.service.ContactService;
import ee.bredbrains.phonebook.utils.EntityUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {
    private final ContactRepository repository;
    private final ContactService service;

    public ContactController(ContactRepository repository, ContactService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Contact> all(Principal principal) {
        service.findCurrentUser(principal);
        return service.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Contact one(@PathVariable String id) {
        Long parsedId = EntityUtils.parseId(id);
        return repository.findById(parsedId).orElseThrow(() -> new EntityNotFoundException(parsedId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Contact save(@RequestBody Contact contact, Principal principal) {
        service.findCurrentUser(principal);
        return service.save(contact);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessMessage delete(@PathVariable String id, Principal principal) {
        service.findCurrentUser(principal);
        Long parsedId = EntityUtils.parseId(id);
        service.delete(parsedId);
        return new DeleteContactSuccessMessage(parsedId);
    }
}

package ee.bredbrains.phonebook.controller;

import ee.bredbrains.phonebook.exception.contact.EntityNotFoundException;
import ee.bredbrains.phonebook.model.Group;
import ee.bredbrains.phonebook.model.payload.response.success.DeleteGroupSuccessMessage;
import ee.bredbrains.phonebook.model.payload.response.success.SuccessMessage;
import ee.bredbrains.phonebook.repository.GroupRepository;
import ee.bredbrains.phonebook.service.GroupService;
import ee.bredbrains.phonebook.utils.EntityUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {
    private final GroupService service;
    private final GroupRepository repository;

    public GroupController(GroupRepository repository, GroupService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Group> all(Principal principal) {
        service.findCurrentUser(principal);
        return service.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Group one(@PathVariable String id) {
        Long parsedId = EntityUtils.parseId(id);
        return repository.findById(parsedId).orElseThrow(() -> new EntityNotFoundException(parsedId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Group save(@RequestBody Group group, Principal principal) {
        service.findCurrentUser(principal);
        return service.save(group);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessMessage delete(@PathVariable String id, Principal principal) {
        service.findCurrentUser(principal);
        Long parsedId = EntityUtils.parseId(id);
        service.delete(parsedId);
        return new DeleteGroupSuccessMessage(parsedId);
    }
}

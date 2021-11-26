package ee.bredbrains.phonebook.repository;

import ee.bredbrains.phonebook.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByCreatedBy_UsernameAndPhoneContains(@NotBlank @Size(max = 32) String createdBy_username, String phone);

    List<Contact> findAllByCreatedBy_UsernameAndFirstNameStartsWithIgnoreCase(String createdBy_username, String firstName);

    List<Contact> findAllByCreatedBy_Username(@NotBlank @Size(max = 32) String createdBy_username);
}

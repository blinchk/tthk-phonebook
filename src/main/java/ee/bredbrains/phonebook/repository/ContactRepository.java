package ee.bredbrains.phonebook.repository;

import ee.bredbrains.phonebook.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}

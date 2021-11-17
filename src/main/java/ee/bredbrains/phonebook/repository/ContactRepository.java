package ee.bredbrains.phonebook.repository;

import ee.bredbrains.phonebook.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByPhone(String phone);
    @Query("SELECT c FROM Contact c WHERE CONCAT(c.firstName, ' ', c.lastName) LIKE '%?1%'")
    List<Contact> findAllByNameContains(String name);
}

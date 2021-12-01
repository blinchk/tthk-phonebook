package ee.bredbrains.phonebook.repository;

import ee.bredbrains.phonebook.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findAllByCreatedBy_Username(@NotBlank @Size(max = 32) String createdBy_username);
}

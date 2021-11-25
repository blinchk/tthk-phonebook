package ee.bredbrains.phonebook.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "contacts", uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
public class Contact implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "First name cannot be empty")
    @Length(min = 3, max = 256, message = "First name should be between 3 and 256 characters.")
    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String phone;

    @Column
    @Email
    private String email;

    @ManyToOne(cascade = CascadeType.MERGE)
    private User createdBy;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Group group;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}

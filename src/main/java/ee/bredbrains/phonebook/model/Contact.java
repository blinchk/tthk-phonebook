package ee.bredbrains.phonebook.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "contact", uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
public class Contact implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "First name cannot be empty")
    @Length(min = 3, max = 256, message = "First name should be between 3 and 256 characters.")
    @Column
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    @Length(min = 3, max = 256, message = "Last name should be between 3 and 256 characters.")
    @Column
    private String lastName;

    @Column
    private String phone;

    @Column
    @Email
    private String email;

    @Column
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String instagram;

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

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    @JsonIgnore
    public String getFullName() {
        return firstName + " " + lastName;
    }
}

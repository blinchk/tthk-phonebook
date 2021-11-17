package ee.bredbrains.phonebook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
public class Contact {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @Column
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private String instagram;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

        public String getName() {
        return firstName + " " + lastName;
    }
}

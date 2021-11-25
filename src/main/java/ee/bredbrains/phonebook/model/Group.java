package ee.bredbrains.phonebook.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "groups", uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
public class Group {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String title;

    @OneToMany(mappedBy = "group")
    private List<Contact> contacts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Contact> getContacts() {
        return contacts;
    }
}

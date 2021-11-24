package ee.bredbrains.phonebook.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "group")
public class Group {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Column
    private String title;
    @OneToMany
    private List<Contact> contact;

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

    public List<Contact> getContact() {
        return contact;
    }
}

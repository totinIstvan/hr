package hu.webuni.hr.totinistvan.model.entity;

import javax.persistence.*;

@NamedEntityGraph(name = "Company.type", attributeNodes = @NamedAttributeNode("name"))
@Entity
public class CompanyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    public CompanyType() {
    }

    public CompanyType(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

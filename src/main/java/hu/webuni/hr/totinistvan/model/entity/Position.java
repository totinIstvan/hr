package hu.webuni.hr.totinistvan.model.entity;

import hu.webuni.hr.totinistvan.model.Qualification;

import javax.persistence.*;
import java.util.List;

//@NamedEntityGraph(name = "Position.employees", attributeNodes = @NamedAttributeNode("employees"))
@Entity
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private Qualification qualification;

//    @OneToMany(mappedBy = "position")
//    private List<Employee> employees;

    public Position() {
    }

    public Position(String name) {
        this.name = name;
    }

    public Position(String name, Qualification qualification) {
        this.name = name;
        this.qualification = qualification;
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

//    public List<Employee> getEmployees() {
//        return employees;
//    }
//
//    public void setEmployees(List<Employee> employees) {
//        this.employees = employees;
//    }

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }
}

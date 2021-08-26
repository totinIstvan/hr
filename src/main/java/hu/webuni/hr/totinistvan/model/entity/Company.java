package hu.webuni.hr.totinistvan.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(
        name = "Company.full",
        attributeNodes = {
                @NamedAttributeNode(value = "employees", subgraph = "employees"),
                @NamedAttributeNode("companyType")},
        subgraphs = @NamedSubgraph(name = "employees", attributeNodes = @NamedAttributeNode("position"))
)
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String registrationNumber;
    private String name;
    private String address;

    @ManyToOne
    private CompanyType companyType;

    @OneToMany(mappedBy = "company")
    private List<Employee> employees;

    public Company() {
    }

    public Company(String registrationNumber, String name, String address) {
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
    }

    public Company(String registrationNumber, String name, String address, CompanyType companyType) {
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
        this.companyType = companyType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }

    public void addEmployee(Employee employee) {
        if (this.employees == null) {
            this.employees = new ArrayList<>();
        }
        this.employees.add(employee);
        employee.setCompany(this);
    }
}

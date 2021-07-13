package hu.webuni.hr.totinistvan.model.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CompanyDto {

    private long id;
    private String registrationNumber;
    private String name;
    private String address;

    private List<EmployeeDto> employees = new ArrayList<>();

    public CompanyDto(long id, String name, String address) {
        this.id = id;
        this.registrationNumber = UUID.randomUUID().toString().substring(0, 8);
        this.name = name;
        this.address = address;
    }

    public CompanyDto() {
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

    public List<EmployeeDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDto> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "CompanyDto{" +
                "id=" + id +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", employees=" + employees +
                '}';
    }
}

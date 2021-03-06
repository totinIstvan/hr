package hu.webuni.hr.totinistvan.model.dto;

import hu.webuni.hr.totinistvan.model.entity.CompanyType;

import java.util.ArrayList;
import java.util.List;

public class CompanyDto {

    private long id;

    private String registrationNumber;

    private String name;

    private String address;

    private CompanyType companyType;

    private List<EmployeeDto> employees = new ArrayList<>();

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

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
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

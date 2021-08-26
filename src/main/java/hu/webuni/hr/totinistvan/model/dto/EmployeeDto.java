package hu.webuni.hr.totinistvan.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.webuni.hr.totinistvan.model.entity.Position;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

public class EmployeeDto {

    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String position;
    @Min(0)
    private int salary;
    @Past
    private LocalDateTime joinDate;

    @JsonIgnore
    private CompanyDto companyDto;

    public EmployeeDto(long id, String name, String position, int salary, LocalDateTime joinDate) {
        this.id = id;

        this.name = name;
        this.position = position;
        this.salary = salary;
        this.joinDate = joinDate;
    }

    public EmployeeDto(long id, String name, int salary, LocalDateTime joinDate) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.joinDate = joinDate;
    }

    public EmployeeDto(String name, String position, int salary, LocalDateTime joinDate) {
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.joinDate = joinDate;
    }

    public EmployeeDto() {
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }

    public CompanyDto getCompanyDto() {
        return companyDto;
    }

    public void setCompanyDto(CompanyDto companyDto) {
        this.companyDto = companyDto;
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", joinDate=" + joinDate +
                '}';
    }
}

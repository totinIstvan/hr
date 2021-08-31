package hu.webuni.hr.totinistvan.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne
    private Position position;

    private int salary;

    private LocalDateTime joinDate;

    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "applicant")
    private List<LeaveOfAbsenceRequest> leaveOfAbsenceRequests;

    @ManyToOne
    private Employee manager;

    public Employee() {
    }

    public Employee(long id, String name, int salary, LocalDateTime joinDate) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.joinDate = joinDate;
    }

    public Employee(String name, int salary, LocalDateTime joinDate) {
        this.name = name;
        this.salary = salary;
        this.joinDate = joinDate;
    }

    public Employee(String name, Position position, int salary, LocalDateTime joinDate) {
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.joinDate = joinDate;
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<LeaveOfAbsenceRequest> getLeaveOfAbsenceRequests() {
        return leaveOfAbsenceRequests;
    }

    public void setLeaveOfAbsenceRequests(List<LeaveOfAbsenceRequest> leaveOfAbsenceRequests) {
        this.leaveOfAbsenceRequests = leaveOfAbsenceRequests;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public void addLeaveOfAbsenceRequest(LeaveOfAbsenceRequest leaveOfAbsenceRequest) {
        if (this.leaveOfAbsenceRequests == null) {
            this.leaveOfAbsenceRequests = new ArrayList<>();
        }
        this.leaveOfAbsenceRequests.add(leaveOfAbsenceRequest);
        leaveOfAbsenceRequest.setApplicant(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return salary == employee.salary && name.equals(employee.name) && position.equals(employee.position) && joinDate.equals(employee.joinDate) && Objects.equals(company, employee.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, position, salary, joinDate, company);
    }
}

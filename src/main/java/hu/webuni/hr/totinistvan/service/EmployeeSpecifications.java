package hu.webuni.hr.totinistvan.service;

import hu.webuni.hr.totinistvan.model.entity.Company_;
import hu.webuni.hr.totinistvan.model.entity.Employee;
import hu.webuni.hr.totinistvan.model.entity.Employee_;
import hu.webuni.hr.totinistvan.model.entity.Position_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;

public class EmployeeSpecifications {

    public static Specification<Employee> hasId(long id) {
        return (root, cq, cb) -> cb.equal(root.get(Employee_.id), id);
    }

    public static Specification<Employee> hasName(String name) {
        return (root, cq, cb) -> cb.like(cb.lower(root.get(Employee_.name)), name.toLowerCase() + "%");
    }

    public static Specification<Employee> hasPosition(String position) {
        return (root, cq, cb) -> cb.equal(root.get(Employee_.position).get(Position_.name), position);
    }

    public static Specification<Employee> hasSalary(int salary) {
        return (root, cq, cb) -> cb.between(root.get(Employee_.salary), (int) (salary * 0.95), (int) (salary * 1.05));
    }

    public static Specification<Employee> hasJoinDate(LocalDateTime joinDate) {
        LocalDateTime joinDay = LocalDateTime.of(joinDate.toLocalDate(), LocalTime.of(0, 0));
        return (root, cq, cb) -> cb.between(root.get(Employee_.joinDate), joinDay, joinDay.plusMinutes(1439));
    }

    public static Specification<Employee> hasCompany(String companyName) {
        return (root, cq, cb) -> cb.like(cb.lower(root.get(Employee_.company).get(Company_.name)), companyName.toLowerCase() + "%");
    }
}

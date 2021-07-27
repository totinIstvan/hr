package hu.webuni.hr.totinistvan.repository;

import hu.webuni.hr.totinistvan.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> getAllBySalaryAfter(int limit);

    List<Employee> getEmployeesByPosition(String position);

    List<Employee> getEmployeesByNameStartingWithIgnoreCase(String s);

    List<Employee> getEmployeesByJoinDateBetween(LocalDateTime start, LocalDateTime end);
}

package hu.webuni.hr.totinistvan.repository;

import hu.webuni.hr.totinistvan.model.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Page<Employee> getAllBySalaryAfter(int limit, Pageable pageable);

    List<Employee> getEmployeesByPositionName(String position);

    List<Employee> getEmployeesByNameStartingWithIgnoreCase(String s);

    List<Employee> getEmployeesByJoinDateBetween(LocalDateTime start, LocalDateTime end);
}

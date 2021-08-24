package hu.webuni.hr.totinistvan.repository;

import hu.webuni.hr.totinistvan.model.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Page<Employee> getAllBySalaryAfter(int limit, Pageable pageable);

    List<Employee> getEmployeesByPositionName(String position);

    List<Employee> getEmployeesByNameStartingWithIgnoreCase(String s);

    List<Employee> getEmployeesByJoinDateBetween(LocalDateTime start, LocalDateTime end);

    @Modifying
    @Query("UPDATE Employee e "
            + "SET e.salary = :minSalary "
            + "WHERE e.id IN "
            + "(SELECT e2.id "
            + "FROM Employee e2 "
            + "WHERE e2.position.name = :positionName "
            + "AND e2.company.id = :companyId "
            + "AND e2.salary < :minSalary"
            + ")")
    void updateSalaries(String positionName, int minSalary, long companyId);
}

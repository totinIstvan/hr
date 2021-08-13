package hu.webuni.hr.totinistvan.repository;

import hu.webuni.hr.totinistvan.model.AvgSalaryForPosition;
import hu.webuni.hr.totinistvan.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query(value = "SELECT * FROM company c JOIN employee e ON c.id=e.company_id WHERE e.salary > :limit", nativeQuery = true)
    List<Company> getCompaniesWithSalariesHigherThanLimit(int limit);

    @Query(value = "SELECT c.id, c.registration_number, c.name, c.address " +
            "FROM (SELECT c.id, c.name, c.registration_number, c.address, count(e.company_id) AS companies " +
            "FROM employee e JOIN company c ON e.company_id=c.id GROUP BY c.id) " +
            "AS c where c.companies > :limit", nativeQuery = true)
    List<Company> getCompaniesWithNumberOfEmployeesMoreThanLimit(int limit);

    @Query(value = "SELECT e.position AS position, AVG(e.salary) AS avgSalary " +
            "FROM company c INNER JOIN employee e ON c.id=e.company_id " +
            "WHERE c.id= :companyId " +
            "GROUP BY position " +
            "ORDER BY avgSalary DESC",
            nativeQuery = true)
    List<AvgSalaryForPosition> averageSalaryOfEmployeesOfSpecifiedCompanyByPositions(Long companyId);
}

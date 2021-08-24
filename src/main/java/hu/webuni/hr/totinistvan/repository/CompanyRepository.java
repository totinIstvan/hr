package hu.webuni.hr.totinistvan.repository;

import hu.webuni.hr.totinistvan.model.AvgSalaryForPosition;
import hu.webuni.hr.totinistvan.model.entity.Company;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @EntityGraph("Company.full")
    @Query(value = "SELECT DISTINCT c FROM Company c JOIN c.employees e WHERE e.salary > :limit")
    List<Company> getCompaniesWithSalariesHigherThanLimitWithEmployees(int limit);

    @Query(value = "SELECT DISTINCT c FROM Company c JOIN c.employees e WHERE e.salary > :limit")
    List<Company> getCompaniesWithSalariesHigherThanLimit(int limit);

    @EntityGraph("Company.full")
    @Query(value = "SELECT c FROM Company c WHERE SIZE(c.employees) > :limit")
    List<Company> getCompaniesWithNumberOfEmployeesMoreThanLimitWithEmployees(int limit);

    @Query(value = "SELECT c FROM Company c WHERE SIZE(c.employees) > :limit")
    List<Company> getCompaniesWithNumberOfEmployeesMoreThanLimit(int limit);

    @Query(value = "SELECT e.position.name AS position, AVG(e.salary) AS avgSalary " +
            "FROM Company c INNER JOIN c.employees e " +
            "WHERE c.id = :companyId GROUP BY position ORDER BY avgSalary DESC")
    List<AvgSalaryForPosition> averageSalaryOfEmployeesOfSpecifiedCompanyByPositions(Long companyId);

    @EntityGraph("Company.full")
    @Query("SELECT c FROM Company c")
    List<Company> findAllWithEmployees();

    @EntityGraph("Company.full")
    @Query("SELECT c FROM Company c WHERE c.id = :companyId")
    Optional<Company> findByIdWithEmployees(long companyId);
}

package hu.webuni.hr.totinistvan.service;

import hu.webuni.hr.totinistvan.mapper.PositionByCompanyMapper;
import hu.webuni.hr.totinistvan.model.AvgSalaryForPosition;
import hu.webuni.hr.totinistvan.model.dto.PositionByCompanyDto;
import hu.webuni.hr.totinistvan.model.entity.Company;
import hu.webuni.hr.totinistvan.model.entity.Employee;
import hu.webuni.hr.totinistvan.model.entity.PositionByCompany;
import hu.webuni.hr.totinistvan.repository.CompanyRepository;
import hu.webuni.hr.totinistvan.repository.EmployeeRepository;
import hu.webuni.hr.totinistvan.repository.PositionByCompanyRepository;
import hu.webuni.hr.totinistvan.repository.PositionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    private final EmployeeRepository employeeRepository;

    private final PositionRepository positionRepository;

    public CompanyService(CompanyRepository companyRepository,
                          EmployeeRepository employeeRepository,
                          PositionRepository positionRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public List<Company> findAllWithEmployees() {
        return companyRepository.findAllWithEmployees();
    }

    public Optional<Company> findById(long id) {
        return companyRepository.findById(id);
    }

    public Optional<Company> findByIdWithEmployees(long companyId) {
        return companyRepository.findByIdWithEmployees(companyId);
    }

    @Transactional
    public Company save(Company company) {
        return companyRepository.save(company);
    }

    @Transactional
    public Company update(Company company) {
        if (companyRepository.existsById(company.getId())) {
            return companyRepository.save(company);
        }
        throw new NoSuchElementException();
    }

    @Transactional
    public void deleteById(long id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Transactional
    public Employee addEmployee(long companyId, Employee employee) throws IllegalArgumentException {
        if (companyRepository.existsById(companyId)) {
            Company company = companyRepository.findByIdWithEmployees(companyId).get();
            if (!employeeRepository.existsById(employee.getId())) {
                employee.setCompany(company);
                EmployeeService.setPositionForEmployee(employee, positionRepository);
                company.addEmployee(employee);
                return employeeRepository.save(employee);
            } else {
                throw new IllegalArgumentException("Employee with id " + employee.getId() + " already exists!");
            }
        }
        throw new NoSuchElementException();
    }

    @Transactional
    public void removeEmployee(long companyId, long employeeId) {
        if (companyRepository.existsById(companyId) && employeeRepository.existsById(employeeId)) {
            Company company = companyRepository.findById(companyId).get();
            Employee employee = employeeRepository.findById(employeeId).get();
            company.getEmployees().removeIf(e -> e.getId() == employeeId);
            if (employee.getCompany().getId() == companyId) {
                employee.setCompany(null);
            }
        } else {
            throw new NoSuchElementException();
        }
    }

    @Transactional
    public Company updateEmployees(long companyId, List<Employee> newEmployees) {
        if (companyRepository.existsById(companyId)) {
            Company company = companyRepository.findByIdWithEmployees(companyId).get();
            company.getEmployees().forEach(e -> e.setCompany(null));
            company.getEmployees().clear();

            newEmployees.forEach(e -> {
                EmployeeService.setPositionForEmployee(e, positionRepository);
                company.addEmployee(employeeRepository.save(e));
                    });
            return company;
        } else {
            throw new NoSuchElementException();
        }
    }

    public List<Company> getCompaniesWithSalariesHigherThanLimit(int limit) {
        return companyRepository.getCompaniesWithSalariesHigherThanLimit(limit);
    }

    public List<Company> getCompaniesWithNumberOfEmployeesMoreThanLimit(int limit) {
        return companyRepository.getCompaniesWithNumberOfEmployeesMoreThanLimit(limit);
    }

    public List<AvgSalaryForPosition> averageSalaryOfEmployeesOfSpecifiedCompanyByPosition(long companyId) {
        return companyRepository.averageSalaryOfEmployeesOfSpecifiedCompanyByPositions(companyId);
    }

    public List<Company> getCompaniesWithSalariesHigherThanLimitWithEmployees(int limit) {
        return companyRepository.getCompaniesWithSalariesHigherThanLimitWithEmployees(limit);
    }

    public List<Company> getCompaniesWithNumberOfEmployeesMoreThanLimitWithEmployees(int limit) {
        return companyRepository.getCompaniesWithNumberOfEmployeesMoreThanLimitWithEmployees(limit);
    }
}

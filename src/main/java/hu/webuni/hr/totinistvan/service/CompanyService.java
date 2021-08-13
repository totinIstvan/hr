package hu.webuni.hr.totinistvan.service;

import hu.webuni.hr.totinistvan.model.AvgSalaryForPosition;
import hu.webuni.hr.totinistvan.model.entity.Company;
import hu.webuni.hr.totinistvan.model.entity.Employee;
import hu.webuni.hr.totinistvan.repository.CompanyRepository;
import hu.webuni.hr.totinistvan.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Optional<Company> findById(long id) {
        return companyRepository.findById(id);
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
            Company company = companyRepository.findById(companyId).get();
            if (!employeeRepository.existsById(employee.getId())) {
                employee.setCompany(company);
                company.addEmployee(employee);
                return employeeRepository.save(employee);
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
            Company company = companyRepository.findById(companyId).get();
            company.getEmployees().forEach(e -> e.setCompany(null));
            newEmployees.forEach(e -> e.setCompany(company));
            company.setEmployees(newEmployees);
            return companyRepository.save(company);
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
}

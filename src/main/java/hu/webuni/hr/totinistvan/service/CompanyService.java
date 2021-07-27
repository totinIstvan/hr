package hu.webuni.hr.totinistvan.service;

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

//    private Map<Long, Company> companies = new HashMap<>();
//
//    {
//        companies.put(1L, new Company(1, "AllAccess Doe", "HN570012", "4615 First Ave. Pittsburg, PA 15342"));
//        companies.put(2L, new Company(2, "Building Doe", "HZ070934", "1768 Fifth Ave. New York, NY 10342"));
//        companies.put(3L, new Company(3, "Luxury Doe", "NN837910", "1245 Eighth Ave. Philadelphia, PA 15872"));
//
//        Employee e1 = new Employee(1L, "John Doe", "CEO", 1_500_000, LocalDateTime.of(1980, 6, 15, 8, 0, 0));
//        Employee e2 = new Employee(2L, "Jack Doe", "CTO", 1_000_000, LocalDateTime.of(2012, 9, 10, 8, 0, 0));
//        Employee e3 = new Employee(3L, "Jane Doe", "CMO", 1_000_000, LocalDateTime.of(2017, 12, 3, 8, 0, 0));
//
//        Employee e4 = new Employee(1L, "James Doe", "CEO", 1_500_000, LocalDateTime.of(2016, 7, 16, 8, 0, 0));
//        Employee e5 = new Employee(2L, "Jason Doe", "CTO", 1_000_000, LocalDateTime.of(2017, 2, 4, 8, 0, 0));
//        Employee e6 = new Employee(3L, "Jake Doe", "CMO", 1_000_000, LocalDateTime.of(2010, 11, 19, 8, 0, 0));
//
//        Employee e7 = new Employee(1L, "Julia Doe", "CEO", 1_500_000, LocalDateTime.of(2006, 10, 10, 8, 0, 0));
//        Employee e8 = new Employee(2L, "Josh Doe", "CTO", 1_000_000, LocalDateTime.of(2001, 4, 7, 8, 0, 0));
//        Employee e9 = new Employee(3L, "Joe Doe", "CMO", 1_000_000, LocalDateTime.of(2012, 10, 25, 8, 0, 0));
//
//        List<Employee> employees1 = new ArrayList<>(Arrays.asList(e1, e2, e3));
//        List<Employee> employees2 = new ArrayList<>(Arrays.asList(e4, e5, e6));
//        List<Employee> employees3 = new ArrayList<>(Arrays.asList(e7, e8, e9));
//
//        companies.get(1L).setEmployees(employees1);
//        companies.get(2L).setEmployees(employees2);
//        companies.get(3L).setEmployees(employees3);
//    }

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
    public List<Employee> updateEmployees(long companyId, List<Employee> newEmployees) {
        if (companyRepository.existsById(companyId)) {
            Company company = companyRepository.findById(companyId).get();
            newEmployees.forEach(e -> e.setCompany(company));
            company.getEmployees().clear();
            company.setEmployees(newEmployees);
            companyRepository.save(company);
            return newEmployees;
        } else {
            throw new NoSuchElementException();
        }
    }
}

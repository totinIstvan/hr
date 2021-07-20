package hu.webuni.hr.totinistvan.service;

import hu.webuni.hr.totinistvan.model.entity.Company;
import hu.webuni.hr.totinistvan.model.entity.Employee;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CompanyService {

    private Map<Long, Company> companies = new HashMap<>();

    {
        companies.put(1L, new Company(1, "AllAccess Doe", "HN570012", "4615 First Ave. Pittsburg, PA 15342"));
        companies.put(2L, new Company(2, "Building Doe", "HZ070934", "1768 Fifth Ave. New York, NY 10342"));
        companies.put(3L, new Company(3, "Luxury Doe", "NN837910", "1245 Eighth Ave. Philadelphia, PA 15872"));

        Employee e1 = new Employee(1L, "John Doe", "CEO", 1_500_000, LocalDateTime.of(1980, 6, 15, 8, 0, 0));
        Employee e2 = new Employee(2L, "Jack Doe", "CTO", 1_000_000, LocalDateTime.of(2012, 9, 10, 8, 0, 0));
        Employee e3 = new Employee(3L, "Jane Doe", "CMO", 1_000_000, LocalDateTime.of(2017, 12, 3, 8, 0, 0));

        Employee e4 = new Employee(1L, "James Doe", "CEO", 1_500_000, LocalDateTime.of(2016, 7, 16, 8, 0, 0));
        Employee e5 = new Employee(2L, "Jason Doe", "CTO", 1_000_000, LocalDateTime.of(2017, 2, 4, 8, 0, 0));
        Employee e6 = new Employee(3L, "Jake Doe", "CMO", 1_000_000, LocalDateTime.of(2010, 11, 19, 8, 0, 0));

        Employee e7 = new Employee(1L, "Julia Doe", "CEO", 1_500_000, LocalDateTime.of(2006, 10, 10, 8, 0, 0));
        Employee e8 = new Employee(2L, "Josh Doe", "CTO", 1_000_000, LocalDateTime.of(2001, 4, 7, 8, 0, 0));
        Employee e9 = new Employee(3L, "Joe Doe", "CMO", 1_000_000, LocalDateTime.of(2012, 10, 25, 8, 0, 0));

        List<Employee> employees1 = new ArrayList<>(Arrays.asList(e1, e2, e3));
        List<Employee> employees2 = new ArrayList<>(Arrays.asList(e4, e5, e6));
        List<Employee> employees3 = new ArrayList<>(Arrays.asList(e7, e8, e9));

        companies.get(1L).setEmployees(employees1);
        companies.get(2L).setEmployees(employees2);
        companies.get(3L).setEmployees(employees3);
    }

    public List<Company> getAll() {
        return new ArrayList<>(companies.values());
    }

    public Company getById(long id) {
        return companies.get(id);
    }

    public Company save(Company company) {
        companies.put(company.getId(), company);
        return company;
    }

    public Company update(long id, Company company) {
        company.setId(id);
        company.setEmployees(companies.get(id).getEmployees());
        companies.put(id, company);
        return company;
    }

    public void delete(long id) {
        companies.remove(id);
    }

    public Employee addEmployee(long companyId, Employee employee) {
        List<Employee> employeeList = companies.get(companyId).getEmployees();
        employeeList.add(employee);
        return employee;
    }

    public void deleteEmployee(long companyId, long employeeId) {
        Company company = companies.get(companyId);
        Employee employee = findById(company, employeeId).get();
        company.getEmployees().remove(employee);
    }

    public Optional<Employee> findById(Company company, long employeeId) {
        return company.getEmployees()
                .stream()
                .filter(e -> e.getId() == employeeId)
                .findFirst();
    }

    public List<Employee> replaceEmployeeList(long companyId, List<Employee> newEmployees) {
        companies.get(companyId).setEmployees(newEmployees);
        return newEmployees;
    }
}

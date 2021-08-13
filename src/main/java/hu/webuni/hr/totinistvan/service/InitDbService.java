package hu.webuni.hr.totinistvan.service;

import hu.webuni.hr.totinistvan.model.entity.Company;
import hu.webuni.hr.totinistvan.model.entity.Employee;
import hu.webuni.hr.totinistvan.repository.CompanyRepository;
import hu.webuni.hr.totinistvan.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class InitDbService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    public void clearDb() {
        companyRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Transactional
    public void insertTestData() {

        Company company1 = new Company("PD763549", "AllAccess Doe", "4615 First Ave. Pittsburg, PA 15342");
        Company company2 = new Company("PD167300", "Building Doe", "1768 Fifth Ave. New York, NY 10342");
        Company company3 = new Company("PH893881", "Luxury Doe", "1245 Eighth Ave. Philadelphia, PA 15872");

        Employee employee1 = new Employee("John Doe", "CEO", 15000, LocalDateTime.of(1980, 6, 15, 8, 0, 0));
        Employee employee2 = new Employee("Jack Doe", "CEO", 15000, LocalDateTime.of(1989, 10, 10, 8, 0, 0));
        Employee employee3 = new Employee("Jason Doe", "CEO", 15000, LocalDateTime.of(1996, 3, 20, 8, 0, 0));
        Employee employee4 = new Employee("Jane Doe", "CMO", 11000, LocalDateTime.of(2000, 11, 5, 8, 0, 0));
        Employee employee5 = new Employee("Jessica Doe", "CMO", 11000, LocalDateTime.of(1990, 7, 25, 8, 0, 0));
        Employee employee6 = new Employee("Jake Doe", "CMO", 11000, LocalDateTime.of(1999, 6, 15, 8, 0, 0));
        Employee employee7 = new Employee("Julia Doe", "CBO", 10000, LocalDateTime.of(2012, 2, 10, 8, 0, 0));
        Employee employee8 = new Employee("Josh Doe", "CFO", 12500, LocalDateTime.of(2018, 7, 1, 8, 0, 0));
        Employee employee9 = new Employee("Joe Doe", "CBO", 13000, LocalDateTime.of(2020, 7, 15, 8, 0, 0));
        Employee employee10 = new Employee("Jacquelyn Doe", "CBO", 13000, LocalDateTime.of(2004, 2, 24, 8, 0, 0));
        Employee employee11 = new Employee("Jamie Doe", "CMO", 13500, LocalDateTime.of(1997, 12, 8, 8, 0, 0));

        companyRepository.save(company1);
        companyRepository.save(company2);
        companyRepository.save(company3);

        company1.addEmployee(employee1);
        company1.addEmployee(employee4);
        company1.addEmployee(employee7);
        company1.addEmployee(employee10);
        company2.addEmployee(employee2);
        company2.addEmployee(employee5);
        company2.addEmployee(employee8);
        company2.addEmployee(employee11);
        company3.addEmployee(employee3);
        company3.addEmployee(employee6);
        company3.addEmployee(employee9);
    }
}

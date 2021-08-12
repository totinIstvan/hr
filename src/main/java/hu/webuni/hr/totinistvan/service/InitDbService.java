package hu.webuni.hr.totinistvan.service;

import hu.webuni.hr.totinistvan.model.entity.Company;
import hu.webuni.hr.totinistvan.model.entity.Employee;
import hu.webuni.hr.totinistvan.repository.CompanyRepository;
import hu.webuni.hr.totinistvan.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void insertTestData() {
        companyRepository.save(new Company("PD763549", "AllAccess Doe", "4615 First Ave. Pittsburg, PA 15342"));
        companyRepository.save(new Company("PD167300", "Building Doe", "1768 Fifth Ave. New York, NY 10342"));
        companyRepository.save(new Company("PH893881", "Luxury Doe", "1245 Eighth Ave. Philadelphia, PA 15872"));

        employeeRepository.save(new Employee("John Doe", "CEO", 15000, LocalDateTime.of(1980, 6, 15, 8, 0, 0)));
        employeeRepository.save(new Employee("Jack Doe", "CEO", 15000, LocalDateTime.of(1989, 10, 10, 8, 0, 0)));
        employeeRepository.save(new Employee("Jason Doe", "CEO", 15000, LocalDateTime.of(1996, 3, 20, 8, 0, 0)));
        employeeRepository.save(new Employee("Jane Doe", "CMO", 11000, LocalDateTime.of(2000, 11, 5, 8, 0, 0)));
        employeeRepository.save(new Employee("Jessica Doe", "CMO", 11000, LocalDateTime.of(1990, 7, 25, 8, 0, 0)));
        employeeRepository.save(new Employee("Jake Doe", "CMO", 11000, LocalDateTime.of(1999, 6, 15, 8, 0, 0)));
        employeeRepository.save(new Employee("Julia Doe", "COO", 10000, LocalDateTime.of(2012, 2, 10, 8, 0, 0)));
        employeeRepository.save(new Employee("Josh Doe", "CFO", 12500, LocalDateTime.of(2018, 7, 1, 8, 0, 0)));
        employeeRepository.save(new Employee("Joe Doe", "CBO", 13000, LocalDateTime.of(2020, 7, 15, 8, 0, 0)));
    }
}

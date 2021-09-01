package hu.webuni.hr.totinistvan.service;

import hu.webuni.hr.totinistvan.model.Qualification;
import hu.webuni.hr.totinistvan.model.entity.*;
import hu.webuni.hr.totinistvan.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class InitDbService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyTypeRepository companyTypeRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private LeaveOfAbsenceRequestRepository leaveOfAbsenceRequestRepository;

    public void clearDb() {
        companyRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Transactional
    public void insertTestData() {

        Company company1 = new Company("PD763549", "AllAccess Doe", "4615 First Ave. Pittsburg, PA 15342");
        Company company2 = new Company("PD167300", "Building Doe","1768 Fifth Ave. New York, NY 10342");
        Company company3 = new Company("PH893881", "Luxury Doe", "1245 Eighth Ave. Philadelphia, PA 15872");

        CompanyType cp1 = new CompanyType("PLC");
        CompanyType cp2 = new CompanyType("LTD");
        CompanyType cp3 = new CompanyType("LP");
        CompanyType cp4 = new CompanyType("LLC");

        companyTypeRepository.save(cp1);
        companyTypeRepository.save(cp2);
        companyTypeRepository.save(cp3);
        companyTypeRepository.save(cp4);

        company1.setCompanyType(cp1);
        company2.setCompanyType(cp2);
        company3.setCompanyType(cp3);

        Position p1 = new Position("CEO", Qualification.UNIVERSITY);
        Position p2 = new Position("CMO", Qualification.UNIVERSITY);
        Position p3 = new Position("CBO", Qualification.HIGH_SCHOOL);
        Position p4 = new Position("CTO", Qualification.PHD);
        Position p5 = new Position("Developer", Qualification.UNIVERSITY);
        Position p6 = new Position("Tester", Qualification.COLLEGE);

        positionRepository.save(p1);
        positionRepository.save(p2);
        positionRepository.save(p3);
        positionRepository.save(p4);
        positionRepository.save(p5);
        positionRepository.save(p6);

        Employee employee1 = new Employee("John Doe",15000, LocalDateTime.of(1980, 6, 15, 8, 0, 0));
        Employee employee2 = new Employee("Jack Doe",15000, LocalDateTime.of(1996, 3, 20, 17, 0, 0));
        Employee employee3 = new Employee("Jason Doe",15000, LocalDateTime.of(1996, 3, 20, 8, 0, 0));
        Employee employee4 = new Employee("Jane Doe",13000, LocalDateTime.of(2000, 11, 5, 8, 0, 0));
        Employee employee5 = new Employee("Jessica Doe", 12500, LocalDateTime.of(2020, 7, 15, 8, 0, 0));
        Employee employee6 = new Employee("Jake Doe", 11000, LocalDateTime.of(1999, 6, 15, 8, 0, 0));
        Employee employee7 = new Employee("Julia Doe", 12500, LocalDateTime.of(2012, 2, 10, 8, 0, 0));
        Employee employee8 = new Employee("Josh Doe", 12500, LocalDateTime.of(2018, 7, 1, 8, 0, 0));
        Employee employee9 = new Employee("Joe Doe", 13000, LocalDateTime.of(2020, 7, 15, 11, 0, 0));
        Employee employee10 = new Employee("Jacquelyn Doe", 13000, LocalDateTime.of(2004, 2, 24, 8, 0, 0));
        Employee employee11 = new Employee("Jamie Doe", 13500, LocalDateTime.of(1997, 12, 8, 8, 0, 0));

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);
        employeeRepository.save(employee4);
        employeeRepository.save(employee5);
        employeeRepository.save(employee6);
        employeeRepository.save(employee7);
        employeeRepository.save(employee8);
        employeeRepository.save(employee9);
        employeeRepository.save(employee10);
        employeeRepository.save(employee11);

        employee1.setPosition(p1);
        employee2.setPosition(p1);
        employee3.setPosition(p1);
        employee4.setPosition(p3);
        employee5.setPosition(p6);
        employee6.setPosition(p5);
        employee7.setPosition(p6);
        employee8.setPosition(p4);
        employee9.setPosition(p6);
        employee10.setPosition(p5);
        employee11.setPosition(p3);

        companyRepository.save(company1);
        companyRepository.save(company2);
        companyRepository.save(company3);

        company1.addEmployee(employee1);
        company1.addEmployee(employee4);
        company1.addEmployee(employee5);
        company1.addEmployee(employee9);
        company2.addEmployee(employee2);
        company2.addEmployee(employee6);
        company2.addEmployee(employee7);
        company2.addEmployee(employee8);
        company3.addEmployee(employee3);
        company3.addEmployee(employee10);
        company3.addEmployee(employee11);

        LeaveOfAbsenceRequest leaveOfAbsenceRequest = new LeaveOfAbsenceRequest();
        leaveOfAbsenceRequest.setApplicant(employee4);
        leaveOfAbsenceRequest.setStartDate(LocalDate.of(2021, 10, 8));
        leaveOfAbsenceRequest.setEndDate(LocalDate.of(2021, 10, 15));
        leaveOfAbsenceRequest.setApplicationDate(LocalDateTime.of(2021, 9, 25, 8, 0, 0));
        leaveOfAbsenceRequestRepository.save(leaveOfAbsenceRequest);
    }
}

package hu.webuni.hr.totinistvan.controller;

import hu.webuni.hr.totinistvan.model.dto.EmployeeDto;
import hu.webuni.hr.totinistvan.model.entity.Company;
import hu.webuni.hr.totinistvan.model.entity.CompanyType;
import hu.webuni.hr.totinistvan.model.entity.Employee;
import hu.webuni.hr.totinistvan.repository.CompanyRepository;
import hu.webuni.hr.totinistvan.repository.CompanyTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureWebTestClient(timeout = "36000")
class CompanyControllerIT {

    private static final String BASE_URI = "/api/companies";

    private Company testCompany;

    private EmployeeDto testEmployee1;
    private EmployeeDto testEmployee2;

    @Autowired
    private CompanyTypeRepository companyTypeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void init() {
        CompanyType testCompanyType = companyTypeRepository.save(new CompanyType("TEST_COMPANY_TYPE"));
        this.testCompany = companyRepository.save(new Company("TEST_REG_NUM", "TEST_COMPANY", "TEST_ADDRESS", testCompanyType));

        this.testEmployee1 = new EmployeeDto("Test Employee1", "testPosition", 10000, LocalDateTime.now());
        this.testEmployee2 = new EmployeeDto("Test Employee2", "testPosition", 20000, LocalDateTime.now());
    }

    @Test
    void addEmployee_withValidData_returnsCompanyWithAddedEmployee() {
        long companyId = testCompany.getId();
        addEmployeeToCompany(companyId, testEmployee1)
                .expectStatus()
                .isOk();

        Optional<Company> optionalCompany = companyRepository.findByIdWithEmployees(companyId);
        Company savedCompany = optionalCompany.get();

        assertThat(optionalCompany).isNotEmpty();
        assertThat(testCompany.getName()).isEqualTo(savedCompany.getName());
        assertThat(testCompany.getRegistrationNumber()).isEqualTo(savedCompany.getRegistrationNumber());
        assertThat(testCompany.getAddress()).isEqualTo(savedCompany.getAddress());
        assertThat(testCompany.getCompanyType())
                .usingRecursiveComparison()
                .isEqualTo(savedCompany.getCompanyType());

        Employee savedEmployee = savedCompany.getEmployees().get(savedCompany.getEmployees().size() - 1);

        assertThat(testEmployee1.getName()).isEqualTo(savedEmployee.getName());
        assertThat(testEmployee1.getJoinDate()).isCloseTo(savedEmployee.getJoinDate(), within(1, ChronoUnit.MICROS));
        assertThat(testEmployee1.getSalary()).isEqualTo(savedEmployee.getSalary());
        assertThat(testEmployee1.getPosition())
                .isEqualTo(savedEmployee.getPosition().getName());
    }

    @Test
    void removeEmployee_withValidData_returnsCompanyWithRemainedEmployees() {
        long companyId = testCompany.getId();
        addEmployeeToCompany(companyId, testEmployee1)
                .expectStatus()
                .isOk();
        addEmployeeToCompany(companyId, testEmployee2)
                .expectStatus()
                .isOk();

        List<Employee> employeesBefore = companyRepository.findByIdWithEmployees(companyId).get().getEmployees();

        removeEmployeeFromCompany(companyId, employeesBefore.get(employeesBefore.size() - 1).getId())
                .expectStatus().isOk();

        List<Employee> employeesAfter = companyRepository.findByIdWithEmployees(companyId).get().getEmployees();

        assertThat(employeesBefore.size() - 1).isEqualTo(employeesAfter.size());
        assertThat(employeesAfter)
                .usingRecursiveFieldByFieldElementComparator()
                .usingElementComparatorIgnoringFields("position", "company", "leaveOfAbsenceRequests")
                .containsExactlyElementsOf(employeesBefore.subList(0, employeesAfter.size()));

    }

    @Test
    void replaceAllEmployees_callWithValidData_returnsListOfNewEmployees() {
        long companyId = testCompany.getId();
        addEmployeeToCompany(companyId, testEmployee1)
                .expectStatus()
                .isOk();

        List<Employee> employeesBefore = companyRepository.findByIdWithEmployees(companyId).get().getEmployees();

        List<EmployeeDto> newEmployees = List.of(testEmployee2);

        replaceAllEmployeesOfCompany(companyId, newEmployees)
                .expectStatus()
                .isOk();

        List<Employee> employeesAfter = companyRepository.findByIdWithEmployees(companyId).get().getEmployees();

        assertThat(employeesAfter)
                .usingRecursiveFieldByFieldElementComparator()
                .usingElementComparatorIgnoringFields("id", "position", "company", "manager", "leaveOfAbsenceRequests")
                .isNotEqualTo(employeesBefore);
        assertThat(employeesAfter)
                .usingRecursiveFieldByFieldElementComparator()
                .usingElementComparatorIgnoringFields("id", "position", "company", "manager", "leaveOfAbsenceRequests")
                .isEqualTo(newEmployees);
    }

    private ResponseSpec addEmployeeToCompany(long companyId, EmployeeDto employee) {
        return webTestClient
                .put()
                .uri(BASE_URI + "/" + companyId + "/new_employee")
                .bodyValue(employee)
                .exchange();
    }

    private ResponseSpec removeEmployeeFromCompany(long companyId, long employeeId) {
        return webTestClient
                .delete()
                .uri(BASE_URI + "/" + companyId + "/employee/" + employeeId)
                .exchange();
    }

    private ResponseSpec replaceAllEmployeesOfCompany(long companyId, List<EmployeeDto> employeeList) {
        return webTestClient
                .put()
                .uri(BASE_URI + "/" + companyId + "/employeeList")
                .bodyValue(employeeList)
                .exchange();
    }
}
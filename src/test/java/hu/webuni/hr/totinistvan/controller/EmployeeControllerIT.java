package hu.webuni.hr.totinistvan.controller;

import hu.webuni.hr.totinistvan.mapper.CompanyMapper;
import hu.webuni.hr.totinistvan.model.dto.CompanyDto;
import hu.webuni.hr.totinistvan.model.dto.EmployeeDto;
import hu.webuni.hr.totinistvan.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class EmployeeControllerIT {

    private static final String BASE_URI = "/api/employees";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyMapper companyMapper;

    private EmployeeDto testEmployee1;
    private EmployeeDto testEmployee2;
    private EmployeeDto testEmployee3;

    @BeforeEach
    private void init() {
        CompanyDto testCompany = new CompanyDto();
        testCompany.setId(0L);
        testCompany.setName("Test Company");
        testCompany.setRegistrationNumber("Test Registration Number");
        testCompany.setAddress("Test Address");
        long companyId = companyRepository.save(companyMapper.companyDtoToCompany(testCompany)).getId();
        testCompany.setId(companyId);

        this.testEmployee1 = new EmployeeDto("Test Employee1", "testPosition", 19800, LocalDateTime.now());
        this.testEmployee2 = new EmployeeDto("Test Employee2", "testPosition", 20000, LocalDateTime.now());
        this.testEmployee3 = new EmployeeDto("Test Employee3", "anotherTestPosition", 20000, LocalDateTime.now());

        this.testEmployee1.setCompany(testCompany);
        this.testEmployee2.setCompany(testCompany);
        this.testEmployee3.setCompany(testCompany);

    }

    @Test
    void addNew_addEmployeeWithValidData_returnsCorrectResults() {
        List<EmployeeDto> employeesBefore = getAllEmployees();

        long id = employeesBefore.size() + 1;

        testEmployee1.setId(id);
        createEmployee(this.testEmployee1)
                .expectStatus()
                .isOk();

        List<EmployeeDto> employeesAfter = getAllEmployees();

        assertEquals(employeesAfter.size(), employeesBefore.size() + 1);

        assertThat(employeesAfter.subList(0, employeesBefore.size()))
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(employeesBefore);

        assertThat(employeesAfter.get(employeesAfter.size() - 1))
                .usingRecursiveComparison()
                .isEqualTo(testEmployee1);
    }

    @Test
    void addNew_addEmployeeWithInvalidJoinDate_returnsBadRequestAndDoesNotChangesAnything() {
        List<EmployeeDto> employeesBefore = getAllEmployees();

        this.testEmployee1.setJoinDate(LocalDateTime.of(2034, 2, 4, 8, 0, 0));
        createEmployee(this.testEmployee1)
                .expectStatus()
                .isBadRequest();

        List<EmployeeDto> employeesAfter = getAllEmployees();

        assertThat(employeesAfter)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(employeesBefore);
    }

    @Test
    void update_updateEmployeeWithValidData_returnsUpdatedEmployee() {
        long id = getAllEmployees().size() + 1;
        this.testEmployee1.setId(id);
        createEmployee(this.testEmployee1)
                .expectStatus()
                .isOk();
        List<EmployeeDto> employeesBefore = getAllEmployees();

        this.testEmployee2.setId(id);
        updateEmployee(id, this.testEmployee2)
                .expectStatus()
                .isOk();
        List<EmployeeDto> employeesAfter = getAllEmployees();

        assertThat(employeesBefore.get(employeesBefore.size() - 1))
                .usingRecursiveComparison()
                .isNotEqualTo(employeesAfter.get(employeesBefore.size() - 1));

        assertThat(employeesAfter.get(employeesBefore.size() - 1))
                .usingRecursiveComparison()
                .isEqualTo(this.testEmployee2);
    }

    @Test
    void update_updateEmployeeWithInvalidData_returnsBadRequestAndDoesNotChangesAnything() {
        long id = getAllEmployees().size() + 1;
        this.testEmployee1.setId(id);
        createEmployee(this.testEmployee1)
                .expectStatus()
                .isOk();
        List<EmployeeDto> employeesBefore = getAllEmployees();

        this.testEmployee2.setPosition(null);
        updateEmployee(id, testEmployee2)
                .expectStatus()
                .isBadRequest();
        List<EmployeeDto> employeesAfter = getAllEmployees();

        assertEquals(employeesAfter.size(), employeesBefore.size());
        assertThat(employeesAfter.get(employeesAfter.size() - 1))
                .usingRecursiveComparison()
                .isEqualTo(testEmployee1);
    }

    @Test
    void getEmployeesByExample_callWithValidData_returnsListOfMatchingEmployees() {
        AtomicLong id = new AtomicLong(getAllEmployees().size());
        List<EmployeeDto> employees = List.of(testEmployee1, testEmployee2, testEmployee3);
        employees.forEach(e -> {
            e.setId(id.incrementAndGet());
            createEmployee(e);
        });

        EmployeeDto example = new EmployeeDto("test eMPL", "testPosition", 20200, LocalDateTime.now());
        CompanyDto exampleCompany = new CompanyDto();
        exampleCompany.setName("tESt coMp");
        example.setCompany(exampleCompany);
        List<EmployeeDto> employeesAfter = getAllByExample(example);

        assertThat(employeesAfter)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(testEmployee1, testEmployee2);
    }

    private ResponseSpec createEmployee(EmployeeDto employee) {
        return webTestClient
                .post()
                .uri(BASE_URI)
                .bodyValue(employee)
                .exchange();
    }

    private ResponseSpec updateEmployee(long id, EmployeeDto employee) {
        return webTestClient
                .put()
                .uri(BASE_URI + "/" + id)
                .bodyValue(employee)
                .exchange();
    }

    private List<EmployeeDto> getAllEmployees() {
        List<EmployeeDto> responseList = webTestClient
                .get()
                .uri(BASE_URI)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .returnResult().getResponseBody();
        responseList.sort(Comparator.comparingLong(EmployeeDto::getId));
        return responseList;
    }

    private List<EmployeeDto> getAllByExample(EmployeeDto example) {
        List<EmployeeDto> responseList = webTestClient
                .post()
                .uri(BASE_URI + "/byExample")
                .bodyValue(example)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .returnResult().getResponseBody();
        responseList.sort(Comparator.comparingLong(EmployeeDto::getId));
        return responseList;
    }
}
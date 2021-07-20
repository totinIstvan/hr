package hu.webuni.hr.totinistvan.controller;

import hu.webuni.hr.totinistvan.customExceptions.WrongDataInputException;
import hu.webuni.hr.totinistvan.model.dto.EmployeeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {

    private static final String BASE_URI = "/api/employees";

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int port;

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    EmployeeController employeeController;

    @Test
    void addNew_addEmployeeWithValidData_returnsCorrectResults() {
        long id = getAllEmployees().size() + 1;
        List<EmployeeDto> employeesBefore = getAllEmployees();

        EmployeeDto employee = new EmployeeDto(id, "Jason Doe", "CBO", 1_000_000, LocalDateTime.of(2017, 2, 4, 8, 0, 0));
        createEmployee(employee);

        List<EmployeeDto> employeesAfter = getAllEmployees();

        assertThat(employeesAfter.subList(0, employeesBefore.size()))
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(employeesBefore);

        assertThat(employeesAfter.get(employeesAfter.size() - 1))
                .usingRecursiveComparison()
                .isEqualTo(employee);
    }

    @Test
    void addNew_addEmployeeWithInvalidJoinDate_throwsWrongDataInputException() {
        EmployeeDto employee = new EmployeeDto(4, "Jason Doe", "CBO", 1_000_000, LocalDateTime.of(2034, 2, 4, 8, 0, 0));

        assertThrows(WrongDataInputException.class, () -> employeeController.addNew(employee));
    }

    @Test
    void addNew_addEmployeeWithInvalidJoinDate_throwsAssertionError() {
        EmployeeDto employee = new EmployeeDto(4, "Jason Doe", "CBO", 1_000_000, LocalDateTime.of(2034, 2, 4, 8, 0, 0));

        assertThrows(AssertionError.class, () -> createEmployee(employee));
    }

    @Test
    void update_updateEmployeeWithValidData_returnsUpdated() {
        long id = getAllEmployees().size() + 1;
        EmployeeDto employee = new EmployeeDto(id, "Jason Doe", "CBO", 1_000_000, LocalDateTime.of(2017, 2, 4, 8, 0, 0));
        employeeController.addNew(employee);
        List<EmployeeDto> employeesBefore = getAllEmployees();

        EmployeeDto newEmployee = new EmployeeDto(id, "Julia Doe", "CBO", 1_200_000, LocalDateTime.of(2021, 7,19,8,0,0));
        updateEmployee(id, newEmployee);
        List<EmployeeDto> employeesAfter = getAllEmployees();

        assertThat(employeesBefore.get(employeesBefore.size() - 1))
                .usingRecursiveComparison()
                .isNotEqualTo(employeesAfter.get(employeesBefore.size() - 1));

        assertThat(employeesAfter.get(employeesBefore.size() - 1))
                .usingRecursiveComparison()
                .isEqualTo(newEmployee);
    }

    @Test
    void update_updateEmployeeWithInvalidData_throwsAssertionError() {
        long id = getAllEmployees().size() + 1;
        EmployeeDto employee = new EmployeeDto(id, "Jason Doe", "CBO", 1_000_000, LocalDateTime.of(2017, 2, 4, 8, 0, 0));
        employeeController.addNew(employee);

        EmployeeDto newEmployee = employeeController.update(id, new EmployeeDto(id, "Julia Doe", "", 1_200_000, LocalDateTime.of(2021, 7,19,8,0,0)));
        assertThrows(AssertionError.class, () -> updateEmployee(id, newEmployee));
    }

    private void createEmployee(EmployeeDto employee) {
        webTestClient
                .post()
                .uri(BASE_URI)
                .bodyValue(employee)
                .exchange()
                .expectStatus()
                .isOk();
    }

    private void updateEmployee(long id, EmployeeDto employee) {
        webTestClient
                .put()
                .uri(BASE_URI + "/" + id)
                .bodyValue(employee)
                .exchange()
                .expectStatus()
                .isOk();
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
}
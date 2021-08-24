package hu.webuni.hr.totinistvan.controller;

import hu.webuni.hr.totinistvan.model.dto.EmployeeDto;
import hu.webuni.hr.totinistvan.model.entity.Position;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class EmployeeControllerTest {

    private static final String BASE_URI = "/api/employees";

    @Autowired
    private WebTestClient webTestClient;

    Position position = new Position("CBO");

    @Test
    void addNew_addEmployeeWithValidData_returnsCorrectResults() {
        List<EmployeeDto> employeesBefore = getAllEmployees();

        long id = employeesBefore.size() + 1;

        EmployeeDto employee = new EmployeeDto(id, "Jason Doe", position, 1_000_000, LocalDateTime.of(2017, 2, 4, 8, 0, 0));
        createEmployee(employee)
                .expectStatus()
                .isOk();

        List<EmployeeDto> employeesAfter = getAllEmployees();

        assertEquals(employeesAfter.size(), employeesBefore.size() + 1);

        assertThat(employeesAfter.subList(0, employeesBefore.size()))
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(employeesBefore);

        assertThat(employeesAfter.get(employeesAfter.size() - 1))
                .usingRecursiveComparison()
                .isEqualTo(employee);
    }

    @Test
    void addNew_addEmployeeWithInvalidJoinDate_returnsBadRequest() {
        List<EmployeeDto> employeesBefore = getAllEmployees();

        long id = employeesBefore.size() + 1;

        EmployeeDto employee = new EmployeeDto(id, "Jason Doe", position, 1_000_000, LocalDateTime.of(2034, 2, 4, 8, 0, 0));

        createEmployee(employee)
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
        EmployeeDto employee = new EmployeeDto(id, "Jason Doe", position, 1_000_000, LocalDateTime.of(2017, 2, 4, 8, 0, 0));
        createEmployee(employee);
        List<EmployeeDto> employeesBefore = getAllEmployees();

        EmployeeDto newEmployee = new EmployeeDto(id, "Julia Doe", position, 1_200_000, LocalDateTime.of(2021, 7,19,8,0,0));
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
    void update_updateEmployeeWithInvalidData_returnsBadRequest() {
        long id = getAllEmployees().size() + 1;
        EmployeeDto employee = new EmployeeDto(id, "Jason Doe", position, 1_000_000, LocalDateTime.of(2017, 2, 4, 8, 0, 0));
        EmployeeDto savedEmployee = createEmployee(employee)
                .expectStatus()
                .isOk()
                .expectBody(EmployeeDto.class)
                .returnResult()
                .getResponseBody();

        List<EmployeeDto> employeesBefore = getAllEmployees();

        EmployeeDto newEmployee = new EmployeeDto(id, "Julia Doe", position, 1_200_000, LocalDateTime.of(2021, 7,19,8,0,0));
        newEmployee.setPosition(null);
        updateEmployee(id, newEmployee)
                .expectStatus()
                .isBadRequest();
        List<EmployeeDto> employeesAfter = getAllEmployees();

        assertEquals(employeesAfter.size(), employeesBefore.size());
        assertThat(employeesAfter.get(employeesAfter.size() -1))
                .usingRecursiveComparison()
                .isEqualTo(employee);
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
}
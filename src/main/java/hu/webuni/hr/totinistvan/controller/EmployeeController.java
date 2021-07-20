package hu.webuni.hr.totinistvan.controller;

import hu.webuni.hr.totinistvan.customExceptions.WrongDataInputException;
import hu.webuni.hr.totinistvan.mapper.EmployeeMapper;
import hu.webuni.hr.totinistvan.model.dto.EmployeeDto;
import hu.webuni.hr.totinistvan.model.entity.Employee;
import hu.webuni.hr.totinistvan.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping
    public List<EmployeeDto> getAll() {
        return employeeMapper.employeesToDtos(employeeService.findAll());
    }

    @GetMapping("/{id}")
    public EmployeeDto getById(@PathVariable long id) {
        Employee employee = employeeService.findById(id);
        if (employee != null) {
            return employeeMapper.employeeToDto(employee);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public EmployeeDto addNew(@RequestBody @Valid EmployeeDto employeeDto) {
        checkJoinDate(employeeDto.getJoinDate());
        Employee employee = employeeService.save(employeeMapper.employeeDtoToEmployee(employeeDto));
        return employeeMapper.employeeToDto(employee);
    }

    private void checkJoinDate(LocalDateTime joinDate) {
        if (ChronoUnit.DAYS.between(joinDate, LocalDateTime.now()) <= 0) {
            throw new WrongDataInputException(joinDate);
        }
    }

    @PutMapping("/{id}")
    public EmployeeDto update(@PathVariable long id, @RequestBody @Valid EmployeeDto employeeDto) {
        checkJoinDate(employeeDto.getJoinDate());
        Employee employee = employeeService.findById(id);
        if (employee != null) {
            employee = employeeService.update(id, employeeMapper.employeeDtoToEmployee(employeeDto));
            return employeeMapper.employeeToDto(employee);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        employeeService.delete(id);
    }

    @GetMapping("/limit")
    public List<EmployeeDto> getWithHigherSalary(@RequestParam int limit) {
        List<Employee> employees = employeeService.getWithHigherSalary(limit);
        return employeeMapper.employeesToDtos(employees);
    }
}

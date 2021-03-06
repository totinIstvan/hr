package hu.webuni.hr.totinistvan.controller;

import hu.webuni.hr.totinistvan.mapper.EmployeeMapper;
import hu.webuni.hr.totinistvan.model.dto.EmployeeDto;
import hu.webuni.hr.totinistvan.model.entity.Employee;
import hu.webuni.hr.totinistvan.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final EmployeeMapper employeeMapper;

    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping
    public List<EmployeeDto> getAll() {
        return employeeMapper.employeesToDtos(employeeService.findAll());
    }

    @GetMapping("/{id}")
    public EmployeeDto getById(@PathVariable long id) {
        Employee employee = employeeService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with id " + id + " not found"));
        return employeeMapper.employeeToDto(employee);
    }

    @PostMapping
    public EmployeeDto addNew(@RequestBody @Valid EmployeeDto employeeDto) {
        return employeeMapper.employeeToDto(employeeService.save(employeeMapper.employeeDtoToEmployee(employeeDto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> update(@PathVariable long id, @RequestBody @Valid EmployeeDto employeeDto) {
        Employee employee = employeeMapper.employeeDtoToEmployee(employeeDto);
        employee.setId(id);
        try {
            EmployeeDto savedEmployeeDto = employeeMapper.employeeToDto(employeeService.update(employee));
            return ResponseEntity.ok(savedEmployeeDto);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with id " + id + " not found");
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        try {
            employeeService.deleteById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with id " + id + " not found");
        }
    }

    @GetMapping("/limit")
    public List<EmployeeDto> getWithHigherSalaryThanLimit(@RequestParam int limit, Pageable pageable) {
        Page<Employee> employees = employeeService.getWithHigherSalaryThanLimit(limit, pageable);
        System.out.println(employees.getTotalElements());
        System.out.println(employees.getTotalPages());
        System.out.println(employees.isFirst());
        System.out.println(employees.isLast());
        return employeeMapper.employeesToDtos(employees.getContent());
    }

    @GetMapping("/position/{position}")
    public List<EmployeeDto> getEmployeesByPosition(@PathVariable String position) {
        return employeeMapper.employeesToDtos(employeeService.getEmployeesByPositionName(position));
    }

    @GetMapping("/name_starts/{s}")
    public List<EmployeeDto> getEmployeesByNameStartingWith(@PathVariable String s) {
        return employeeMapper.employeesToDtos(employeeService.getEmployeesByNameStartingWith(s));
    }

    @GetMapping("/start_date/{s}/endDate/{e}")
    public List<EmployeeDto> getEmployeesByJoinDateBetween(@PathVariable("s") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                           @PathVariable("e") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return employeeMapper.employeesToDtos(employeeService.getEmployeesByJoinDateBetween(start, end));
    }

    @PostMapping("/byExample")
    public List<EmployeeDto> getEmployeesByExample(@RequestBody EmployeeDto employeeDto) {
        return employeeMapper
                .employeesToDtos(employeeService
                        .findEmployeesByExample(employeeMapper.employeeDtoToEmployee(employeeDto)));
    }
}

package hu.webuni.hr.totinistvan.controller;

import hu.webuni.hr.totinistvan.model.dto.EmployeeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private Map<Long, EmployeeDto> employees = new HashMap<>();

    {
        employees.put(1L, new EmployeeDto(1, "John Doe", "CEO", 1_500_000, LocalDateTime.of(1980, 6, 15, 8, 0,0)));
        employees.put(2L, new EmployeeDto(2, "Jack Doe", "CTO", 1_000_000, LocalDateTime.of(2012, 9, 10, 8, 0,0)));
        employees.put(3L, new EmployeeDto(3, "Jane Doe", "CMO", 1_000_000, LocalDateTime.of(2017, 12, 3, 8, 0,0)));
    }

    @GetMapping
    public List<EmployeeDto> getAll() {
        return new ArrayList<>(employees.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getById(@PathVariable long id) {
        EmployeeDto employeeDto = employees.get(id);
        if (employeeDto != null) {
            return ResponseEntity.ok(employeeDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public EmployeeDto addNew(@RequestBody EmployeeDto employeeDto) {
        employees.put(employeeDto.getId(), employeeDto);
        return employeeDto;
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> update(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
        if (!employees.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        employeeDto.setId(id);
        employees.put(id, employeeDto);
        return ResponseEntity.ok(employeeDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        employees.remove(id);
    }

    @GetMapping("/limit")
    public List<EmployeeDto> getWithHigherSalary(@RequestParam int limit) {
        return employees.values()
                .stream()
                .filter(employee -> employee.getSalary() > limit)
                .collect(Collectors.toList());
    }
}

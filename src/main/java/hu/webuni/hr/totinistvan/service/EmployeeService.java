package hu.webuni.hr.totinistvan.service;

import hu.webuni.hr.totinistvan.model.entity.Employee;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public abstract class EmployeeService {

    private Map<Long, Employee> employees = new HashMap<>();

    {
        employees.put(1L, new Employee(1, "John Doe", "CEO", 1_500_000, LocalDateTime.of(1980, 6, 15, 8, 0,0)));
        employees.put(2L, new Employee(2, "Jack Doe", "CTO", 1_000_000, LocalDateTime.of(2012, 9, 10, 8, 0,0)));
        employees.put(3L, new Employee(3, "Jane Doe", "CMO", 1_000_000, LocalDateTime.of(2017, 12, 3, 8, 0,0)));
    }

    public abstract int getPayRaisePercent(Employee employee);

    public List<Employee> findAll() {
        return new ArrayList<>(employees.values());
    }


    public Employee findById(long id) {
        return employees.get(id);
    }

    public Employee save(Employee employee) {
        employees.put(employee.getId(), employee);
        return employee;
    }

    public Employee update(long id, Employee employee) {
        employees.put(id, employee);
        return employee;
    }

    public void delete(long id) {
        employees.remove(id);
    }

    public List<Employee> getWithHigherSalary(int limit) {
        return employees.values()
                .stream()
                .filter(employee -> employee.getSalary() > limit)
                .collect(Collectors.toList());
    }
}

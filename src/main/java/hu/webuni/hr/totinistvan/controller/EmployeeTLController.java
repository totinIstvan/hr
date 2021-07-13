package hu.webuni.hr.totinistvan.controller;

import hu.webuni.hr.totinistvan.model.entity.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class EmployeeTLController {

    private List<Employee> employees = new ArrayList<>();

    {
        employees.add(new Employee(1L, "John Doe", "CEO", 1_500_000, LocalDateTime.of(1980, 6, 15, 8, 0, 0)));
        employees.add(new Employee(2L, "Jack Doe", "CTO", 1_000_000, LocalDateTime.of(2012, 9, 10, 8, 0, 0)));
        employees.add(new Employee(3L, "Jane Doe", "CMO", 1_000_000, LocalDateTime.of(2017, 12, 3, 8, 0, 0)));
    }

    @GetMapping
    public String listEmployees(Map<String, Object> model) {
        model.put("employees", employees);
        model.put("newEmployee", new Employee());
        return "index";
    }

    @PostMapping("/employee")
    public String createEmployee(Employee employee) {
        employees.add(employee);
        return "redirect:/";
    }

    @GetMapping("/employee/{id}")
    public String returnEmployee(@PathVariable long id, Map<String, Object> model) {
        model.put("employees", employees);
        model.put("employee", employees.get((int) (id - 1)));
        return "update";
    }

    @PostMapping("/employee/{id}")
    public String updateEmployee(@PathVariable long id, Employee employee) {
        employees.set((int) (id - 1), employee);
        return "redirect:/";
    }

    @GetMapping("/employee/delete/{id}")
    public String deleteEmployee(@PathVariable long id) {
        employees.removeIf(e -> e.getId() == id);
        return "redirect:/";
    }
}

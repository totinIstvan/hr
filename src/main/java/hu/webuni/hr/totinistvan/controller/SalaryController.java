package hu.webuni.hr.totinistvan.controller;

import hu.webuni.hr.totinistvan.model.entity.Employee;
import hu.webuni.hr.totinistvan.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/salary")
public class SalaryController {

    private final EmployeeService employeeService;

    public SalaryController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/pay_rise_percent")
    public int payRisePercent(@RequestBody Employee employee) {
        return employeeService.getPayRaisePercent(employee);
    }
}

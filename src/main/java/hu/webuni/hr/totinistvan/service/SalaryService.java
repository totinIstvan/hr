package hu.webuni.hr.totinistvan.service;

import hu.webuni.hr.totinistvan.model.Employee;
import org.springframework.stereotype.Service;

@Service
public class SalaryService {

    private EmployeeService employeeService;

    public SalaryService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public int payRise(Employee employee) {
        int basicSalary = employee.getSalary();
        int payRisePercent = employeeService.getPayRaisePercent(employee);
        employee.setSalary((int) Math.round(basicSalary + basicSalary / 100.0 * payRisePercent));
        return employee.getSalary();
    }

}

package hu.webuni.hr.totinistvan.service;

import hu.webuni.hr.totinistvan.model.entity.Employee;
import hu.webuni.hr.totinistvan.repository.PositionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalaryService {

    private final EmployeeService employeeService;
    private final PositionRepository positionRepository;

    public SalaryService(EmployeeService employeeService, PositionRepository positionRepository) {
        this.employeeService = employeeService;
        this.positionRepository = positionRepository;
    }

    public int payRise(Employee employee) {
        int basicSalary = employee.getSalary();
        int payRisePercent = employeeService.getPayRaisePercent(employee);
        employee.setSalary((int) Math.round(basicSalary + basicSalary / 100.0 * payRisePercent));
        return employee.getSalary();
    }

    @Transactional
    public void minSalaryPayRaise(String positionName, int minSalary) {
        positionRepository.findByName(positionName)
                .forEach(p -> {
                    p.setMinSalary(minSalary);
                    p.getEmployees().forEach(e -> {
                        if (e.getSalary() < minSalary) {
                            e.setSalary(minSalary);
                        }

                    });
                });
    }
}

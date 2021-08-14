package hu.webuni.hr.totinistvan.service;

import hu.webuni.hr.totinistvan.model.entity.Employee;
import hu.webuni.hr.totinistvan.model.entity.PositionByCompany;
import hu.webuni.hr.totinistvan.repository.EmployeeRepository;
import hu.webuni.hr.totinistvan.repository.PositionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalaryService {

    private final EmployeeService employeeService;

    private final PositionRepository positionRepository;

    private final EmployeeRepository employeeRepository;

    public SalaryService(EmployeeService employeeService,
                         PositionRepository positionRepository,
                         EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.positionRepository = positionRepository;
        this.employeeRepository = employeeRepository;
    }

    public int payRise(Employee employee) {
        int basicSalary = employee.getSalary();
        int payRisePercent = employeeService.getPayRaisePercent(employee);
        employee.setSalary((int) Math.round(basicSalary + basicSalary / 100.0 * payRisePercent));
        return employee.getSalary();
    }

    @Transactional
    public void minSalaryPayRaise(PositionByCompany positionByCompany) {
        String positionName = positionRepository.findById(positionByCompany.getPosition().getId()).get().getName();
        int minSalary = positionByCompany.getMinSalary();
        long companyId = positionByCompany.getCompany().getId();
        System.out.println(positionName);
        System.out.println(minSalary);
        System.out.println(companyId);
        employeeRepository.updateSalaries(positionName, minSalary, companyId);
    }
}

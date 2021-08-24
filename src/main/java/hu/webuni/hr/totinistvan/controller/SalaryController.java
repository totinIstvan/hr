package hu.webuni.hr.totinistvan.controller;

import hu.webuni.hr.totinistvan.mapper.EmployeeMapper;
import hu.webuni.hr.totinistvan.mapper.PositionByCompanyMapper;
import hu.webuni.hr.totinistvan.model.dto.EmployeeDto;
import hu.webuni.hr.totinistvan.model.dto.PositionByCompanyDto;
import hu.webuni.hr.totinistvan.model.entity.Employee;
import hu.webuni.hr.totinistvan.model.entity.PositionByCompany;
import hu.webuni.hr.totinistvan.repository.PositionByCompanyRepository;
import hu.webuni.hr.totinistvan.service.EmployeeService;
import hu.webuni.hr.totinistvan.service.SalaryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {

    private final SalaryService salaryService;

    private final EmployeeService employeeService;

    private final EmployeeMapper employeeMapper;

    private final PositionByCompanyRepository positionByCompanyRepository;

    private final PositionByCompanyMapper positionByCompanyMapper;

    public SalaryController(SalaryService salaryService,
                            EmployeeService employeeService,
                            EmployeeMapper employeeMapper,
                            PositionByCompanyRepository positionByCompanyRepository,
                            PositionByCompanyMapper positionByCompanyMapper) {
        this.salaryService = salaryService;
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
        this.positionByCompanyRepository = positionByCompanyRepository;
        this.positionByCompanyMapper = positionByCompanyMapper;
    }

    @PostMapping("/payraisePercent")
    public int getPayRaisePercent(@RequestBody EmployeeDto employeeDto) {
        Employee employee = employeeMapper.employeeDtoToEmployee(employeeDto);
        return employeeService.getPayRaisePercent(employee);
    }

    @PutMapping("/minSalaryPayRaise")
    public void minSalaryPayRaiseByPosition(@RequestBody PositionByCompanyDto positionByCompanyDto) {
        PositionByCompany positionByCompany = positionByCompanyRepository.save(positionByCompanyMapper.dtoToPositionByCompany(positionByCompanyDto));
        salaryService.minSalaryPayRaise(positionByCompany);
    }
}

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    PositionByCompanyRepository positionByCompanyRepository;

    @Autowired
    private PositionByCompanyMapper positionByCompanyMapper;

    @PostMapping("/payraisePercent")
    public int getPayRisePercent(@RequestBody EmployeeDto employeeDto) {
        Employee employee = employeeMapper.employeeDtoToEmployee(employeeDto);
        return employeeService.getPayRaisePercent(employee);
    }

    @PutMapping("/minSalaryPayRaise")
    public void minSalaryPayraiseByPosition(@RequestBody PositionByCompanyDto positionByCompanyDto) {
        PositionByCompany positionByCompany = positionByCompanyRepository.save(positionByCompanyMapper.dtoToPositionByCompany(positionByCompanyDto));
        salaryService.minSalaryPayRaise(positionByCompany);
    }
}

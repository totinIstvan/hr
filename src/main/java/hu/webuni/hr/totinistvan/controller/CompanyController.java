package hu.webuni.hr.totinistvan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.totinistvan.mapper.CompanyMapper;
import hu.webuni.hr.totinistvan.mapper.EmployeeMapper;
import hu.webuni.hr.totinistvan.model.dto.CompanyDto;
import hu.webuni.hr.totinistvan.model.dto.EmployeeDto;
import hu.webuni.hr.totinistvan.model.dto.Views;
import hu.webuni.hr.totinistvan.model.entity.Company;
import hu.webuni.hr.totinistvan.model.entity.Employee;
import hu.webuni.hr.totinistvan.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping
    @JsonView(Views.BaseData.class)
    public List<CompanyDto> getCompaniesWithoutEmployees() {
        return companyMapper.companiesToDtos(companyService.getAll());
    }

    @GetMapping(params = "full=true")
    public List<CompanyDto> getAll() {
        return companyMapper.companiesToDtos(companyService.getAll());
    }

    @GetMapping("/{id}")
    @JsonView(Views.BaseData.class)
    public CompanyDto getCompanyWithoutEmployeesById(@PathVariable long id) {
        Company company = companyService.getById(id);

        if (company != null) {
            return companyMapper.companyToDto(company);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/{id}", params = "full=true")
    public CompanyDto getById(@PathVariable long id) {
        Company company = companyService.getById(id);

        if (company != null) {
            return companyMapper.companyToDto(company);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public CompanyDto addNew(@RequestBody CompanyDto companyDto) {
        Company company = companyService.save(companyMapper.companyDtoToCompany(companyDto));
        return companyMapper.companyToDto(company);
    }

    @PutMapping("/{id}")
    public CompanyDto update(@PathVariable long id, @RequestBody CompanyDto companyDto) {
        Company company = companyService.getById(id);

        if (company != null) {
            company = companyService.update(id, companyMapper.companyDtoToCompany(companyDto));
            return companyMapper.companyToDto(company);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        Company company = companyService.getById(id);

        if (company == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        companyService.delete(id);
    }

    @PutMapping("/{company_id}/new_employee")
    public EmployeeDto addEmployee(@PathVariable("company_id") long companyId, @RequestBody EmployeeDto employeeDto) {
        Company company = companyService.getById(companyId);

        if (company != null) {
            Employee employee = companyService.addEmployee(companyId, employeeMapper.employeeDtoToEmployee(employeeDto));
            return employeeMapper.employeeToDto(employee);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{company_id}/employee/{employee_id}")
    public void deleteEmployee(@PathVariable("company_id") long companyId, @PathVariable("employee_id") long employeeId) {
        Company company = companyService.getById(companyId);
        Optional<Employee> employee = Optional.empty();

        if (company != null) {
            employee = companyService.findById(company, employeeId);
        }

        if (employee.isPresent()) {
            companyService.deleteEmployee(companyId, employeeId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{company_id}/employeeList")
    public List<EmployeeDto> replaceEmployeeList(@PathVariable("company_id") long companyId, @RequestBody List<EmployeeDto> newEmployees) {
        Company company = companyService.getById(companyId);

        if (company != null) {
            List<Employee> employees = companyService.replaceEmployeeList(companyId, employeeMapper.employeeDtosToEmployees(newEmployees));
            return employeeMapper.employeesToDtos(employees);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}

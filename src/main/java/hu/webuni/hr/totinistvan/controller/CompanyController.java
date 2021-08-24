package hu.webuni.hr.totinistvan.controller;

import hu.webuni.hr.totinistvan.mapper.CompanyMapper;
import hu.webuni.hr.totinistvan.mapper.EmployeeMapper;
import hu.webuni.hr.totinistvan.model.AvgSalaryForPosition;
import hu.webuni.hr.totinistvan.model.dto.CompanyDto;
import hu.webuni.hr.totinistvan.model.dto.EmployeeDto;
import hu.webuni.hr.totinistvan.model.entity.Company;
import hu.webuni.hr.totinistvan.model.entity.Employee;
import hu.webuni.hr.totinistvan.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    private final CompanyMapper companyMapper;

    private final EmployeeMapper employeeMapper;

    public CompanyController(CompanyService companyService, CompanyMapper companyMapper, EmployeeMapper employeeMapper) {
        this.companyService = companyService;
        this.companyMapper = companyMapper;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping
    public List<CompanyDto> getAll(@RequestParam(required = false) Boolean full) {
        List<Company> companies;
        boolean notFull = (full == null || !full);
        if (notFull) {
            companies = companyService.findAll();
        } else {
            companies = companyService.findAllWithEmployees();
        }
        return mapCompanies(companies, full);
    }

    private List<CompanyDto> mapCompanies(List<Company> companies, Boolean full) {
        if (full == null || !full) {
            return companyMapper.companiesToSummaryDtos(companies);
        } else {
            return companyMapper.companiesToDtos(companies);
        }
    }

    @GetMapping(path = "/{id}")
    public CompanyDto getById(@PathVariable long id, @RequestParam(required = false) Boolean full) {
        Company company;
        boolean notFull = (full == null || !full);
        if (notFull) {
            company = companyService.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company with id " + id + " not found"));
        } else {
            company = companyService.findByIdWithEmployees(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company with id " + id + " not found"));
        }
        return mapCompany(company, full);
    }

    private CompanyDto mapCompany(Company company, Boolean full) {
        if (full == null || !full) {
            return companyMapper.companyToSummaryDto(company);
        }
        return companyMapper.companyToDto(company);
    }

    @PostMapping
    public CompanyDto addNew(@RequestBody CompanyDto companyDto) {
        Company company = companyService.save(companyMapper.companyDtoToCompany(companyDto));
        List<Employee> employees = employeeMapper.employeeDtosToEmployees(companyDto.getEmployees());
        company.setEmployees(employees);
        return companyMapper.companyToDto(company);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> update(@PathVariable long id, @RequestBody CompanyDto companyDto) {
        Company company = companyMapper.companyDtoToCompany(companyDto);
        company.setId(id);
        try {
            CompanyDto savedCompanyDto = companyMapper.companyToDto(companyService.update(company));
            return ResponseEntity.ok(savedCompanyDto);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company with id " + id + " not found");
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        try {
            companyService.deleteById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company with id " + id + " not found");
        }
    }

    @PutMapping("/{company_id}/new_employee")
    public ResponseEntity<EmployeeDto> addEmployee(@PathVariable("company_id") long companyId, @RequestBody EmployeeDto employeeDto) {
        try {
            return ResponseEntity.ok(employeeMapper.employeeToDto(companyService.addEmployee(companyId, employeeMapper.employeeDtoToEmployee(employeeDto))));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company with id " + companyId + " not found");
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @DeleteMapping("/{company_id}/employee/{employee_id}")
    public void removeEmployee(@PathVariable("company_id") long companyId, @PathVariable("employee_id") long employeeId) {
        try {
            companyService.removeEmployee(companyId, employeeId);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company with id " + companyId + " not found, or person with id " + employeeId + "not an employee of requested company");
        }
    }

    @PutMapping("/{company_id}/employeeList")
    public CompanyDto replaceAllEmployees(@PathVariable("company_id") long companyId, @RequestBody List<EmployeeDto> newEmployees) {
        List<Employee> employees = employeeMapper.employeeDtosToEmployees(newEmployees);
        try {
            return companyMapper.companyToDto(companyService.updateEmployees(companyId, employees));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company with id " + companyId + " not found");
        }
    }

    @GetMapping("/withHigherSalaryThan")
    public List<CompanyDto> getCompaniesWithSalariesHigherThanLimit(@RequestParam int limit, @RequestParam(required = false) Boolean full) {
        List<Company> companies;
        boolean notFull = (full == null || !full);
        if (notFull) {
            companies = companyService.getCompaniesWithSalariesHigherThanLimit(limit);
        } else {
            companies = companyService.getCompaniesWithSalariesHigherThanLimitWithEmployees(limit);
        }
        return mapCompanies(companies, full);
    }

    @GetMapping("/moreEmployeesThan")
    public List<CompanyDto> getCompaniesWithNumberOfEmployeesMoreThanLimit(@RequestParam int limit, @RequestParam(required = false) Boolean full) {
        List<Company> companies;
        boolean notFull = (full == null || !full);
        if (notFull) {
            companies = companyService.getCompaniesWithNumberOfEmployeesMoreThanLimit(limit);
        } else {
            companies = companyService.getCompaniesWithNumberOfEmployeesMoreThanLimitWithEmployees(limit);
        }
        return mapCompanies(companies, full);
    }

    @GetMapping("/averageSalaries/{companyId}")
    public List<AvgSalaryForPosition> averageSalaryOfEmployeesOfSpecifiedCompanyByPosition(@PathVariable long companyId) {
        return companyService.averageSalaryOfEmployeesOfSpecifiedCompanyByPosition(companyId);
    }
}

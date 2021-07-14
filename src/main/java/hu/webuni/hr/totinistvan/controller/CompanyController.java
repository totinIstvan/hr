package hu.webuni.hr.totinistvan.controller;

import hu.webuni.hr.totinistvan.model.dto.CompanyDto;
import hu.webuni.hr.totinistvan.model.dto.EmployeeDto;
import hu.webuni.hr.totinistvan.model.entity.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private Map<Long, CompanyDto> companies = new HashMap<>();

    {
        companies.put(1L, new CompanyDto(1, "AllAccess Doe", "4615 First Ave. Pittsburg, PA 15342"));
        companies.put(2L, new CompanyDto(2, "Building Doe", "1768 Fifth Ave. New York, NY 10342"));
        companies.put(3L, new CompanyDto(3, "Luxury Doe", "1245 Eighth Ave. Philadelphia, PA 15872"));

        EmployeeDto e1 = new EmployeeDto(1L, "John Doe", "CEO", 1_500_000, LocalDateTime.of(1980, 6, 15, 8, 0, 0));
        EmployeeDto e2 = new EmployeeDto(2L, "Jack Doe", "CTO", 1_000_000, LocalDateTime.of(2012, 9, 10, 8, 0, 0));
        EmployeeDto e3 = new EmployeeDto(3L, "Jane Doe", "CMO", 1_000_000, LocalDateTime.of(2017, 12, 3, 8, 0, 0));

        EmployeeDto e4 = new EmployeeDto(1L, "James Doe", "CEO", 1_500_000, LocalDateTime.of(2016, 7, 16, 8, 0, 0));
        EmployeeDto e5 = new EmployeeDto(2L, "Jason Doe", "CTO", 1_000_000, LocalDateTime.of(2017, 2, 4, 8, 0, 0));
        EmployeeDto e6 = new EmployeeDto(3L, "Jake Doe", "CMO", 1_000_000, LocalDateTime.of(2010, 11, 19, 8, 0, 0));

        EmployeeDto e7 = new EmployeeDto(1L, "Julia Doe", "CEO", 1_500_000, LocalDateTime.of(2006, 10, 10, 8, 0, 0));
        EmployeeDto e8 = new EmployeeDto(2L, "Josh Doe", "CTO", 1_000_000, LocalDateTime.of(2001, 4, 7, 8, 0, 0));
        EmployeeDto e9 = new EmployeeDto(3L, "Joe Doe", "CMO", 1_000_000, LocalDateTime.of(2012, 10, 25, 8, 0, 0));

        List<EmployeeDto> employees1 = new ArrayList<>(Arrays.asList(e1, e2, e3));
        List<EmployeeDto> employees2 = new ArrayList<>(Arrays.asList(e4, e5, e6));
        List<EmployeeDto> employees3 = new ArrayList<>(Arrays.asList(e7, e8, e9));

        companies.get(1L).setEmployees(employees1);
        companies.get(2L).setEmployees(employees2);
        companies.get(3L).setEmployees(employees3);
    }

    @GetMapping
    public List<CompanyDto> getAll(@RequestParam(required = false) Boolean full) {
        if (!full) {
            List<CompanyDto> res = new ArrayList<>();
            for (CompanyDto company : companies.values()) {
                res.add(withoutEmployees(company));
            }
            return res;
        }
        return new ArrayList<>(companies.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getById(@PathVariable long id, @RequestParam(required = false) Boolean full) {
        CompanyDto companyDto;
        try {
            companyDto = companies.get(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }

        if (!full) {
            companyDto = withoutEmployees(companyDto);
        }
        return ResponseEntity.ok(companyDto);
    }

    @PostMapping
    public CompanyDto addNew(@RequestBody CompanyDto companyDto) {
        companyDto.setRegistrationNumber(UUID.randomUUID().toString().substring(0, 8));
        companies.put(companyDto.getId(), companyDto);
        return companyDto;
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> update(@PathVariable long id, @RequestBody CompanyDto companyDto) {
        if (!companies.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        companyDto.setId(id);
        // Ez kell?
//        companyDto.setEmployees(companies.get(id).getEmployees());
        companies.put(id, companyDto);
        return ResponseEntity.ok(companyDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        companies.remove(id);
    }

    @PutMapping("/{company_id}/new_employee")
    public ResponseEntity<EmployeeDto> addEmployee(@PathVariable("company_id") long companyId, @RequestBody EmployeeDto employeeDto) {
        if (!companies.containsKey(companyId)) {
            return ResponseEntity.notFound().build();
        }
        List<EmployeeDto> emp = companies.get(companyId).getEmployees();
        emp.add(employeeDto);
        return ResponseEntity.ok(employeeDto);
    }

    @DeleteMapping("/{company_id}/employee/{employee_id}")
    public void deleteEmployee(@PathVariable("company_id") long companyId, @PathVariable("employee_id") long employeeId) {
        CompanyDto companyDto = companies.get(companyId);
        EmployeeDto employeeDto = findById(companyDto, employeeId).get();
        companyDto.getEmployees().remove(employeeDto);
    }

    @PutMapping("/{company_id}/employeeList")
    public ResponseEntity<List<EmployeeDto>> replaceEmployeeList(@PathVariable("company_id") long companyId, @RequestBody List<EmployeeDto> newEmployees) {
        if (!companies.containsKey(companyId)) {
            return ResponseEntity.notFound().build();
        }
        companies.get(companyId).setEmployees(newEmployees);
        return ResponseEntity.ok(newEmployees);
    }

    private Optional<EmployeeDto> findById(CompanyDto companyDto, long employeeId) {
        return companyDto.getEmployees()
                .stream()
                .filter(e -> e.getId() == employeeId)
                .findFirst();
    }

    private CompanyDto withoutEmployees(CompanyDto companyDto) {
        CompanyDto company = new CompanyDto(companyDto.getId(), companyDto.getName(), companyDto.getAddress());
        company.setRegistrationNumber(companyDto.getRegistrationNumber());
        return company;
    }
}

package hu.webuni.hr.totinistvan.mapper;

import hu.webuni.hr.totinistvan.model.dto.CompanyDto;
import hu.webuni.hr.totinistvan.model.dto.EmployeeDto;
import hu.webuni.hr.totinistvan.model.entity.Company;
import hu.webuni.hr.totinistvan.model.entity.Employee;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    List<CompanyDto> companiesToDtos(List<Company> companies);

    CompanyDto companyToDto(Company company);

    Company companyDtoToCompany(CompanyDto companyDto);

    @Mapping(target = "employees", ignore = true)
    @Named("summary")
    CompanyDto companyToSummaryDto(Company company);

    @IterableMapping(qualifiedByName = "summary")
    List<CompanyDto> companiesToSummaryDtos(List<Company> companies);

    EmployeeDto employeeToDto(Employee employee);

    Employee employeeDtoToEmployee(EmployeeDto employeeDto);
}

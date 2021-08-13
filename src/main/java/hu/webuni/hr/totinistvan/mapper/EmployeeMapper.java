package hu.webuni.hr.totinistvan.mapper;

import hu.webuni.hr.totinistvan.model.dto.EmployeeDto;
import hu.webuni.hr.totinistvan.model.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    List<EmployeeDto> employeesToDtos(List<Employee> employees);

    @Mapping(target = "position", source = "position.name")
    EmployeeDto employeeToDto(Employee employee);

    @Mapping(source = "position", target = "position.name")
    Employee employeeDtoToEmployee(EmployeeDto employeeDto);

    List<Employee> employeeDtosToEmployees(List<EmployeeDto> employees);
}

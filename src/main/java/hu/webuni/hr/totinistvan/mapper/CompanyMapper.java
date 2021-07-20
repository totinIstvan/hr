package hu.webuni.hr.totinistvan.mapper;

import hu.webuni.hr.totinistvan.model.dto.CompanyDto;
import hu.webuni.hr.totinistvan.model.entity.Company;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    List<CompanyDto> companiesToDtos(List<Company> companies);

    CompanyDto companyToDto(Company company);

    Company companyDtoToCompany(CompanyDto companyDto);
}

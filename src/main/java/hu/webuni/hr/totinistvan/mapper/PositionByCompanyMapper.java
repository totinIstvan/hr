package hu.webuni.hr.totinistvan.mapper;

import hu.webuni.hr.totinistvan.model.dto.PositionByCompanyDto;
import hu.webuni.hr.totinistvan.model.entity.PositionByCompany;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PositionByCompanyMapper {

    List<PositionByCompanyDto> positionByCompaniesToDtos(List<PositionByCompany> positionByCompanies);

    @Mapping(source = "companyId", target = "company.id")
    @Mapping(source = "positionId", target = "position.id")
    PositionByCompany dtoToPositionByCompany(PositionByCompanyDto positionByCompanyDto);

    @Mapping(target = "companyId", source = "company.id")
    @Mapping(target = "positionId", source = "position.id")
    PositionByCompanyDto positionByCompanyToDto(PositionByCompany positionByCompany);
}

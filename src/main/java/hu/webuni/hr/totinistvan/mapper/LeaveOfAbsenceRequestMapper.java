package hu.webuni.hr.totinistvan.mapper;

import hu.webuni.hr.totinistvan.model.dto.LeaveOfAbsenceRequestDto;
import hu.webuni.hr.totinistvan.model.entity.LeaveOfAbsenceRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LeaveOfAbsenceRequestMapper {

    List<LeaveOfAbsenceRequestDto> leaveOfAbsenceRequestsToDtos(List<LeaveOfAbsenceRequest> leaveOfAbsenceRequests);

    @Mapping(target = "applicant", ignore = true)
    @Mapping(target = "approver", ignore = true)
    LeaveOfAbsenceRequest dtoToLeaveOfAbsenceRequest(LeaveOfAbsenceRequestDto leaveOfAbsenceRequestDto);

    @Mapping(source = "applicant.id", target = "applicantId")
    @Mapping(source = "approver.id", target = "approverId")
    LeaveOfAbsenceRequestDto leaveOfAbsenceRequestToDto(LeaveOfAbsenceRequest leaveOfAbsenceRequest);
}

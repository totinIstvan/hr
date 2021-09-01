package hu.webuni.hr.totinistvan.controller;

import hu.webuni.hr.totinistvan.mapper.LeaveOfAbsenceRequestMapper;
import hu.webuni.hr.totinistvan.model.dto.LeaveOfAbsenceRequestDto;
import hu.webuni.hr.totinistvan.model.dto.LeaveOfAbsenceRequestFilterDto;
import hu.webuni.hr.totinistvan.model.entity.LeaveOfAbsenceRequest;
import hu.webuni.hr.totinistvan.service.LeaveOfAbsenceRequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/leaveofabsencerequests")
public class LeaveOfAbsenceRequestController {

    private final LeaveOfAbsenceRequestService leaveOfAbsenceRequestService;

    private final LeaveOfAbsenceRequestMapper leaveOfAbsenceRequestMapper;

    public LeaveOfAbsenceRequestController(LeaveOfAbsenceRequestService leaveOfAbsenceRequestService,
                                           LeaveOfAbsenceRequestMapper leaveOfAbsenceRequestMapper) {
        this.leaveOfAbsenceRequestService = leaveOfAbsenceRequestService;
        this.leaveOfAbsenceRequestMapper = leaveOfAbsenceRequestMapper;
    }

    @GetMapping
    public List<LeaveOfAbsenceRequestDto> getAll() {
        return leaveOfAbsenceRequestMapper.leaveOfAbsenceRequestsToDtos(leaveOfAbsenceRequestService.findAll());
    }

    @GetMapping("/{id}")
    public LeaveOfAbsenceRequestDto getById(@PathVariable long id) {
        LeaveOfAbsenceRequest leaveOfAbsenceRequest = leaveOfAbsenceRequestService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Request for leave of absence with id " + id + " not found"));
        return leaveOfAbsenceRequestMapper.leaveOfAbsenceRequestToDto(leaveOfAbsenceRequest);
    }

    @PostMapping
    public LeaveOfAbsenceRequestDto addNew(@RequestBody @Valid LeaveOfAbsenceRequestDto leaveOfAbsenceRequestDto) {
        leaveOfAbsenceRequestDto.setAccepted(null);
        leaveOfAbsenceRequestDto.setApprovalDate(null);
        leaveOfAbsenceRequestDto.setApplicationDate(LocalDateTime.now());
        LeaveOfAbsenceRequest leaveOfAbsenceRequest;
        long applicantId = leaveOfAbsenceRequestDto.getApplicantId();
        try {
            leaveOfAbsenceRequest = leaveOfAbsenceRequestService
                    .save(leaveOfAbsenceRequestMapper
                            .dtoToLeaveOfAbsenceRequest(leaveOfAbsenceRequestDto), applicantId);
            return leaveOfAbsenceRequestMapper
                    .leaveOfAbsenceRequestToDto(leaveOfAbsenceRequest);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with id " + applicantId + " not found");
        }
    }

    @PutMapping("/{id}")
    public LeaveOfAbsenceRequestDto update(@PathVariable long id,
                                           @RequestBody @Valid LeaveOfAbsenceRequestDto leaveOfAbsenceRequestDto) {
        leaveOfAbsenceRequestDto.setId(id);
        long applicantId = leaveOfAbsenceRequestDto.getApplicantId();
        leaveOfAbsenceRequestDto.setApprovalDate(null);
        leaveOfAbsenceRequestDto.setApplicationDate(LocalDateTime.now());
        try {
            LeaveOfAbsenceRequestDto savedLeaveOfAbsenceRequestDto = leaveOfAbsenceRequestMapper
                    .leaveOfAbsenceRequestToDto(leaveOfAbsenceRequestService
                            .update(leaveOfAbsenceRequestMapper
                                    .dtoToLeaveOfAbsenceRequest(leaveOfAbsenceRequestDto), applicantId));
            savedLeaveOfAbsenceRequestDto.setApplicantId(leaveOfAbsenceRequestDto.getApplicantId());
            return savedLeaveOfAbsenceRequestDto;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Request for leave of absence with id " + id + " not found");
        } catch (IllegalAccessException exception) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Request for leave of absence with id " + id + " is already evaluated");
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        try {
            leaveOfAbsenceRequestService.deleteById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Request for leave of absence with id " + id + " not found");
        } catch (IllegalAccessException exception) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Request for leave of absence with id " + id + " is already evaluated");
        }
    }

    @PutMapping("/{id}/approver/{approverId}")
    public LeaveOfAbsenceRequestDto approveLeaveOfAbsentRequest(@PathVariable long id,
                                                                @PathVariable long approverId,
                                                                @RequestParam boolean status) {
        LeaveOfAbsenceRequest leaveOfAbsenceRequest;
        try {
            leaveOfAbsenceRequest = leaveOfAbsenceRequestService.approve(id, approverId, status);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Request for leave of absence with id " + id + " not found");
        }
        return leaveOfAbsenceRequestMapper.leaveOfAbsenceRequestToDto(leaveOfAbsenceRequest);
    }

    @PostMapping("/byExample")
    public List<LeaveOfAbsenceRequestDto> getLeaveOfAbsentRequestsByExample(@RequestBody LeaveOfAbsenceRequestFilterDto example, Pageable pageable) {
        Page<LeaveOfAbsenceRequest> page = leaveOfAbsenceRequestService.findRequestsByExample(example, pageable);
        return leaveOfAbsenceRequestMapper.leaveOfAbsenceRequestsToDtos(page.getContent());
    }
}

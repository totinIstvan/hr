package hu.webuni.hr.totinistvan.service;

import hu.webuni.hr.totinistvan.model.dto.LeaveOfAbsenceRequestFilterDto;
import hu.webuni.hr.totinistvan.model.entity.Employee;
import hu.webuni.hr.totinistvan.model.entity.LeaveOfAbsenceRequest;
import hu.webuni.hr.totinistvan.repository.EmployeeRepository;
import hu.webuni.hr.totinistvan.repository.LeaveOfAbsenceRequestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class LeaveOfAbsenceRequestService {

    private final LeaveOfAbsenceRequestRepository leaveOfAbsenceRequestRepository;

    private final EmployeeRepository employeeRepository;

    public LeaveOfAbsenceRequestService(LeaveOfAbsenceRequestRepository leaveOfAbsenceRequestRepository,
                                        EmployeeRepository employeeRepository) {
        this.leaveOfAbsenceRequestRepository = leaveOfAbsenceRequestRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<LeaveOfAbsenceRequest> findAll() {
        return leaveOfAbsenceRequestRepository.findAll();
    }

    public Optional<LeaveOfAbsenceRequest> findById(long id) {
        return leaveOfAbsenceRequestRepository.findById(id);
    }

    @Transactional
    public LeaveOfAbsenceRequest save(LeaveOfAbsenceRequest leaveOfAbsenceRequest, long applicantId) {
        if (employeeRepository.existsById(applicantId)) {
            Employee applicant = employeeRepository.getById(applicantId);
            applicant.addLeaveOfAbsenceRequest(leaveOfAbsenceRequest);
            return leaveOfAbsenceRequestRepository.save(leaveOfAbsenceRequest);
        }
        throw new NoSuchElementException();
    }

    @Transactional
    public LeaveOfAbsenceRequest approve(long id, long approverId, boolean status) {
        if (leaveOfAbsenceRequestRepository.existsById(id)) {
            LeaveOfAbsenceRequest leaveOfAbsenceRequest = leaveOfAbsenceRequestRepository.findById(id).get();
            leaveOfAbsenceRequest.setApprover(employeeRepository.findById(approverId).get());
            leaveOfAbsenceRequest.setAccepted(status);
            leaveOfAbsenceRequest.setApprovalDate(LocalDateTime.now());
            return leaveOfAbsenceRequestRepository.save(leaveOfAbsenceRequest);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Transactional
    public LeaveOfAbsenceRequest update(LeaveOfAbsenceRequest leaveOfAbsenceRequest, long applicantId) throws IllegalAccessException {
        if (leaveOfAbsenceRequest.getAccepted() != null) {
            throw new IllegalAccessException();
        }
        if (leaveOfAbsenceRequestRepository.existsById(leaveOfAbsenceRequest.getId())) {
            return save(leaveOfAbsenceRequest, applicantId);
        }
        throw new NoSuchElementException();
    }

    @Transactional
    public void deleteById(long id) throws IllegalAccessException {
        if (leaveOfAbsenceRequestRepository.existsById(id)) {
            LeaveOfAbsenceRequest leaveOfAbsenceRequest = leaveOfAbsenceRequestRepository.findById(id).get();
            if (leaveOfAbsenceRequest.getAccepted() != null) {
                throw new IllegalAccessException();
            }
            leaveOfAbsenceRequest.getApplicant().getLeaveOfAbsenceRequests().remove(leaveOfAbsenceRequest);
            leaveOfAbsenceRequestRepository.deleteById(id);
        } else {
            throw new NoSuchElementException();
        }
    }

    public Page<LeaveOfAbsenceRequest> findRequestsByExample(LeaveOfAbsenceRequestFilterDto example, Pageable pageable) {
        Boolean accepted = example.getAccepted();
        String nameOfApplicant = example.getApplicantName();
        String nameOfApprover = example.getApproverName();
        LocalDateTime requestsDateStart = example.getRequestsDateStart();
        LocalDateTime requestsDateEnd = example.getRequestsDateEnd();
        LocalDate startOfLeaveOfAbsence = example.getStartDate();
        LocalDate endOfLeaveOfAbsence = example.getEndDate();

        Specification<LeaveOfAbsenceRequest> spec = Specification.where(null);

        if (accepted != null) {
            spec = spec.and(LeaveOfAbsenceRequestSpecifications.hasAccepted(accepted));
        }
        if (StringUtils.hasText(nameOfApplicant)) {
            spec = spec.and(LeaveOfAbsenceRequestSpecifications.hasNameOfApplicant(nameOfApplicant));
        }
        if (StringUtils.hasText(nameOfApprover)) {
            spec = spec.and(LeaveOfAbsenceRequestSpecifications.hasNameOfApprover(nameOfApprover));
        }
        if (requestsDateStart != null && requestsDateEnd != null) {
            spec = spec.and(LeaveOfAbsenceRequestSpecifications.requestDateIsBetween(requestsDateStart, requestsDateEnd));
        }
        if (startOfLeaveOfAbsence != null) {
            spec = spec.and(LeaveOfAbsenceRequestSpecifications.isStartDateBefore(startOfLeaveOfAbsence));
        }
        if (endOfLeaveOfAbsence != null) {
            spec = spec.and(LeaveOfAbsenceRequestSpecifications.isEndDateAfter(endOfLeaveOfAbsence));
        }

        return leaveOfAbsenceRequestRepository.findAll(spec, pageable);
    }
}

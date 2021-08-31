package hu.webuni.hr.totinistvan.service;

import hu.webuni.hr.totinistvan.model.entity.Employee_;
import hu.webuni.hr.totinistvan.model.entity.LeaveOfAbsenceRequest;
import hu.webuni.hr.totinistvan.model.entity.LeaveOfAbsenceRequest_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LeaveOfAbsenceRequestSpecifications {

    public static Specification<LeaveOfAbsenceRequest> hasAccepted(Boolean accepted) {
        return (root, cq, cb) -> cb.equal(root.get(LeaveOfAbsenceRequest_.accepted), accepted);
    }

    public static Specification<LeaveOfAbsenceRequest> hasNameOfApplicant(String nameOfApplicant) {
        return (root, cq, cb) -> cb.like(cb.lower(root.get(LeaveOfAbsenceRequest_.applicant).get(Employee_.name)),
                (nameOfApplicant.toLowerCase() + "%"));
    }

    public static Specification<LeaveOfAbsenceRequest> hasNameOfApprover(String nameOfApprover) {
        return (root, cq, cb) -> cb.like(cb.lower(root.get(LeaveOfAbsenceRequest_.approver).get(Employee_.name)),
                (nameOfApprover.toLowerCase() + "%"));
    }

    public static Specification<LeaveOfAbsenceRequest> requestDateIsBetween(LocalDateTime requestsDateStart,
                                                                            LocalDateTime requestsDateEnd) {
        return (root, cq, cb) -> cb.between(root.get(LeaveOfAbsenceRequest_.applicationDate),
                requestsDateStart, requestsDateEnd);
    }

    public static Specification<LeaveOfAbsenceRequest> isStartDateBefore(LocalDate startOfLeaveOfAbsence) {
        return (root, cq, cb) -> cb.lessThan(root.get(LeaveOfAbsenceRequest_.startDate), startOfLeaveOfAbsence);
    }

    public static Specification<LeaveOfAbsenceRequest> isEndDateAfter(LocalDate endOfLeaveOfAbsence) {
        return (root, cq, cb) -> cb.greaterThan(root.get(LeaveOfAbsenceRequest_.endDate), endOfLeaveOfAbsence);
    }
}

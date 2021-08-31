package hu.webuni.hr.totinistvan.repository;

import hu.webuni.hr.totinistvan.model.entity.LeaveOfAbsenceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LeaveOfAbsenceRequestRepository extends JpaRepository<LeaveOfAbsenceRequest, Long>, JpaSpecificationExecutor<LeaveOfAbsenceRequest> {
}

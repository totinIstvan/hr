package hu.webuni.hr.totinistvan.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LeaveOfAbsenceRequestFilterDto {

    private LocalDateTime requestsDateStart;
    private LocalDateTime requestsDateEnd;
    private String applicantName;
    private String approverName;
    private  Boolean accepted;
    private LocalDate startDate;
    private LocalDate endDate;

    public LeaveOfAbsenceRequestFilterDto() {
    }

    public LocalDateTime getRequestsDateStart() {
        return requestsDateStart;
    }

    public void setRequestsDateStart(LocalDateTime requestsDateStart) {
        this.requestsDateStart = requestsDateStart;
    }

    public LocalDateTime getRequestsDateEnd() {
        return requestsDateEnd;
    }

    public void setRequestsDateEnd(LocalDateTime requestsDateEnd) {
        this.requestsDateEnd = requestsDateEnd;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}

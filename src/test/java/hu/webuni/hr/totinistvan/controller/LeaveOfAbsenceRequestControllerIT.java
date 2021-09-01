package hu.webuni.hr.totinistvan.controller;

import hu.webuni.hr.totinistvan.model.dto.LeaveOfAbsenceRequestDto;
import hu.webuni.hr.totinistvan.model.dto.LeaveOfAbsenceRequestFilterDto;
import hu.webuni.hr.totinistvan.model.entity.Company;
import hu.webuni.hr.totinistvan.model.entity.Employee;
import hu.webuni.hr.totinistvan.model.entity.Position;
import hu.webuni.hr.totinistvan.repository.CompanyRepository;
import hu.webuni.hr.totinistvan.repository.EmployeeRepository;
import hu.webuni.hr.totinistvan.repository.PositionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureWebTestClient(timeout = "36000")
public class LeaveOfAbsenceRequestControllerIT {

    private static final String BASE_URI = "/api/leaveofabsencerequests";

    private LeaveOfAbsenceRequestDto testRequest;

    private Employee testEmployee1;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void init() {
        Company testCompany = new Company("Test Registration Number", "Test Company", "Test Address");
        companyRepository.save(testCompany);

        Position testPosition = new Position("Test Position");
        positionRepository.save(testPosition);

        this.testEmployee1 = new Employee("Test Employee1", testPosition, 13800, LocalDateTime.now());
        Employee testEmployee2 = new Employee("Test Employee2", testPosition, 11000, LocalDateTime.now());

        this.testEmployee1.setCompany(testCompany);
        testEmployee2.setCompany(testCompany);

        employeeRepository.save(this.testEmployee1);
        employeeRepository.save(testEmployee2);

        this.testRequest = new LeaveOfAbsenceRequestDto();
        this.testRequest.setApplicantId(testEmployee2.getId());
        this.testRequest.setStartDate(LocalDate.of(2021, 10, 11));
        this.testRequest.setEndDate(LocalDate.of(2021, 10, 15));
    }

    @Test
    void addNew_createLeaveOfAbsenceRequestWithValidData_returnsCorrectResults() {
        List<LeaveOfAbsenceRequestDto> requestsBefore = getAllRequests();

        long id = requestsBefore.size() + 1;
        this.testRequest.setId(id);

        createLeaveOfAbsenceRequest(this.testRequest)
                .expectStatus().isOk();

        List<LeaveOfAbsenceRequestDto> requestsAfter = getAllRequests();

        assertThat(requestsBefore.subList(0, requestsBefore.size()))
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(requestsBefore);

        assertThat(requestsAfter.get(requestsAfter.size() - 1))
                .usingRecursiveComparison()
                .ignoringFields("applicationDate")
                .isEqualTo(testRequest);
    }

    @Test
    void update_withValidData_returnsUpdatedLeaveOfAbsenceRequest() {
        long id = getAllRequests().size() + 1;
        this.testRequest.setId(id);
        createLeaveOfAbsenceRequest(this.testRequest)
                .expectStatus().isOk();
        List<LeaveOfAbsenceRequestDto> requestsBefore = getAllRequests();

        LeaveOfAbsenceRequestDto updatedRequest = new LeaveOfAbsenceRequestDto();
        updatedRequest.setStartDate(LocalDate.of(2021, 11, 11));
        updatedRequest.setEndDate(LocalDate.of(2021, 11, 19));
        updatedRequest.setApplicantId(this.testRequest.getApplicantId());
        updatedRequest.setId(id);

        updateLeaveOfAbsenceRequest(updatedRequest, id);

        List<LeaveOfAbsenceRequestDto> requestsAfter = getAllRequests();

        assertThat(requestsBefore.get(requestsBefore.size() - 1))
                .usingRecursiveComparison()
                .isNotEqualTo(requestsAfter.get(requestsAfter.size() - 1));

        assertThat(requestsAfter.get(requestsAfter.size() - 1))
                .usingRecursiveComparison()
                .ignoringFields("applicationDate")
                .isEqualTo(updatedRequest);
    }

    @Test
    void approveLeaveOfAbsentRequest_returnsApprovedResultWithApproverId() {
        long id = getAllRequests().size() + 1;
        this.testRequest.setId(id);
        createLeaveOfAbsenceRequest(this.testRequest)
                .expectStatus().isOk();

        approveLeaveOfAbsentRequest(id, this.testEmployee1.getId(), true);

        List<LeaveOfAbsenceRequestDto> requestsAfter = getAllRequests();
        LeaveOfAbsenceRequestDto result = requestsAfter.get(requestsAfter.size() - 1);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("applicationDate", "approverId", "approvalDate", "accepted")
                .isEqualTo(this.testRequest);
        assertThat(result.getAccepted())
                .isTrue();
        assertThat(result.getApproverId()).isEqualTo(this.testEmployee1.getId());
        assertThat(result.getApprovalDate()).isNotNull();
    }

    @Test
    void update_alreadyEvaluatedLeaveOfAbsenceRequest_returnsForbiddenResponseStatusAndNotModifiedAnything() {
        long id = getAllRequests().size() + 1;
        this.testRequest.setId(id);
        createLeaveOfAbsenceRequest(this.testRequest)
                .expectStatus().isOk();
        approveLeaveOfAbsentRequest(id, this.testEmployee1.getId(), true);

        int index = getAllRequests().size() - 1;
        LeaveOfAbsenceRequestDto expected = getAllRequests().get(index);
        LeaveOfAbsenceRequestDto evaluatedRequest = getAllRequests().get(index);

        evaluatedRequest.setStartDate(LocalDate.of(2021, 11, 11));
        evaluatedRequest.setEndDate(LocalDate.of(2021, 11, 19));
        updateLeaveOfAbsenceRequest(evaluatedRequest, id)
                .expectStatus()
                .isForbidden();

        LeaveOfAbsenceRequestDto result = getAllRequests().get(index);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void getLeaveOfAbsentRequestsByExample_callWithValidData_returnsListOfMatchingRequests() {
        int index = getAllRequests().size();
        long applicantId = this.testRequest.getApplicantId();
        long approverId = this.testEmployee1.getId();
        LocalDate startDate = LocalDate.of(2021, 10, 1);
        LocalDate endDate = startDate.plusDays(10);
        LeaveOfAbsenceRequestDto request1 = new LeaveOfAbsenceRequestDto(applicantId, startDate, endDate);
        LeaveOfAbsenceRequestDto request2 = new LeaveOfAbsenceRequestDto(applicantId, startDate.plusDays(2), endDate.plusDays(2));
        LeaveOfAbsenceRequestDto request3 = new LeaveOfAbsenceRequestDto(applicantId, startDate.plusDays(2), endDate.plusDays(2));
        LeaveOfAbsenceRequestDto request4 = new LeaveOfAbsenceRequestDto(applicantId, startDate.plusDays(15), endDate.plusDays(15));
        List<LeaveOfAbsenceRequestDto> requests = List.of(request1, request2, request3, request4);
        requests.forEach(this::createLeaveOfAbsenceRequest);
        requests = getAllRequests();
        approveLeaveOfAbsentRequest(requests.get(index).getId(), approverId, true);
        approveLeaveOfAbsentRequest(requests.get(index + 1).getId(), approverId, true);
        approveLeaveOfAbsentRequest(requests.get(index + 2).getId(), approverId, false);
        approveLeaveOfAbsentRequest(requests.get(index + 3).getId(), approverId, true);
        List<LeaveOfAbsenceRequestDto> requestsBefore = getAllRequests().subList(index, index + 4);

        LeaveOfAbsenceRequestFilterDto example = new LeaveOfAbsenceRequestFilterDto();
        example.setStartDate(startDate.plusDays(3));
        example.setEndDate(endDate.minusDays(1));
        example.setApplicantName("tEsT");
        example.setApproverName("TeSt");
        example.setAccepted(true);

        List<LeaveOfAbsenceRequestDto> filtered = getAllByExample(example);

        assertThat(filtered)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(requestsBefore.subList(0, 2));
    }

    private ResponseSpec createLeaveOfAbsenceRequest(LeaveOfAbsenceRequestDto leaveOfAbsenceRequest) {
        return webTestClient
                .post()
                .uri(BASE_URI)
                .bodyValue(leaveOfAbsenceRequest)
                .exchange();
    }

    private ResponseSpec updateLeaveOfAbsenceRequest(LeaveOfAbsenceRequestDto leaveOfAbsenceRequest, long id) {
        return webTestClient
                .put()
                .uri(BASE_URI + "/" + id)
                .bodyValue(leaveOfAbsenceRequest)
                .exchange();
    }

    private ResponseSpec approveLeaveOfAbsentRequest(long id, long approverId, boolean status) {
        return webTestClient
                .put()
                .uri(BASE_URI + "/" + id + "/approver/" + approverId + "?status=" + status)
                .exchange();
    }

    private List<LeaveOfAbsenceRequestDto> getAllRequests() {
        List<LeaveOfAbsenceRequestDto> responseList = webTestClient
                .get()
                .uri(BASE_URI)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(LeaveOfAbsenceRequestDto.class)
                .returnResult().getResponseBody();

        responseList.sort(Comparator.comparingLong(LeaveOfAbsenceRequestDto::getId));
        return responseList;
    }

    private List<LeaveOfAbsenceRequestDto> getAllByExample(LeaveOfAbsenceRequestFilterDto example) {
        List<LeaveOfAbsenceRequestDto> responseList = webTestClient
                .post()
                .uri(BASE_URI + "/byExample")
                .bodyValue(example)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(LeaveOfAbsenceRequestDto.class)
                .returnResult().getResponseBody();

        responseList.sort(Comparator.comparingLong(LeaveOfAbsenceRequestDto::getId));
        return responseList;
    }
}

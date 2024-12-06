package mb.pso.issuesystem.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;

public record IssueDto(Integer id, String status, String docNumber, Date docDate, ClientDto client, IssueTypeDto type,
        Set<DepartmentFeedbackDto> departmentFeedbacks, String issueResult, List<AttachedFileDto> attachedFiles,
        List<String> relatedDocFromSigma, Set<EmployeeDto> issuedEmployees, String issuedDemands,
        SubjectDto subject, List<IssueAttributeDto> issueAttributes, String issueDescription,
        List<AdditionalAttributeDto> additionalAttributes,
        ChatDto chat

) {

}

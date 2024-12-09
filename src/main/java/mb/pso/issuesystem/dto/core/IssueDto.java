package mb.pso.issuesystem.dto.core;

import java.util.Date;
import java.util.List;
import java.util.Set;

import mb.pso.issuesystem.dto.im.ChatDto;

public record IssueDto(Integer id, String status, String docNumber, Date docDate, ClientDto client, IssueTypeDto type,
        Set<DepartmentFeedbackDto> departmentFeedbacks, String issueResult, List<AttachedFileDto> attachedFiles,
        List<String> relatedDocFromSigma, Set<EmployeeDto> issuedEmployees, String issuedDemands,
        SubjectDto subject, List<IssueAttributeDto> issueAttributes, String issueDescription,
        List<AdditionalAttributeDto> additionalAttributes,
        ChatDto chat

) {

}

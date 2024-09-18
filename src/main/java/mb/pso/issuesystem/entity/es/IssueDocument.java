package mb.pso.issuesystem.entity.es;

import java.util.Date;
import java.util.List;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import jakarta.persistence.Id;
import mb.pso.issuesystem.entity.enums.IssueStatus;

@Document(indexName = "pso_issue_gzk")
@Setting(settingPath = "/es_settings/pso_issue/settings.json")
public class IssueDocument {
    @Id
    private Integer id;
    @Field(type = FieldType.Keyword)
    private IssueStatus status;
    @Field(type = FieldType.Date, format = DateFormat.epoch_second)
    private Date docDate;

    @Field(type = FieldType.Text, analyzer = "my_index_analyzer")
    private String clientName;
    @Field(type = FieldType.Text, analyzer = "my_index_analyzer")
    private String clientAdress;
    @Field(type = FieldType.Text, analyzer = "my_index_analyzer")
    private String clientEmail;
    @Field(type = FieldType.Text, analyzer = "my_index_analyzer")
    private String clientPhoneNumber;

    @Field(type = FieldType.Keyword)
    private String type;

    @Field(type = FieldType.Text, analyzer = "my_index_analyzer")
    private String subjectDescription;
    @Field(type = FieldType.Text, analyzer = "my_index_analyzer")
    private String subjectVin;
    @Field(type = FieldType.Keyword)
    private String subjectType;

    @Field(type = FieldType.Keyword)
    private List<String> issueAttributes;
    @Field(type = FieldType.Text, analyzer = "my_index_analyzer")
    private String issueDescription;
    @Field(type = FieldType.Text, analyzer = "my_index_analyzer")
    private String issuedDepartment;

    @Field(type = FieldType.Text, analyzer = "my_index_analyzer")
    private String issuedEmployeeGivenName;
    @Field(type = FieldType.Text, analyzer = "my_index_analyzer")
    private String issuedEmployeeSn;
    @Field(type = FieldType.Text, analyzer = "my_index_analyzer")
    private String issuedEmployeeMail;

    @Field(type = FieldType.Text, analyzer = "my_index_analyzer")
    private String issuedDemands;
    @Field(type = FieldType.Keyword)
    private List<String> additionalAttributes;
    @Field(type = FieldType.Text, analyzer = "my_index_analyzer")
    private String departmentFeedback;
    @Field(type = FieldType.Text, analyzer = "my_index_analyzer")
    private String issueResult;

    public IssueDocument(Integer id, IssueStatus status, Date docDate, String clientName, String clientAdress,
            String clientEmail, String clientPhoneNumber, String type, String subjectDescription, String subjectVin,
            String subjectType, List<String> issueAttributes, String issueDescription, String issuedDepartment,
            String issuedEmployeeGivenName, String issuedEmployeeSn, String issuedEmployeeMail, String issuedDemands,
            List<String> additionalAttributes, String departmentFeedback, String issueResult) {
        this.id = id;
        this.status = status;
        this.docDate = docDate;
        this.clientName = clientName;
        this.clientAdress = clientAdress;
        this.clientEmail = clientEmail;
        this.clientPhoneNumber = clientPhoneNumber;
        this.type = type;
        this.subjectDescription = subjectDescription;
        this.subjectVin = subjectVin;
        this.subjectType = subjectType;
        this.issueAttributes = issueAttributes;
        this.issueDescription = issueDescription;
        this.issuedDepartment = issuedDepartment;
        this.issuedEmployeeGivenName = issuedEmployeeGivenName;
        this.issuedEmployeeSn = issuedEmployeeSn;
        this.issuedEmployeeMail = issuedEmployeeMail;
        this.issuedDemands = issuedDemands;
        this.additionalAttributes = additionalAttributes;
        this.departmentFeedback = departmentFeedback;
        this.issueResult = issueResult;
    }

    public Integer getId() {
        return id;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public Date getDocDate() {
        return docDate;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientAdress() {
        return clientAdress;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public String getType() {
        return type;
    }

    public String getSubjectDescription() {
        return subjectDescription;
    }

    public String getSubjectVin() {
        return subjectVin;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public List<String> getIssueAttributes() {
        return issueAttributes;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public String getIssuedDepartment() {
        return issuedDepartment;
    }

    public String getIssuedEmployeeGivenName() {
        return issuedEmployeeGivenName;
    }

    public String getIssuedEmployeeSn() {
        return issuedEmployeeSn;
    }

    public String getIssuedEmployeeMail() {
        return issuedEmployeeMail;
    }

    public String getIssuedDemands() {
        return issuedDemands;
    }

    public List<String> getAdditionalAttributes() {
        return additionalAttributes;
    }

    public String getDepartmentFeedback() {
        return departmentFeedback;
    }

    public String getIssueResult() {
        return issueResult;
    }

}
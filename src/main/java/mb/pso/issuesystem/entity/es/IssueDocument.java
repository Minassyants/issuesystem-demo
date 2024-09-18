package mb.pso.issuesystem.entity.es;

import java.util.Date;
import java.util.List;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.data.elasticsearch.annotations.Setting;

import jakarta.persistence.Id;
import mb.pso.issuesystem.entity.AdditionalAttribute;
import mb.pso.issuesystem.entity.Client;
import mb.pso.issuesystem.entity.Department;
import mb.pso.issuesystem.entity.Employee;
import mb.pso.issuesystem.entity.IssueAttribute;
import mb.pso.issuesystem.entity.IssueType;
import mb.pso.issuesystem.entity.Subject;
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

    @Field(type = FieldType.Keyword, analyzer = "my_index_analyzer")
    private List<String> issueAttributes;
    @Field(type = FieldType.Text, analyzer = "my_index_analyzer")
    private String issueDescription;
    @Field(type = FieldType.Text, analyzer = "my_index_analyzer")
    private String issuedDepartment;

    @Field(type = FieldType.Keyword, analyzer = "my_index_analyzer")
    private String issuedEmployeeGivenName;
    @Field(type = FieldType.Keyword, analyzer = "my_index_analyzer")
    private String issuedEmployeeSn;
    @Field(type = FieldType.Keyword, analyzer = "my_index_analyzer")
    private String issuedEmployeeMail;

    @Field(type = FieldType.Text, analyzer = "my_index_analyzer")
    private String issuedDemands;
    @Field(type = FieldType.Keyword, analyzer = "my_index_analyzer")
    private List<String> additionalAttributes;
    @Field(type = FieldType.Text, analyzer = "my_index_analyzer")
    private String departmentFeedback;
    @Field(type = FieldType.Text, analyzer = "my_index_analyzer")
    private String issueResult;

    public IssueDocument() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public IssueType getType() {
        return type;
    }

    public void setType(IssueType type) {
        this.type = type;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public List<IssueAttribute> getIssueAttributes() {
        return issueAttributes;
    }

    public void setIssueAttributes(List<IssueAttribute> issueAttributes) {
        this.issueAttributes = issueAttributes;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public Department getIssuedDepartment() {
        return issuedDepartment;
    }

    public void setIssuedDepartment(Department issuedDepartment) {
        this.issuedDepartment = issuedDepartment;
    }

    public Employee getIssuedEmployee() {
        return issuedEmployee;
    }

    public void setIssuedEmployee(Employee issuedEmployee) {
        this.issuedEmployee = issuedEmployee;
    }

    public String getIssuedDemands() {
        return issuedDemands;
    }

    public void setIssuedDemands(String issuedDemands) {
        this.issuedDemands = issuedDemands;
    }

    public List<AdditionalAttribute> getAdditionalAttributes() {
        return additionalAttributes;
    }

    public void setAdditionalAttributes(List<AdditionalAttribute> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    public String getDepartmentFeedback() {
        return departmentFeedback;
    }

    public void setDepartmentFeedback(String departmentFeedback) {
        this.departmentFeedback = departmentFeedback;
    }

    public String getIssueResult() {
        return issueResult;
    }

    public void setIssueResult(String issueResult) {
        this.issueResult = issueResult;
    }

}

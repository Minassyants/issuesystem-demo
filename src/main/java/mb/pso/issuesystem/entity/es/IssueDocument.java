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

@Document(indexName = "#{@environment.getProperty('es.issue.index-name')}")
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
    private List<String> issuedEmployees;
    @Field(type = FieldType.Text, analyzer = "my_index_analyzer")
    private List<String> departmentFeedbacks;
    @Field(type = FieldType.Text, analyzer = "my_index_analyzer")
    private String issuedDemands;
    @Field(type = FieldType.Keyword)
    private List<String> additionalAttributes;
    @Field(type = FieldType.Text, analyzer = "my_index_analyzer")
    private String issueResult;

    public IssueDocument(Integer id, IssueStatus status, Date docDate, String clientName, String clientAdress,
            String clientEmail, String clientPhoneNumber, String type, String subjectDescription, String subjectVin,
            String subjectType, List<String> issueAttributes, String issueDescription,
            List<String> issuedEmployees, List<String> departmentFeedbacks, String issuedDemands,
            List<String> additionalAttributes, String issueResult) {
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
        this.issuedEmployees = issuedEmployees;
        this.departmentFeedbacks = departmentFeedbacks;
        this.issuedDemands = issuedDemands;
        this.additionalAttributes = additionalAttributes;
        this.issueResult = issueResult;
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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientAdress() {
        return clientAdress;
    }

    public void setClientAdress(String clientAdress) {
        this.clientAdress = clientAdress;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubjectDescription() {
        return subjectDescription;
    }

    public void setSubjectDescription(String subjectDescription) {
        this.subjectDescription = subjectDescription;
    }

    public String getSubjectVin() {
        return subjectVin;
    }

    public void setSubjectVin(String subjectVin) {
        this.subjectVin = subjectVin;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public List<String> getIssueAttributes() {
        return issueAttributes;
    }

    public void setIssueAttributes(List<String> issueAttributes) {
        this.issueAttributes = issueAttributes;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public List<String> getIssuedEmployees() {
        return issuedEmployees;
    }

    public void setIssuedEmployees(List<String> issuedEmployees) {
        this.issuedEmployees = issuedEmployees;
    }

    public List<String> getDepartmentFeedbacks() {
        return departmentFeedbacks;
    }

    public void setDepartmentFeedbacks(List<String> departmentFeedbacks) {
        this.departmentFeedbacks = departmentFeedbacks;
    }

    public String getIssuedDemands() {
        return issuedDemands;
    }

    public void setIssuedDemands(String issuedDemands) {
        this.issuedDemands = issuedDemands;
    }

    public List<String> getAdditionalAttributes() {
        return additionalAttributes;
    }

    public void setAdditionalAttributes(List<String> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    public String getIssueResult() {
        return issueResult;
    }

    public void setIssueResult(String issueResult) {
        this.issueResult = issueResult;
    }

}
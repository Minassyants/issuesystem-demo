package mb.pso.issuesystem.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import mb.pso.issuesystem.entity.enums.IssueStatus;

@Entity
public class Issue {
    @Id
    private String id;

    private IssueStatus status;
    private String docNumber;
    private Date docDate;
    @ManyToOne(cascade = CascadeType.ALL)
    private Client client;
    @ManyToOne(cascade = CascadeType.ALL)
    private IssueType type;
    @ManyToOne(cascade = CascadeType.ALL)
    private Subject subject;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<IssueAttribute> issueAttributes;
    private String issueDescription;
    private List<String> relatedDocFromSigma;
    @ManyToOne(cascade = CascadeType.ALL)
    private Department issuedDepartment;
    private String issuedEmployee;
    private String issuedDemands;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<AdditionalAttribute> additionalAttributes;
    private String departmentFeedback;
    private String issueResult;

    @Override
    public String toString() {
        return "Issue [id=" + id + ", status=" + status + ", docNumber=" + docNumber + ", docDate=" + docDate
                + ", client=" + client + ", type=" + type + ", subject=" + subject + ", issueAttributes="
                + issueAttributes + ", issueDescription=" + issueDescription + ", relatedDocFromSigma="
                + relatedDocFromSigma + ", issuedDepartment=" + issuedDepartment + ", issuedEmployee=" + issuedEmployee
                + ", issuedDemands=" + issuedDemands + ", additionalAttributes=" + additionalAttributes
                + ", departmentFeedback=" + departmentFeedback + ", issueResult=" + issueResult + "]";
    }

    public Issue() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
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

    public List<String> getRelatedDocFromSigma() {
        return relatedDocFromSigma;
    }

    public void setRelatedDocFromSigma(List<String> relatedDocFromSigma) {
        this.relatedDocFromSigma = relatedDocFromSigma;
    }

    public Department getIssuedDepartment() {
        return issuedDepartment;
    }

    public void setIssuedDepartment(Department issuedDepartment) {
        this.issuedDepartment = issuedDepartment;
    }

    public String getIssuedEmployee() {
        return issuedEmployee;
    }

    public void setIssuedEmployee(String issuedEmployee) {
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

    public String getIssueResult() {
        return issueResult;
    }

    public void setIssueResult(String issueResult) {
        this.issueResult = issueResult;
    }

    public boolean hasDepartment() {
        return this.issuedDepartment != null && !this.issuedDepartment.isEmpty();
    }

    public String getDepartmentFeedback() {
        return departmentFeedback;
    }

    public void setDepartmentFeedback(String departmentFeedback) {
        this.departmentFeedback = departmentFeedback;
    }

}

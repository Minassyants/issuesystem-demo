package mb.pso.issuesystem.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import mb.pso.issuesystem.entity.enums.IssueStatus;
import mb.pso.issuesystem.listeners.IssueEntityListener;

@Entity
@EntityListeners(IssueEntityListener.class)
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private IssueStatus status;
    private String docNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date docDate;
    @ManyToOne(cascade = CascadeType.ALL)
    private Client client;
    @ManyToOne(cascade = CascadeType.ALL)
    private IssueType type;
    @ManyToOne(cascade = CascadeType.ALL)
    private Subject subject = null;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<IssueAttribute> issueAttributes;
    @Column(length = 2000)
    private String issueDescription;
    private List<String> relatedDocFromSigma;
    @ManyToOne(cascade = CascadeType.ALL)
    private Department issuedDepartment;
    @ManyToOne(cascade = CascadeType.ALL)
    private Employee issuedEmployee;
    @Column(length = 2000)
    private String issuedDemands;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<AdditionalAttribute> additionalAttributes;
    @Column(length = 2000)
    private String departmentFeedback;
    @Column(length = 2000)
    private String issueResult;
    @OneToMany(cascade = CascadeType.ALL)
    private List<AttachedFile> attachedFiles;

    public Issue(Integer id, IssueStatus status, String docNumber, Date docDate, Client client, IssueType type,
            Subject subject, List<IssueAttribute> issueAttributes, String issueDescription,
            List<String> relatedDocFromSigma, Department issuedDepartment, Employee issuedEmployee,
            String issuedDemands, List<AdditionalAttribute> additionalAttributes, String departmentFeedback,
            String issueResult, List<AttachedFile> attachedFiles) {
        this.id = id;
        this.status = status;
        this.docNumber = docNumber;
        this.docDate = docDate;
        this.client = client;
        this.type = type;
        this.subject = subject;
        this.issueAttributes = issueAttributes;
        this.issueDescription = issueDescription;
        this.relatedDocFromSigma = relatedDocFromSigma;
        this.issuedDepartment = issuedDepartment;
        this.issuedEmployee = issuedEmployee;
        this.issuedDemands = issuedDemands;
        this.additionalAttributes = additionalAttributes;
        this.departmentFeedback = departmentFeedback;
        this.issueResult = issueResult;
        this.attachedFiles = attachedFiles;
    }

    public List<AttachedFile> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(List<AttachedFile> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }

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

    public boolean hasEmployee() {
        return this.issuedEmployee != null && !this.issuedEmployee.isEmpty();
    }

    public String getDepartmentFeedback() {
        return departmentFeedback;
    }

    public void setDepartmentFeedback(String departmentFeedback) {
        this.departmentFeedback = departmentFeedback;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getIssuedEmployee() {
        return issuedEmployee;
    }

    public void setIssuedEmployee(Employee issuedEmployee) {
        this.issuedEmployee = issuedEmployee;
    }

}

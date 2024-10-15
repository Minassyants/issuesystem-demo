package mb.pso.issuesystem.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
import jakarta.persistence.OneToOne;
import mb.pso.issuesystem.entity.enums.IssueStatus;
import mb.pso.issuesystem.entity.im.Chat;
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
    @ManyToMany(cascade = CascadeType.MERGE)
    private List<IssueAttribute> issueAttributes = new ArrayList<>();
    @Column(length = 2000)
    private String issueDescription;
    private List<String> relatedDocFromSigma;
    @ManyToOne(cascade = CascadeType.ALL)
    private Department issuedDepartment;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Employee> issuedEmployees = Collections.emptySet();
    @Column(length = 2000)
    private String issuedDemands;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<AdditionalAttribute> additionalAttributes;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<DepartmentFeedback> departmentFeedbacks = Collections.emptySet();
    @Column(length = 2000)
    private String issueResult;
    @OneToMany(cascade = CascadeType.ALL)
    private List<AttachedFile> attachedFiles = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    private Chat chat;

    public Issue(Integer id, IssueStatus status, String docNumber, Date docDate, Client client, IssueType type,
            Subject subject, List<IssueAttribute> issueAttributes, String issueDescription,
            List<String> relatedDocFromSigma, Department issuedDepartment, Set<Employee> issuedEmployees,
            String issuedDemands, List<AdditionalAttribute> additionalAttributes,
            Set<DepartmentFeedback> departmentFeedbacks, String issueResult, List<AttachedFile> attachedFiles,
            Chat chat) {
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
        this.issuedEmployees = issuedEmployees;
        this.issuedDemands = issuedDemands;
        this.additionalAttributes = additionalAttributes;
        this.departmentFeedbacks = departmentFeedbacks;
        this.issueResult = issueResult;
        this.attachedFiles = attachedFiles;
        this.chat = chat;
    }

    public void addAttachedFile(AttachedFile file) {
        this.attachedFiles.add(file);
    }

    public void addAllAttachedFile(List<AttachedFile> files) {
        this.attachedFiles.addAll(files);
    }

    public List<AttachedFile> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(List<AttachedFile> attachedFiles) {
        this.attachedFiles.clear();
        this.attachedFiles.addAll(attachedFiles);
    }

    @Override
    public String toString() {
        return "Issue [id=" + id + ", status=" + status + ", docNumber=" + docNumber + ", docDate=" + docDate
                + ", client=" + client + ", type=" + type + ", subject=" + subject + ", issueAttributes="
                + issueAttributes + ", issueDescription=" + issueDescription + ", relatedDocFromSigma="
                + relatedDocFromSigma + ", issuedDepartment=" + issuedDepartment + ", issuedEmployees="
                + issuedEmployees + ", issuedDemands=" + issuedDemands + ", additionalAttributes="
                + additionalAttributes + ", departmentFeedbacks=" + departmentFeedbacks + ", issueResult=" + issueResult
                + ", attachedFiles=" + attachedFiles + ", chat=" + chat + "]";
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
        this.issueAttributes.clear();
        this.issueAttributes.addAll(issueAttributes);
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
        // chat.setIssue(this);
    }

    public void addAllIssuedEmployees(List<Employee> employees) {
        this.issuedEmployees.addAll(employees);
    }

    public void addIssuedEmployee(Employee employee) {
        this.issuedEmployees.add(employee);
    }

    public Set<Employee> getIssuedEmployees() {
        return issuedEmployees;
    }

    public void setIssuedEmployees(List<Employee> issuedEmployees) {
        this.issuedEmployees.clear();
        this.issuedEmployees.addAll(issuedEmployees);
    }

    public void addDepartmentFeedbacks(DepartmentFeedback departmentFeedback) {
        this.departmentFeedbacks.add(departmentFeedback);
    }

    public void addAllDepartmentFeedbacks(List<DepartmentFeedback> departmentFeedbacks) {
        this.departmentFeedbacks.addAll(departmentFeedbacks);
    }

    public Set<DepartmentFeedback> getDepartmentFeedbacks() {
        return departmentFeedbacks;
    }

    public void setDepartmentFeedbacks(List<DepartmentFeedback> departmentFeedbacks) {
        this.departmentFeedbacks.clear();
        this.departmentFeedbacks.addAll(departmentFeedbacks);
    }

}

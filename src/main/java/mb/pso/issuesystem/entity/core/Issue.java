package mb.pso.issuesystem.entity.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import mb.pso.issuesystem.entity.enums.IssueStatus;
import mb.pso.issuesystem.entity.im.Chat;
import mb.pso.issuesystem.listeners.IssueEntityListener;
import mb.pso.issuesystem.listeners.IssueEntityNotificationListener;


/**
 * Represents an issue or complaint raised by a client in the system.
 * 
 * The {@code Issue} class stores detailed information about an issue,
 * including its status, description, related documents, assigned employees,
 * feedback, and attached files. It also maintains relationships with other
 * entities, such as the client who raised the issue, the type of issue, the
 * subject matter, and any related communications.
 * 
 * 
 * @see Client
 * @see IssueType
 * @see Subject
 * @see Employee
 * @see Chat
 */
@Entity
@EntityListeners({ IssueEntityListener.class, IssueEntityNotificationListener.class })
public class Issue {

    /**
     * Unique ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * Contains current status of the issue.
     */
    @Enumerated(EnumType.STRING)
    private IssueStatus status;

    /**
     * Not implemented.
     */
    private String docNumber;

    /**
     * Date of creation.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date docDate;

    /**
     * The client who raised the issue.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull
    private Client client;

    /**
     * The way issue was recieved (e.g. e-mail, 2Gis).
     */
    @ManyToOne(cascade = CascadeType.ALL)
    private IssueType type;

    /**
     * Issue subject (e.g {@link Vehicle}, {@link Good})
     */
    @ManyToOne(cascade = CascadeType.ALL)
    private Subject subject = null;

    /**
     * List of attributes describing the problem, associated with the subject.
     */
    @ManyToMany(cascade = CascadeType.MERGE)
    private List<IssueAttribute> issueAttributes = new ArrayList<>();

    /**
     * Free-form description of the issue, provided by the client.
     */
    @Column(length = 2000)
    @Size(max = 2000)
    private String issueDescription;

    /**
     * Not implemented.
     */
    private List<String> relatedDocFromSigma = new ArrayList<>();

    /**
     * Set of {@link Employee} involved in causing the issue.
     */
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Employee> issuedEmployees = new HashSet<Employee>();

    /**
     * Client's demands or expectations regarding the resolution of the issue.
     */
    @Column(length = 2000)
    @Size(max = 2000)
    private String issuedDemands;

    /**
     * Not implemented.
     */
    @ManyToMany(cascade = CascadeType.ALL)
    private List<AdditionalAttribute> additionalAttributes = new ArrayList<>();

    /**
     * Feedback provided by employees involved in the issue.
     */
    @OneToMany(cascade = CascadeType.ALL)
    private Set<DepartmentFeedback> departmentFeedbacks = new HashSet<DepartmentFeedback>();

    /**
     * Resolution of the issue.
     */
    @Column(length = 2000)
    @Size(max = 2000)
    private String issueResult;

    /**
     * Files attached to the issue, such as photos or videos.
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<AttachedFile> attachedFiles = new ArrayList<>();

    /**
     * Chat associated with the issue.
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Chat chat;

    public Issue(Integer id, IssueStatus status, String docNumber, Date docDate, Client client, IssueType type,
            Subject subject, List<IssueAttribute> issueAttributes, String issueDescription,
            List<String> relatedDocFromSigma, Set<Employee> issuedEmployees,
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
        this.issuedEmployees = issuedEmployees;
        this.issuedDemands = issuedDemands;
        this.additionalAttributes = additionalAttributes;
        this.departmentFeedbacks = departmentFeedbacks;
        this.issueResult = issueResult;
        this.attachedFiles = attachedFiles;
        this.chat = chat;
    }

    public Issue() {
    }

    @Override
    public String toString() {
        return "Issue [id=" + id + ", status=" + status + ", docDate=" + docDate + "]";
    }

    /**
     * Adds a file to the issue.
     * 
     * @param file the file to be added.
     */
    public void addAttachedFiles(AttachedFile file) {
        this.attachedFiles.add(file);
    }

    /**
     * Adds a collection of files to the issue.
     * 
     * @param files the collection of files to be added.
     */
    public void addAttachedFiles(Collection<AttachedFile> files) {
        this.attachedFiles.addAll(files);
    }

    /**
     * Replaces the current collection of attached files with the provided
     * collection.
     * 
     * @param attachedFiles the collection of files to be replaced with.
     */
    public void setAttachedFiles(List<AttachedFile> attachedFiles) {
        this.attachedFiles.clear();
        this.attachedFiles.addAll(attachedFiles);
    }

    public List<AttachedFile> getAttachedFiles() {
        return attachedFiles;
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

    public void addRelatedDocFromSigma(String relatedDocFromSigma) {
        this.relatedDocFromSigma.add(relatedDocFromSigma);
    }

    public void addRelatedDocFromSigma(Collection<String> relatedDocsFromSigma) {
        this.relatedDocFromSigma.addAll(relatedDocsFromSigma);
    }

    public void setRelatedDocFromSigma(Collection<String> relatedDocFromSigma) {
        this.relatedDocFromSigma.clear();
        this.relatedDocFromSigma.addAll(relatedDocFromSigma);
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

    public void addAdditionalAttributes(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.add(additionalAttribute);
    }

    public void addAdditionalAttributes(Collection<AdditionalAttribute> additionalAttributes) {
        this.additionalAttributes.addAll(additionalAttributes);
    }

    public void setAdditionalAttributes(Collection<AdditionalAttribute> additionalAttributes) {
        this.additionalAttributes.clear();
        this.additionalAttributes.addAll(additionalAttributes);
    }

    public String getIssueResult() {
        return issueResult;
    }

    public void setIssueResult(String issueResult) {
        this.issueResult = issueResult;
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

    /**
     * Adds a collection of employees to the issue.
     * 
     * @param employees the collection of employees to be added.
     */
    public void addIssuedEmployees(Collection<Employee> employees) {
        issuedEmployees.addAll(employees);
    }

    /**
     * Adds an instance of employee to the issue.
     * 
     * @param employee the employee to be added.
     */
    public void addIssuedEmployees(Employee employee) {
        this.issuedEmployees.add(employee);
    }

    public Set<Employee> getIssuedEmployees() {
        return issuedEmployees;
    }

    /**
     * Replaces the current collection of employees with the provided collection.
     * 
     * @param issuedEmployees the collection of employees to be replaced with.
     */
    public void setIssuedEmployees(Collection<Employee> issuedEmployees) {

        this.issuedEmployees.clear();
        this.issuedEmployees.addAll(issuedEmployees);
    }

    /**
     * Adds an instance of department feedback to the issue.
     * 
     * @param departmentFeedback the department feedback to be added.
     */
    public void addDepartmentFeedbacks(DepartmentFeedback departmentFeedback) {

        this.departmentFeedbacks.add(departmentFeedback);
    }

    /**
     * Adds a collection of department feedbacks to the issue.
     * 
     * @param departmentFeedbacks the collection of department feedbacks to be
     *                            added.
     */
    public void addDepartmentFeedbacks(Collection<DepartmentFeedback> departmentFeedbacks) {

        this.departmentFeedbacks.addAll(departmentFeedbacks);
    }

    public Set<DepartmentFeedback> getDepartmentFeedbacks() {
        return departmentFeedbacks;
    }

    /**
     * Replaces the current collection of department feedbacks with the provided
     * collection.
     * 
     * @param departmentFeedbacks the collection of department feedbacks to be
     *                            replaced with.
     */
    public void setDepartmentFeedbacks(Collection<DepartmentFeedback> departmentFeedbacks) {

        this.departmentFeedbacks.clear();
        this.departmentFeedbacks.addAll(departmentFeedbacks);
    }

    /**
     * Builder class for creating instances of {@link Issue}.
     * 
     * Example usage:
     * {@code
     * Issue issue = new Issue.Builder()
     *     .setIssueDescription("My car is broken!")
     *     .setStatus(IssueStatus.NEW)
     *     .build();
     * }
     */
    public static class Builder {
        private Integer id;
        private IssueStatus status;
        private String docNumber;
        private Date docDate;
        private Client client;
        private IssueType type;
        private Subject subject;
        private List<IssueAttribute> issueAttributes = new ArrayList<>();
        private String issueDescription;
        private List<String> relatedDocFromSigma = new ArrayList<>();
        private Set<Employee> issuedEmployees = new HashSet<>();
        private String issuedDemands;
        private List<AdditionalAttribute> additionalAttributes = new ArrayList<>();
        private Set<DepartmentFeedback> departmentFeedbacks = new HashSet<>();
        private String issueResult;
        private List<AttachedFile> attachedFiles = new ArrayList<>();
        private Chat chat;

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setStatus(IssueStatus status) {
            this.status = status;
            return this;
        }

        public Builder setDocNumber(String docNumber) {
            this.docNumber = docNumber;
            return this;
        }

        public Builder setDocDate(Date docDate) {
            this.docDate = docDate;
            return this;
        }

        public Builder setClient(Client client) {
            this.client = client;
            return this;
        }

        public Builder setIssueType(IssueType type) {
            this.type = type;
            return this;
        }

        public Builder setSubject(Subject subject) {
            this.subject = subject;
            return this;
        }

        public Builder setIssueAttributes(Collection<IssueAttribute> issueAttributes) {
            this.issueAttributes.clear();
            this.issueAttributes.addAll(issueAttributes);
            return this;
        }

        public Builder setIssueDescription(String issueDescription) {
            this.issueDescription = issueDescription;
            return this;
        }

        public Builder setRelatedDocFromSigma(Collection<String> relatedDocFromSigma) {
            this.relatedDocFromSigma.clear();
            this.relatedDocFromSigma.addAll(relatedDocFromSigma);
            return this;
        }

        public Builder setIssuedEmployees(Collection<Employee> issuedEmployees) {
            this.issuedEmployees.clear();
            this.issuedEmployees.addAll(issuedEmployees);
            return this;
        }

        public Builder setIssuedDemands(String issuedDemands) {
            this.issuedDemands = issuedDemands;
            return this;
        }

        public Builder setAdditionalAttributes(Collection<AdditionalAttribute> additionalAttributes) {
            this.additionalAttributes.clear();
            this.additionalAttributes.addAll(additionalAttributes);
            return this;
        }

        public Builder setDepartmentFeedbacks(Collection<DepartmentFeedback> departmentFeedbacks) {
            this.departmentFeedbacks.clear();
            this.departmentFeedbacks.addAll(departmentFeedbacks);
            return this;
        }

        public Builder setIssueResult(String issueResult) {
            this.issueResult = issueResult;
            return this;
        }

        public Builder setAttachedFiles(Collection<AttachedFile> attachedFiles) {
            this.attachedFiles.clear();
            this.attachedFiles.addAll(attachedFiles);
            return this;
        }

        public Builder setChat(Chat chat) {
            this.chat = chat;
            return this;
        }

        public Issue build() {
            return new Issue(id, status, docNumber, docDate, client, type, subject, issueAttributes,
                    issueDescription, relatedDocFromSigma, issuedEmployees, issuedDemands,
                    additionalAttributes, departmentFeedbacks, issueResult, attachedFiles, chat);
        }
    }

}

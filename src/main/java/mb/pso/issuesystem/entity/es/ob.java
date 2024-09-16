package mb.pso.issuesystem.entity.es;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.elasticsearch.annotations.Document;

import co.elastic.clients.util.DateTime;
import jakarta.persistence.Id;
import mb.pso.issuesystem.entity.Department;

@Document(indexName = "test")
public class ob {
    @Id
    Integer id;
    String testString;
    Boolean testBool;
    Integer testInteger;
    Department department;
    Date testDate;
    LocalDateTime testLocalDateTime;
    DateTime testDateTime;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    public Boolean getTestBool() {
        return testBool;
    }

    public void setTestBool(Boolean testBool) {
        this.testBool = testBool;
    }

    public Integer getTestInteger() {
        return testInteger;
    }

    public void setTestInteger(Integer testInteger) {
        this.testInteger = testInteger;
    }

    public ob() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public LocalDateTime getTestLocalDateTime() {
        return testLocalDateTime;
    }

    public void setTestLocalDateTime(LocalDateTime testLocalDateTime) {
        this.testLocalDateTime = testLocalDateTime;
    }

    public DateTime getTestDateTime() {
        return testDateTime;
    }

    public void setTestDateTime(DateTime testDateTime) {
        this.testDateTime = testDateTime;
    }

}

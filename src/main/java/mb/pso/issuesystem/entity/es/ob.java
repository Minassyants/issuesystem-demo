package mb.pso.issuesystem.entity.es;

import org.springframework.data.elasticsearch.annotations.Document;

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

}

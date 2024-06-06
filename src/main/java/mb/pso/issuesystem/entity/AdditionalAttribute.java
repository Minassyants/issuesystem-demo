package mb.pso.issuesystem.entity;

import org.springframework.data.annotation.Id;

import com.arangodb.springframework.annotation.ArangoId;
import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.Ref;

@Document("additionalAttribute")
public class AdditionalAttribute {
    @Id
    private String id;
    @ArangoId
    private String arangoId;
    @Ref
    private AdditionalAttributeType type;
    private String value;

    @Override
    public String toString() {
        return "AdditionalAttribute [id=" + id + ", arangoId=" + arangoId + ", type=" + type + ", value=" + value + "]";
    }

    public AdditionalAttribute() {
    }

    public AdditionalAttribute(AdditionalAttributeType type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArangoId() {
        return arangoId;
    }

    public void setArangoId(String arangoId) {
        this.arangoId = arangoId;
    }

    public AdditionalAttributeType getType() {
        return type;
    }

    public void setType(AdditionalAttributeType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

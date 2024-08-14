package mb.pso.issuesystem.entity;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class AdditionalAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @ManyToOne(cascade = CascadeType.ALL)
    private AdditionalAttributeType type;
    private String stringValue;

    @Override
    public String toString() {
        return "AdditionalAttribute [id=" + id + ", type=" + type + ", value=" + stringValue + "]";
    }

    public AdditionalAttribute() {
    }

    public AdditionalAttribute(AdditionalAttributeType type, String value) {
        this.type = type;
        this.stringValue = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AdditionalAttributeType getType() {
        return type;
    }

    public void setType(AdditionalAttributeType type) {
        this.type = type;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String value) {
        this.stringValue = value;
    }

}

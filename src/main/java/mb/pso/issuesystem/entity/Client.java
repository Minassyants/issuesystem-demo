package mb.pso.issuesystem.entity;

import java.util.Objects;

import org.springframework.data.annotation.Id;

import com.arangodb.springframework.annotation.ArangoId;
import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.PersistentIndexed;

@Document("client")
public class Client {
    @Id
    private String id;
    @ArangoId
    private String arangoId;
    @PersistentIndexed(deduplicate = true, unique = true)
    private String name;
    private String address;
    private String phoneNumber;
    @PersistentIndexed(deduplicate = true, unique = true)
    private String email;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        final Client other = (Client) obj;
        if (Objects.equals(this.name, other.name) & (Objects.equals(this.email, other.email)))
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "Client [id=" + id + ", arangoId=" + arangoId + ", name=" + name + ", address=" + address
                + ", phoneNumber=" + phoneNumber + ", email=" + email + "]";
    }

    public Client() {
    }

    public Client(String name, String address, String phoneNumber, String email) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getArangoId() {
        return arangoId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setArangoId(String arangoId) {
        this.arangoId = arangoId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

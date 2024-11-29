package mb.pso.issuesystem.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//[x] REFACTOR
/**
 * Represents a client who raised the issue.
 * <p>
 * This class contains important information such as name, address, phone number, email.
 * </p>
 */
@Entity
public class Client {

    /**
     * Unique ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * Name of the client (e.g. Ivanov Ivan Ivanovich).
     */
    private String name;

    /**
     * Physical address of the client.
     */
    private String address;

    /**
     * Phone number of the client.
     * <p>
     * The phone number must be unique across all clients to persist in the
     * database.
     * This constraint ensures that no two clients can have the same phone number.
     * </p>
     */
    @Column(unique = true)
    private String phoneNumber;

    /**
     * Email of the client.
     */
    private String email;

    public Client() {
    }

    public Client(String name, String address, String phoneNumber, String email) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}

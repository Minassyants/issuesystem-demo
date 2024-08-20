package mb.pso.issuesystem.entity;

import jakarta.persistence.Entity;

@Entity
public class Vehicle extends Subject {
    private String vin;

    

    public Vehicle() {
        super();
    }

    public Vehicle(String description, String vin) {
        super(description);
        this.vin = vin;
    }

    @Override
    public String toString() {
        return "Vehicle [vin=" + vin + ", getId()=" + getId()
                + ", getDescription()=" + getDescription() + "]";
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

}

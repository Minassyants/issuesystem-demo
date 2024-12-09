package mb.pso.issuesystem.entity.core;

import jakarta.persistence.Entity;


/**
 * {@inheritDoc}
 * <p>
 * This class extends Subject and contains information about VIN code.
 * </p>
 */
@Entity
public class Vehicle extends Subject {

    /**
     * VIN code of the vehicle.
     */
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

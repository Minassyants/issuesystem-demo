package mb.pso.issuesystem.entity.core;

import jakarta.persistence.Entity;

@Entity
public class Good extends Subject {

    public Good() {
        super();
    }

    public Good(String description) {
        super(description);

    }

    @Override
    public String toString() {
        return "Good [getId()=" + getId()
                + ", getDescription()=" + getDescription() + "]";
    }

}

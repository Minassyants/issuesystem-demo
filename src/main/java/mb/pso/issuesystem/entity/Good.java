package mb.pso.issuesystem.entity;

public class Good extends Subject {

    public Good() {
        super();
    }

    public Good(String description) {
        super(description);

    }

    @Override
    public String toString() {
        return "Good [getId()=" + getId() + ", getArangoId()=" + getArangoId()
                + ", getDescription()=" + getDescription() + "]";
    }

}

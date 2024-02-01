package models;

import java.io.Serializable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents enrollment information in the system.
 *
 * @author irati
 */
@XmlRootElement
public class Enrolled implements Serializable {

    private final SimpleObjectProperty<EnrolledId> id;
    private final SimpleBooleanProperty isMatriculate;

    /**
     * Default constructor for creating an Enrolled object with default values.
     */
    public Enrolled() {
        this.id = new SimpleObjectProperty();
        this.isMatriculate = new SimpleBooleanProperty();
    }

    // ... other constructors and methods
    /**
     * Gets the unique identifier for enrollment.
     *
     * @return The unique identifier for enrollment.
     */
    @XmlElement
    public EnrolledId getId() {
        return id.get();
    }

    /**
     * Sets the unique identifier for enrollment.
     *
     * @param id The unique identifier to set for enrollment.
     */
    public void setId(EnrolledId id) {
        this.id.set(id);
    }

    /**
     * Gets the matriculation status of the enrollment.
     *
     * @return True if the student is matriculated, false otherwise.
     */
    @XmlElement
    public Boolean getIsMatriculate() {
        return this.isMatriculate.get();
    }

    /**
     * Sets the matriculation status of the enrollment.
     *
     * @param isMatriculate The matriculation status to set.
     */
    public void setIsMatriculate(Boolean isMatriculate) {
        this.isMatriculate.set(isMatriculate);
    }
}

package models;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.ObservableSet;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a student in the system.
 *
 * @author irati
 */
@XmlRootElement
public class Student extends User {

    private final SimpleObjectProperty<LevelType> levelType;
    private final SimpleSetProperty<Enrolled> enrollments;

    /**
     * Default constructor for creating a Student object with default values.
     */
    public Student() {
        super();
        this.levelType = new SimpleObjectProperty();
        this.enrollments = new SimpleSetProperty();
    }

    /**
     * Parameterized constructor for creating a Student object with specific
     * attributes.
     *
     * @param levelType The level type of the student.
     * @param enrollments The set of enrollments associated with the student.
     */
    public Student(LevelType levelType, ObservableSet<Subject> enrollments) {
        super();
        this.levelType = new SimpleObjectProperty(levelType);
        this.enrollments = new SimpleSetProperty(enrollments);
    }

    /**
     * Gets the level type of the student.
     *
     * @return The level type of the student.
     */
    public LevelType getLevelType() {
        return this.levelType.get();
    }

    /**
     * Sets the level type of the student.
     *
     * @param levelType The level type to set for the student.
     */
    public void setLevelType(LevelType levelType) {
        this.levelType.set(levelType);
    }

    /**
     * Gets the set of enrollments associated with the student.
     *
     * @return The set of enrollments associated with the student.
     */
    public ObservableSet<Enrolled> getEnrollments() {
        return this.enrollments.get();
    }

    /**
     * Sets the set of enrollments associated with the student.
     *
     * @param enrollments The set of enrollments to be associated with the
     * student.
     */
    public void setEnrollments(ObservableSet<Enrolled> enrollments) {
        this.enrollments.set(enrollments);
    }
}

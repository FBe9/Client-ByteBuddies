package models;

import java.io.Serializable;
import javafx.beans.property.SimpleIntegerProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a composite key for the Enrolled class.
 * 
 * @author irati
 */
@XmlRootElement
public class EnrolledId implements Serializable {

    private final SimpleIntegerProperty studentId;
    private final SimpleIntegerProperty subjectId;

    /**
     * Default constructor for creating an EnrolledId object with default values.
     */
    public EnrolledId() {
        this.studentId = new SimpleIntegerProperty();
        this.subjectId = new SimpleIntegerProperty();
    }

    /**
     * Parameterized constructor for creating an EnrolledId object with specific
     * studentId and subjectId values.
     *
     * @param studentId The identifier of the student.
     * @param subjectId The identifier of the subject.
     */
    public EnrolledId(Integer studentId, Integer subjectId) {
        this.studentId = new SimpleIntegerProperty(studentId);
        this.subjectId = new SimpleIntegerProperty(subjectId);
    }

    /**
     * Gets the identifier of the student.
     *
     * @return The identifier of the student.
     */
    public Integer getStudentId() {
        return this.studentId.get();
    }

    /**
     * Sets the identifier of the student.
     *
     * @param studentId The identifier to set for the student.
     */
    public void setStudentId(Integer studentId) {
        this.studentId.set(studentId);
    }

    /**
     * Gets the identifier of the subject.
     *
     * @return The identifier of the subject.
     */
    public Integer getSubjectId() {
        return this.subjectId.get();
    }

    /**
     * Sets the identifier of the subject.
     *
     * @param subjectId The identifier to set for the subject.
     */
    public void setSubjectId(Integer subjectId) {
        this.subjectId.set(subjectId);
    }
}

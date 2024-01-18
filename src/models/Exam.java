package models;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Entity representing the exams. It has the following fields: exam id,
 * description, date, duration, file path, subject, marks.
 *
 * @author Alex
 */
public class Exam implements Serializable {

    /**
     * Identification (id) for the exam.
     */
    private final SimpleIntegerProperty id;

    /**
     * The description, title or name.
     */
    private final SimpleStringProperty description;

    /**
     * The enum that marks the call of the exam, First, Second, Third...
     */
    private final SimpleObjectProperty<CallType> callType;

    /**
     * The date when the exam is programmed.
     */
    private final SimpleObjectProperty<Date> dateInit;

    /**
     * The duration value of the exam, in minutes.
     */
    private final SimpleIntegerProperty duration;

    /**
     * The path to store the exam heading.
     */
    private final SimpleStringProperty filePath;

    /**
     * The subject which the exam belongs to.
     */
    private final SimpleObjectProperty<Subject> subject;

    /**
     * Relational field for the grades or marks assigned to an exam.
     */
    private final SimpleSetProperty<Mark> marks;

    /**
     * Gets the exam id.
     *
     * @return Integer value of the id field.
     */
    public Integer getId() {
        return this.id.get();
    }

    /**
     * Sets the exam id
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id.set(id);
    }

    /**
     * Gets the name of the exam.
     *
     * @return String value of the field description.
     */
    public String getDescription() {
        return this.description.get();
    }

    /**
     * Sets the name of the exam.
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description.set(description);
    }

    /**
     * Gets the date of the exam.
     *
     * @return Date value of the dateInit field.
     */
    public Date getDateInit() {
        return this.dateInit.get();
    }

    /**
     * Sets the date of the exam.
     *
     * @param dateInit
     */
    public void setDateInit(Date dateInit) {
        this.dateInit.set(dateInit);
    }

    /**
     * Gets the duration of the exam (minutes).
     *
     * @return Integer value (in minutes) of the duration field.
     */
    public Integer getDuration() {
        return this.duration.get();
    }

    /**
     * Sets the duration of the exam (in minutes).
     *
     * @param duration
     */
    public void setDuration(Integer duration) {
        this.duration.set(duration);
    }

    /**
     * Gets the path where the exam heading file is stored.
     *
     * @return String value of the filePath field.
     */
    public String getFilePath() {
        return this.filePath.get();
    }

    /**
     * Sets the path to where the exam heading is stored.
     *
     * @param filePath
     */
    public void setFilePath(String filePath) {
        this.filePath.set(filePath);
    }

    /**
     * Gets the subject the exam belongs to.
     *
     * @return Subject object of the subject field.
     */
    public Subject getSubject() {
        return this.subject.get();
    }

    /**
     * Sets the subject the exam belongs to.
     *
     * @param subject
     */
    public void setSubject(Subject subject) {
        this.subject.set(subject);
    }
    
    /**
     * Gets the call type of the exam.
     *
     * @return The string value of the call type.
     */
    public CallType getCallType() {
        return this.callType.get();
    }

    /**
     * Sets the call type.
     *
     * @param callType
     */
    public void setCallType(CallType callType) {
        this.callType.set(callType);
    }

    /**
     * Gets the marks related to the exam.
     *
     * @return Set list of the type Mark.
     */
    public Set<Mark> getMarks() {
        return this.marks.get();
    }

    /**
     * Sets the marks collection with the marks related to the exam.
     *
     * @param marks
     */
    public void setMarks(Set<Mark> marks) {
        this.marks.set(marks);
    }

    /**
     * Constructor with parameters for the Exam entity.
     *
     * @param id
     * @param description
     * @param dateInit
     * @param duration
     * @param filePath
     * @param subject
     * @param marks
     */
    public Exam(SimpleIntegerProperty id, SimpleStringProperty description, SimpleObjectProperty<CallType> callType, SimpleObjectProperty<Date> dateInit, SimpleIntegerProperty duration, SimpleStringProperty filePath, SimpleObjectProperty<Subject> subject, <any> marks) {
        this.id = id;
        this.description = description;
        this.callType = callType;
        this.dateInit = dateInit;
        this.duration = duration;
        this.filePath = filePath;
        this.subject = subject;
        this.marks = marks;
    }

    /**
     * Computes the hash code for the exam.
     *
     * @return int value of the hash code.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /**
     * Checks if this exam is equal to any other exam.
     *
     * @param obj
     * @return boolean value, true if it's equal to any other exam, false if
     * opposite.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Exam other = (Exam) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    /**
     * String representation of the exam entity.
     *
     * @return the string value of the representation of the entity.
     */
    @Override
    public String toString() {
        return "Exam{" + "id=" + id + ", description=" + description + ", dateInit=" + dateInit + ", duration=" + duration + ", filePath=" + filePath + ", subject=" + subject + ", marks=" + marks + '}';
    }

}

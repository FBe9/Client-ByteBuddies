package models;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity representing the exams. It has the following fields: exam id,
 * description, date, duration, file path, subject.
 *
 * @author Alex
 */
@XmlRootElement
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
    private final SimpleStringProperty duration;

    /**
     * The path to store the exam heading.
     */
    private final SimpleStringProperty filePath;

    /**
     * The subject which the exam belongs to.
     */
    private final SimpleObjectProperty<Subject> subject;
    
    /**
     * Default empty constructor for the entity Exam.
     */
    public Exam() {
        this.id = new SimpleIntegerProperty();
        this.description = new SimpleStringProperty();
        this.callType = new SimpleObjectProperty<>();
        this.dateInit = new SimpleObjectProperty<>();
        this.duration = new SimpleStringProperty();
        this.filePath = new SimpleStringProperty();
        this.subject = new SimpleObjectProperty<>();
    }
    
    /**
     * Constructor with parameters for the Exam entity.
     *
     * @param id
     * @param description
     * @param callType
     * @param dateInit
     * @param duration
     * @param filePath
     * @param subject
     */
    public Exam(Integer id, String description, CallType callType, Date dateInit, String duration, String filePath, Subject subject) {
        this.id = new SimpleIntegerProperty(id);
        this.description = new SimpleStringProperty(description);
        this.callType = new SimpleObjectProperty(callType);
        this.dateInit = new SimpleObjectProperty(dateInit);
        this.duration = new SimpleStringProperty(duration);
        this.filePath = new SimpleStringProperty(filePath);
        this.subject = new SimpleObjectProperty(subject);
    }

    public Exam(String description, Date dateInit, String duration, String filePath, Subject subject) {
        this.id = null;
        this.callType = null;
        this.description = new SimpleStringProperty(description);
        this.dateInit = new SimpleObjectProperty(dateInit);
        this.duration = new SimpleStringProperty(duration);
        this.filePath = new SimpleStringProperty(filePath);
        this.subject = new SimpleObjectProperty(subject);
    }
    
    

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
    public String getDuration() {
        return this.duration.get();
    }

    /**
     * Sets the duration of the exam (in minutes).
     *
     * @param duration
     */
    public void setDuration(String duration) {
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
    @XmlElement(name = "subject")
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
     * Computes the hash code for the exam.
     *
     * @return int value of the hash code.
     */
    @Override    
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.id);
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
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.callType, other.callType)) {
            return false;
        }
        if (!Objects.equals(this.dateInit, other.dateInit)) {
            return false;
        }
        if (!Objects.equals(this.duration, other.duration)) {
            return false;
        }
        if (!Objects.equals(this.filePath, other.filePath)) {
            return false;
        }
        if (!Objects.equals(this.subject, other.subject)) {
            return false;
        }
        return true;
    }
}
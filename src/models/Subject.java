package models;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a subject in the system.
 *
 * @author irati
 */
@XmlRootElement
public class Subject implements Serializable {

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty hours;
    private SimpleObjectProperty<LevelType> levelType;
    private final SimpleObjectProperty<LanguageType> languageType;
    private final SimpleObjectProperty<Date> dateInit;
    private final SimpleObjectProperty<Date> dateEnd;
    private final SimpleIntegerProperty studentsCount;
    private final SetProperty<Teacher> teachers;
    private final SetProperty<Enrolled> enrollments;
    private final SimpleBooleanProperty status;

    /**
     * Default constructor for creating a SubjectBean object with default
     * values.
     */
    public Subject() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.hours = new SimpleStringProperty();
        this.levelType = new SimpleObjectProperty<>();
        this.languageType = new SimpleObjectProperty<>();
        this.dateInit = new SimpleObjectProperty<>();
        this.dateEnd = new SimpleObjectProperty<>();
        this.teachers = new SimpleSetProperty<>(FXCollections.observableSet());
        this.enrollments = new SimpleSetProperty<>(FXCollections.observableSet());
        this.status = new SimpleBooleanProperty();
        this.studentsCount = new SimpleIntegerProperty();
    }

    /**
     * Parameterized constructor for creating a SubjectBean object with specific
     * attributes.
     *
     * @param id The unique identifier for the subject.
     * @param name The name of the subject.
     * @param hours The duration of the subject in hours.
     * @param levelType The level type of the subject.
     * @param languageType The language type of the subject.
     * @param dateInit The start date of the subject.
     * @param dateEnd The end date of the subject.
     * @param teachers The set of teachers associated with the subject.
     * @param enrollments The set of enrollments associated with the subject.
     * @param status The status of the subject.
     * @param studentsCount The number of students enrolled in the subject.
     */
    public Subject(Integer id, String name, String hours, LevelType levelType, LanguageType languageType, Date dateInit, Date dateEnd, ObservableSet<Teacher> teachers, ObservableSet<Enrolled> enrollments, Boolean status, Integer studentsCount) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.hours = new SimpleStringProperty(hours);
        this.levelType = new SimpleObjectProperty(levelType);
        this.languageType = new SimpleObjectProperty(languageType);
        this.dateInit = new SimpleObjectProperty(dateInit);
        this.dateEnd = new SimpleObjectProperty(dateEnd);
        this.teachers = new SimpleSetProperty(teachers);
        this.enrollments = new SimpleSetProperty(enrollments);
        this.status = new SimpleBooleanProperty(status);
        this.studentsCount = new SimpleIntegerProperty(studentsCount);
    }

    /**
     * Gets the unique identifier of the subject.
     *
     * @return The subject's unique identifier.
     */
    public Integer getId() {
        return this.id.get();
    }

    /**
     * Sets the unique identifier of the subject.
     *
     * @param id The unique identifier to be set.
     */
    public void setId(Integer id) {
        this.id.set(id);
    }

    /**
     * Gets the name of the subject.
     *
     * @return The name of the subject.
     */
    public String getName() {
        return this.name.get();
    }

    /**
     * Sets the name of the subject.
     *
     * @param name The name to be set.
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Gets the duration of the subject in hours.
     *
     * @return The duration of the subject in hours.
     */
    public String getHours() {
        return this.hours.get();
    }

    /**
     * Sets the duration of the subject in hours.
     *
     * @param hours The duration to be set.
     */
    public void setHours(String hours) {
        this.hours.set(hours);
    }

    /**
     * Gets the level type of the subject.
     *
     * @return The level type of the subject.
     */
    public LevelType getLevelType() {
        return this.levelType.get();
    }

    /**
     * Sets the level type of the subject.
     *
     * @param levelType The level type to be set.
     */
    public void setLevelType(LevelType levelType) {
        this.levelType.set(levelType);
    }

    /**
     * Gets the level type of the subject.
     *
     * @return The level type of the subject.
     */
    @XmlElement(name = "languageType")
    public LanguageType getLanguageType() {
        return this.languageType.get();
    }

    /**
     * Sets the language type of the subject.
     *
     * @param languageType The language type to be set.
     */
    public void setLanguageType(LanguageType languageType) {
        this.languageType.set(languageType);
    }

    /**
     * Gets the start date of the subject.
     *
     * @return The start date of the subject.
     */
    public Date getDateInit() {
        return this.dateInit.get();
    }

    /**
     * Sets the start date of the subject.
     *
     * @param dateInit The start date to be set.
     */
    public void setDateInit(Date dateInit) {
        this.dateInit.set(dateInit);
    }

    /**
     * Gets the end date of the subject.
     *
     * @return The end date of the subject.
     */
    public Date getDateEnd() {
        return this.dateEnd.get();
    }

    /**
     * Sets the end date of the subject.
     *
     * @param dateEnd The end date to be set.
     */
    public void setDateEnd(Date dateEnd) {
        this.dateEnd.set(dateEnd);
    }

    /**
     * Gets the set of teachers associated with the subject.
     *
     * @return The set of teachers.
     */
    @XmlElementWrapper(name = "teachers")
    @XmlElement(name = "teacher")
    public ObservableSet<Teacher> getTeachers() {
        return FXCollections.observableSet(teachers);
    }

    /**
     * Sets the set of teachers associated with the subject.
     *
     * @param teachers The set of teachers to be set.
     */
    public void setTeachers(ObservableSet<Teacher> teachers) {
        this.teachers.addAll(teachers);
    }

    /**
     * Gets the set of enrollments associated with the subject.
     *
     * @return The set of enrollments.
     */
    public ObservableSet<Enrolled> getEnrollments() {
        return FXCollections.observableSet(enrollments);
    }

    /**
     * Sets the set of enrollments associated with the subject.
     *
     * @param enrollments The set of enrollments to be set.
     */
    public void setEnrollments(ObservableSet<Enrolled> enrollments) {
        this.enrollments.addAll(enrollments);
    }

    /**
     * Gets the status of the subject.
     *
     * @return The status of the subject.
     */
    public Boolean getStatus() {
        return this.status.get();
    }

    /**
     * Sets the status of the subject.
     *
     * @param status The status to be set.
     */
    public void setStatus(Boolean status) {
        this.status.set(status);
    }

    /**
     * Gets the property representing the status of the subject.
     *
     * @return The property representing the status.
     */
    public SimpleBooleanProperty statusProperty() {
        return this.status;
    }

    /**
     * Gets the number of students enrolled in the subject.
     *
     * @return The number of students.
     */
    public Integer getStudentsCount() {
        return this.studentsCount.get();
    }

    /**
     * Sets the number of students enrolled in the subject.
     *
     * @param studentsCount The number of students to be set.
     */
    public void setStudentsCount(Integer studentsCount) {
        this.studentsCount.set(studentsCount);
    }

    /**
     * Generates a hash code for the subject based on its unique identifier.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Checks if the given object is equal to this subject. Two subjects are
     * considered equal if they have the same unique identifier.
     *
     * @param obj The object to be compared.
     * @return True if the objects are equal, false otherwise.
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
        final Subject other = (Subject) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.hours, other.hours)) {
            return false;
        }
        if (!Objects.equals(this.levelType, other.levelType)) {
            return false;
        }
        if (!Objects.equals(this.languageType, other.languageType)) {
            return false;
        }
        if (!Objects.equals(this.dateInit, other.dateInit)) {
            return false;
        }
        if (!Objects.equals(this.dateEnd, other.dateEnd)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of the subject.
     *
     * @return The name of the subject.
     */
    @Override
    public String toString() {
        return name.getValue();
    }
}

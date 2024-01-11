package models;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

/**
 *
 * @author irati
 */
/**
 * Represents a subject in the system. This class is a JavaBean class that holds
 * information about a subject, such as its unique identifier, name, duration in
 * hours, level type, language type, start date, and end date. The class
 * provides getters and setters for accessing and modifying these attributes.
 *
 * @author irati
 */
public class Subject implements Serializable {

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleIntegerProperty hours;
    private SimpleObjectProperty<LevelType> levelType;
    private final SimpleObjectProperty<LanguageType> languageType;
    private final SimpleObjectProperty<Date> dateInit;
    private final SimpleObjectProperty<Date> dateEnd;
    private final SimpleSetProperty<Teacher> teachers;
    // private final SimpleSetProperty<Exam> exams;
    private final SimpleSetProperty<Unit> units;
    private final SimpleSetProperty<Enrolled> enrollments;

    /**
     * Default constructor for creating a SubjectBean object. Initializes all
     * properties to their default values.
     */
    public Subject() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.hours = new SimpleIntegerProperty();
        this.levelType = new SimpleObjectProperty();
        this.languageType = new SimpleObjectProperty();
        this.dateInit = new SimpleObjectProperty();
        this.dateEnd = new SimpleObjectProperty();
        this.teachers = new SimpleSetProperty();
        //this.exams = new SimpleSetProperty();
        this.units = new SimpleSetProperty();
        this.enrollments = new SimpleSetProperty();
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
     */
    public Subject(Integer id, String name, Integer hours, LevelType levelType, LanguageType languageType, Date dateInit, Date dateEnd, ObservableSet<Teacher> teachers, ObservableSet<Exam> exams, ObservableSet<Unit> units, ObservableSet<Enrolled> enrollments) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.hours = new SimpleIntegerProperty(hours);
        this.levelType = new SimpleObjectProperty(levelType);
        this.languageType = new SimpleObjectProperty(languageType);
        this.dateInit = new SimpleObjectProperty(dateInit);
        this.dateEnd = new SimpleObjectProperty(dateEnd);
        this.teachers = new SimpleSetProperty(teachers);
        // this.exams = new SimpleSetProperty(exams);
        this.units = new SimpleSetProperty(units);
        this.enrollments = new SimpleSetProperty(enrollments);
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
    public Integer getHours() {
        return this.hours.get();
    }

    /**
     * Sets the duration of the subject in hours.
     *
     * @param hours The duration to be set.
     */
    public void setHours(Integer hours) {
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

    public ObservableSet<Teacher> getTeachers() {
        return this.teachers.get();
    }

    public void setTeachers(ObservableSet<Teacher> teachers) {
        this.teachers.set(teachers);
    }

    /*  public ObservableSet<Exam> getExams() {
        return this.exams.get();
    }

    public void setExams(ObservableSet<Exam> exams) {
        this.exams.set(exams);
    } */
    public ObservableSet<Unit> getUnits() {
        return this.units.get();
    }

    public void setUnits(ObservableSet<Unit> units) {
        this.units.set(units);
    }

    public ObservableSet<Enrolled> getEnrollments() {
        return this.enrollments.get();
    }

    public void setEnrollments(ObservableSet<Enrolled> enrollments) {
        this.enrollments.set(enrollments);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

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
        if (!Objects.equals(this.teachers, other.teachers)) {
            return false;
        }
/*        if (!Objects.equals(this.exams, other.exams)) {
            return false;
        } */
        if (!Objects.equals(this.units, other.units)) {
            return false;
        }
        if (!Objects.equals(this.enrollments, other.enrollments)) {
            return false;
        }
        return true;
    }

}

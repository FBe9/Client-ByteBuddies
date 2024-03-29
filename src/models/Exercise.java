package models;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity representing exercise. It contains the following fields: exercise id,
 * exercise unit, exercise number, exercise description, exercise level type,
 * exercise file, exercise file solution, exercise deadline, ´exercise hours.
 *
 * @author Leire
 */
@XmlRootElement
public class Exercise implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Auto generated exercise id
     */
    private Integer id;

    /**
     * Unit exercise
     */
    private Unit unit;

    /**
     * Exercise number
     */
    private String number;

    /**
     * Exercise description
     */
    private String description;

    /**
     * Enumeration for the level of the exercise BEGGINER MEDIUM EXPERIENCED
     */
    private LevelType levelType;

    /**
     * This is the file with the exercise
     */
    private String file;

    /**
     * This is the file with the exercise solution
     */
    private String fileSolution;

    /**
     * Date of the deadline
     */
    private Date deadline;

    /**
     * The hours that the exercise takes
     */
    private String hours;

    //Costructors
    /**
     * Empty constructor
     */
    public Exercise() {
    }

    /**
     * Constructor with parameters
     *
     * @param id The id.
     * @param unit The unit.
     * @param number The number.
     * @param description The description.
     * @param levelType The level type.
     * @param file The file.
     * @param fileSolution The solution.
     * @param deadline The deadline date.
     * @param hours The hours.
     */
    public Exercise(Integer id, Unit unit, String number, String description, LevelType levelType, String file, String fileSolution, Date deadline, String hours) {
        this.id = id;
        this.unit = unit;
        this.number = number;
        this.description = description;
        this.levelType = levelType;
        this.file = file;
        this.fileSolution = fileSolution;
        this.deadline = deadline;
        this.hours = hours;
    }

    //Getters and Setters
    /**
     * Gets the id
     *
     * @return The id of the exercise.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id
     *
     * @param id The id of the exercise,
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the unit
     *
     * @return The unit.
     */
    public Unit getUnit() {
        return unit;
    }

    /**
     * Sets the unit
     *
     * @param unit The unit.
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    /**
     * Gets the number
     *
     * @return The number of exercise.
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the number
     *
     * @param number The of exercise.
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Gets the description
     *
     * @return The description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description
     *
     * @param description The description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the level type
     *
     * @return The Level Type.
     */
    public LevelType getLevelType() {
        return levelType;
    }

    /**
     * Sets the level type
     *
     * @param levelType The Level Type.
     */
    public void setLevelType(LevelType levelType) {
        this.levelType = levelType;
    }

    /**
     * Gets the file
     *
     * @return The file.
     */
    public String getFile() {
        return file;
    }

    /**
     * Sets the file
     *
     * @param file The file.
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * Gets the file solution
     *
     * @return The solution.
     */
    public String getFileSolution() {
        return fileSolution;
    }

    /**
     * Sets the file solution
     *
     * @param fileSolution The solution.
     */
    public void setFileSolution(String fileSolution) {
        this.fileSolution = fileSolution;
    }

    /**
     * Gets the deadline
     *
     * @return The deadline date.
     */
    public Date getDeadline() {
        return deadline;
    }

    /**
     * Sets the deadline
     *
     * @param deadline The deadline date.
     */
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    /**
     * Gets the hours
     *
     * @return The hours.
     */
    public String getHours() {
        return hours;
    }

    /**
     * Sets the hours
     *
     * @param hours The hours.
     */
    public void setHours(String hours) {
        this.hours = hours;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exercise)) {
            return false;
        }
        Exercise other = (Exercise) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }
}

package models;

import java.io.Serializable;
import java.util.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a user in the system.
 *
 * @author irati
 */
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private final SimpleStringProperty user_type;
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty email;
    private final SimpleStringProperty name;
    private final SimpleStringProperty surname;
    private final SimpleStringProperty password;
    private final SimpleObjectProperty<Date> dateInit;

    /**
     * Default constructor for creating a User object with default values.
     */
    public User() {
        this.user_type = new SimpleStringProperty();
        this.id = new SimpleIntegerProperty();
        this.email = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.surname = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.dateInit = new SimpleObjectProperty();
    }

    /**
     * Parameterized constructor for creating a User object with specific
     * attributes.
     *
     * @param user_type The type of the user.
     * @param id The unique identifier for the user.
     * @param email The email address of the user.
     * @param name The name of the user.
     * @param surname The surname of the user.
     * @param password The password of the user.
     * @param dateInit The registration date of the user.
     */
    public User(String user_type, Integer id, String email, String name, String surname, String password, Date dateInit) {
        this.user_type = new SimpleStringProperty(user_type);
        this.id = new SimpleIntegerProperty(id);
        this.email = new SimpleStringProperty(email);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.password = new SimpleStringProperty(password);
        this.dateInit = new SimpleObjectProperty(dateInit);
    }

    /**
     * Gets the unique identifier of the user.
     *
     * @return The user's unique identifier.
     */
    public Integer getId() {
        return this.id.get();
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param id The unique identifier to be set.
     */
    public void setId(Integer id) {
        this.id.set(id);
    }

    /**
     * Gets the email address of the user.
     *
     * @return The email address of the user.
     */
    public String getEmail() {
        return this.email.get();
    }

    /**
     * Sets the email address of the user.
     *
     * @param email The email address to be set.
     */
    public void setEmail(String email) {
        this.email.set(email);
    }

    /**
     * Gets the name of the user.
     *
     * @return The name of the user.
     */
    public String getName() {
        return this.name.get();
    }

    /**
     * Sets the name of the user.
     *
     * @param name The name to be set.
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Gets the surname of the user.
     *
     * @return The surname of the user.
     */
    public String getSurname() {
        return this.surname.get();
    }

    /**
     * Sets the surname of the user.
     *
     * @param surname The surname to be set.
     */
    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    /**
     * Gets the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return this.password.get();
    }

    /**
     * Sets the password of the user.
     *
     * @param password The password to be set.
     */
    public void setPassword(String password) {
        this.password.set(password);
    }

    /**
     * Gets the registration date of the user.
     *
     * @return The registration date of the user.
     */
    public Date getDateInit() {
        return this.dateInit.get();
    }

    /**
     * Sets the registration date of the user.
     *
     * @param dateInit The registration date to be set.
     */
    public void setDateInit(Date dateInit) {
        this.dateInit.set(dateInit);
    }

    /**
     * Gets the type of the user.
     *
     * @return The type of the user.
     */
    public String getUser_type() {
        return this.user_type.get();
    }

    /**
     * Sets the type of the user.
     *
     * @param user_type The type to be set.
     */
    public void setUser_type(String user_type) {
        this.user_type.set(user_type);
    }

    /**
     * Returns a string representation of the user.
     *
     * @return The name and surname of the user.
     */
    @Override
    public String toString() {
        return name.getValue() + " " + surname.getValue();
    }

}

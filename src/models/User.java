package models;

import java.io.Serializable;
import java.util.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
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

    public User() {
        this.user_type = new SimpleStringProperty();         
        this.id = new SimpleIntegerProperty();
        this.email = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.surname = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.dateInit = new SimpleObjectProperty();

    }

    public User(String user_type, Integer id, String email, String name, String surname, String password, Date dateInit) {
        this.user_type = new SimpleStringProperty(user_type);
        this.id = new SimpleIntegerProperty(id);
        this.email = new SimpleStringProperty(email);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.password = new SimpleStringProperty(password);
        this.dateInit = new SimpleObjectProperty(dateInit);
    }

    public Integer getId() {
        return this.id.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public String getEmail() {
        return this.email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return this.surname.get();
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getPassword() {
        return this.password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public Date getDateInit() {
        return this.dateInit.get();
    }

    public void setDateInit(Date dateInit) {
        this.dateInit.set(dateInit);
    }
     public String getUser_type() {
        return this.user_type.get();
    }

    public void setUser_type(String user_type) {
        this.user_type.set(user_type);
    }

    @Override
    public String toString() {
        return name.getValue() + " " +  surname.getValue();
    }

}

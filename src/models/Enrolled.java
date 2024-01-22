/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author irati
 */
@XmlRootElement
public class Enrolled implements Serializable {

    private final SimpleObjectProperty<EnrolledId> id;
    private final SimpleBooleanProperty isMatriculate;

    public Enrolled() {
        this.id = new SimpleObjectProperty();
        this.isMatriculate = new SimpleBooleanProperty();
    }

    // ... other constructors and methods
    @XmlElement
    public EnrolledId getId() {
        return id.get();
    }

    public void setId(EnrolledId id) {
        this.id.set(id);
    }

    @XmlElement
    public Boolean getIsMatriculate() {
        return this.isMatriculate.get();
    }

    public void setIsMatriculate(Boolean isMatriculate) {
        this.isMatriculate.set(isMatriculate);
    }


}

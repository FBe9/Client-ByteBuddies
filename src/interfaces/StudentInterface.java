/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import exceptions.CreateErrorException;
import exceptions.FindErrorException;
import java.util.Collection;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import models.Student;
import models.User;


/**
 *
 * @author irati
 */
public interface StudentInterface {
 /**
     * Retrieves a collection of students.
     *
     * @param student
     * @return A collection of students.
     * @throws FindErrorException If an error occurs during student retrieval.
     */
    public User find(User student) throws FindErrorException;

    /**
     * Retrieves an student .
     *
     * @return An student
     * @throws FindErrorException If an error occurs during student retrieval.
     */
    public Collection<Student> findAll() throws FindErrorException;
   
    public void createStudent (Student student) throws CreateErrorException;

  
}

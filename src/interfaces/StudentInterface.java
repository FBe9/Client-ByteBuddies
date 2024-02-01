package interfaces;

import exceptions.CreateErrorException;
import exceptions.FindErrorException;
import java.util.Collection;
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
   /**
    * Method to create a student
    * @param student the student to be created
    * @throws CreateErrorException If an error occurs during creation.
    */
    public void createStudent (Student student) throws CreateErrorException;

  
}

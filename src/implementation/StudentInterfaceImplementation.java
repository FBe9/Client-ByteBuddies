package implementation;

import exceptions.CreateErrorException;
import exceptions.FindErrorException;
import interfaces.StudentInterface;
import java.util.Collection;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import models.Student;
import models.User;
import services.StudentRESTClient;

/**
 * The implementation of StudentInterface
 *
 * @author irati
 */
public class StudentInterfaceImplementation implements StudentInterface {

    /**
     * The webClient for StudentRESTClient
     */
    private StudentRESTClient webClient;
    /**
     * Logger for the class
     */
    private static final Logger LOGGER = Logger.getLogger("StudentInterfaceImplementation");

    /**
     * Constructs a new StudentInterfaceImplementation instance.
     */
    public StudentInterfaceImplementation() {
        webClient = new StudentRESTClient();
    }

    /**
     * Finds a student based on the provided User object.
     *
     * @param student The User object representing the student to be found.
     * @return The found student as a User object.
     * @throws FindErrorException If an error occurs during the find operation.
     */
    @Override
    public User find(User student) throws FindErrorException {
        User studentSearch;
        try {
            LOGGER.info("Finding student with ID " + student.getId());
            studentSearch = webClient.find_XML(Student.class, student.getId().toString());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "StudentInterface: Error finding student - " + e.getMessage());
            throw new FindErrorException("Error finding the student");
        }
        return studentSearch;
    }

    /**
     * Finds all students.
     *
     * @return A Collection of Student objects representing all students.
     * @throws FindErrorException If an error occurs during the find operation.
     */
    @Override
    public Collection<Student> findAll() throws FindErrorException {
        Set<Student> students;
        try {
            LOGGER.info("Finding all students");
            students = webClient.findAll_XML(new GenericType<Set<Student>>() {
            });
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "StudentInterface: Error finding all students - " + e.getMessage());
            throw new FindErrorException("Error finding all students");
        }
        return students;
    }

    /**
     * Creates a new student.
     *
     * @param student The Student object to be created.
     * @throws CreateErrorException If an error occurs during the create
     * operation.
     */
    @Override
    public void createStudent(Student student) throws CreateErrorException {
        try {
            LOGGER.info("Creating a new student");
            webClient.createStudent_XML(student);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "StudentInterface: Error creating a student - " + e.getMessage());
            throw new CreateErrorException("Error creating a student");
        }
    }

}

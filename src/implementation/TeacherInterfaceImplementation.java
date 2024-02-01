package implementation;

import exceptions.FindErrorException;
import interfaces.TeacherInterface;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import models.Teacher;
import services.TeacherRESTClient;

/**
 * The implementation of TeacherInterface
 *
 * @author irati
 */
public class TeacherInterfaceImplementation implements TeacherInterface {

    /**
     * The webClient for TeacherRESTClient
     */
    private TeacherRESTClient webClient;
    /**
     * The logger for the class.
     */
    private static final Logger LOGGER = Logger.getLogger("TeacherInterfaceImplementation");

    /**
     * Constructs a new TeacherInterfaceImplementation instance.
     */
    public TeacherInterfaceImplementation() {
        webClient = new TeacherRESTClient();
    }

    /**
     * Finds all teachers.
     *
     * @return A Collection of Teacher objects representing all teachers.
     * @throws FindErrorException If an error occurs during the find operation.
     */
    @Override
    public Collection<Teacher> findAll() throws FindErrorException {
        List<Teacher> teachers;
        try {
            LOGGER.info("Finding all teachers");
            teachers = webClient.findAll_XML(new GenericType<List<Teacher>>() {
            });
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeacherInterface: Error finding all teachers - " + e.getMessage());
            throw new FindErrorException("Error finding all teachers");
        }
        return teachers;
    }
}

package implementation;

import exceptions.FindErrorException;
import interfaces.TeacherInterface;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import models.Teacher;
import models.User;
import services.TeacherRESTClient;


/**
 *
 * @author irati
 */
public class TeacherInterfaceImplementation implements TeacherInterface {

    private TeacherRESTClient webClient;
    private static final Logger LOGGER = Logger.getLogger("TeacherInterfaceImplementation");

    /**
     * Constructs a new TeacherInterfaceImplementation instance.
     */
    public TeacherInterfaceImplementation() {
        webClient = new TeacherRESTClient();
    }

    @Override
    public User find(User teacher) throws FindErrorException {
        User teacherSearch;
        try {
            LOGGER.info("Finding teacher with ID " + teacher.getId());
            teacherSearch = webClient.find_XML(Teacher.class, teacher.getId().toString());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeacherInterface: Error finding teacher - " + e.getMessage());
            throw new FindErrorException("Error finding the teacher");
        }
        return teacherSearch;
    }

    @Override
    public Collection<Teacher> findAll() throws FindErrorException {
        List<Teacher> teachers;
        try {
            LOGGER.info("Finding all teachers");
            teachers = webClient.findAll_XML(new GenericType<List<Teacher>>() {});
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeacherInterface: Error finding all teachers - " + e.getMessage());
            throw new FindErrorException("Error finding all teachers");
        }
        return teachers;
    }

}

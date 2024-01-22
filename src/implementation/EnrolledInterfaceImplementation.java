package implementation;

import exceptions.CreateErrorException;
import exceptions.DeleteErrorException;
import exceptions.FindErrorException;
import exceptions.UpdateErrorException;
import interfaces.EnrolledInterface;
import java.util.Collection;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import models.Enrolled;
import services.EnrolledRESTClient;

/**
 *
 * @author irati
 */
public class EnrolledInterfaceImplementation implements EnrolledInterface {

    private EnrolledRESTClient webClient;

    public EnrolledInterfaceImplementation() {
        webClient = new EnrolledRESTClient();
    }

    private static final Logger LOGGER = Logger.getLogger("EnrolledInterfaceImplementation.class");

    @Override
    public void createEnrolled(Enrolled enrolled) throws CreateErrorException {
        try{
            LOGGER.info("Creating a new enrolled");
            webClient.createEnrolled_XML(enrolled);
            LOGGER.info("Created!");
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "EnrolledManager: Error creating an enrolled" + ex.getMessage());
            throw new CreateErrorException("Error creating an enrolled" + ex.getMessage());
        }
    }

    @Override
    public void deleteEnrolled(String studentId, String subjectId) throws DeleteErrorException {
      try{
            LOGGER.info("Deleting a new enrolled");
            webClient.removeEnrolled(studentId, subjectId);
            LOGGER.info("Deleted!");
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "EnrolledManager: Error deleting an enrolled " + ex.getMessage());
            throw new DeleteErrorException("Error creating an enrolled" + ex.getMessage());
        }
      
    }

    @Override
    public void updateEnrolled(Enrolled enrolled) throws UpdateErrorException {
        try{
            LOGGER.info("Updating a new enrolled");
            webClient.updateEnrolled_XML(enrolled);
            LOGGER.info("Updated!");
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "EnrolledManager: Error updating an enrolled " + ex.getMessage());
            throw new UpdateErrorException("Error updating an enrolled" + ex.getMessage());
        }
    }

    @Override
    public Collection<Enrolled> findById(String id) throws FindErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Enrolled> findAll() throws FindErrorException {
        Set<Enrolled> enrollments;
        try {
            LOGGER.info("Finding all enrollments");
            enrollments = webClient.findAll_JSON(new GenericType<Set<Enrolled>>() {
            });
                           
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "StudentInterface: Error finding all students - " + e.getMessage());
            throw new FindErrorException("Error finding all enrollments");
        }
        return enrollments;
    }

}

package implementation;

import exceptions.CreateErrorException;
import exceptions.FindErrorException;
import exceptions.UpdateErrorException;
import interfaces.UserInterface;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import models.Student;
import models.User;
import services.UserRESTClient;

/**
 * Implementation of the UserInterface interface for user management.
 * @author irati
 */
public class UserInterfaceImplementation implements UserInterface {

    private UserRESTClient webClient;
    private static final Logger LOGGER = Logger.getLogger("UserInterfaceImplementation");

    /**
     * Constructs a new UserInterfaceImplementation instance.
     */
    public UserInterfaceImplementation() {
        webClient = new UserRESTClient();
    }

    /**
     * Creates a new user.
     *
     * @param user The user to be created.
     * @throws CreateErrorException If an error occurs during user creation.
     */
    @Override
    public void createUser(User user) throws CreateErrorException {
        try {
            LOGGER.info("Creating a new user");
            webClient.createUser_XML(user);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserInterface: Error creating a user - " + e.getMessage());
            throw new CreateErrorException("Error creating a user");
        }
    }

    /**
     * Updates an existing user.
     *
     * @param user The user to be updated.
     * @throws UpdateErrorException If an error occurs during user update.
     */
    @Override
    public void updateUser(User user) throws UpdateErrorException {
        try {
            LOGGER.info("Updating the user with ID " + user.getId());
            webClient.updateUser_XML(user);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserInterface: Error updating user - " + e.getMessage());
            throw new UpdateErrorException("Error updating a user");
        }
    }

    /**
     * Finds a user based on the provided user object.
     *
     * @param user The user to be found.
     * @return The found user.
     * @throws FindErrorException If an error occurs during user retrieval.
     */
    @Override
    public User find(User user) throws FindErrorException {
        User userSearch;
        try {
            LOGGER.info("Finding user with ID " + user.getId());
            userSearch = webClient.find_XML(User.class, user.getId().toString());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserInterface: Error finding user - " + e.getMessage());
            throw new FindErrorException("Error finding the user");
        }
        return userSearch;
    }

    /**
     * Retrieves a collection of all users.
     *
     * @return A collection of all users.
     * @throws FindErrorException If an error occurs during user retrieval.
     */
    @Override
    public Collection<User> findAll() throws FindErrorException {
        Set<User> users;
        try {
            LOGGER.info("Finding all users");
            users = webClient.findAll_XML(new GenericType<Set<User>>() {
            });
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserInterface: Error finding all users - " + e.getMessage());
            throw new FindErrorException("Error finding all users");
        }
        return users;
    }

    /**
     * Retrieves a collection of all student users.
     *
     * @return A collection of all student users.
     * @throws FindErrorException If an error occurs during user retrieval.
     */
    @Override
    public Collection<User> findAllStudents() throws FindErrorException {
        Set<User> users;
        try {
            LOGGER.info("Finding all students");
            users = webClient.findAllStudents_XML(new GenericType<Set<User>>() {
            });
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserInterface: Error finding all students - " + e.getMessage());
            throw new FindErrorException("Error finding all students");
        }
        return users;
    }

    /**
     * Retrieves a collection of all teacher users.
     *
     * @return A collection of all teacher users.
     * @throws FindErrorException If an error occurs during user retrieval.
     */
    @Override
    public Collection<User> findAllTeachers() throws FindErrorException {
        Set<User> users;
        try {
            LOGGER.info("Finding all teachers");
            users = webClient.findAllTeachers_XML(new GenericType<Set<User>>() {
            });
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserInterface: Error finding all teachers - " + e.getMessage());
            throw new FindErrorException("Error finding all teachers");
        }
        return users;
    }

    /**
     * Authenticates a user.
     *
     * @param user The user to be authenticated.
     * @return The authenticated user.
     * @throws FindErrorException If an error occurs during user authentication.
     */
    @Override
    public User login(User user) throws FindErrorException {
       User userSearch;
        try {
            LOGGER.info("Logging in user with ID " + user.getId());
            userSearch = webClient.login(user, User.class);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserInterface: Error logging in - " + e.getMessage());
            throw new FindErrorException("User not found");
        }
        return userSearch;
    }
}

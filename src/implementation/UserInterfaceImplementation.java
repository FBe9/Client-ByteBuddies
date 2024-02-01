package implementation;

import exceptions.FindErrorException;
import interfaces.UserInterface;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;
import services.UserRESTClient;

/**
 * Implementation of the UserInterface interface for user management.
 *
 * @author irati
 */
public class UserInterfaceImplementation implements UserInterface {

    /**
     * The webclient for UserRESTClient
     */
    private UserRESTClient webClient;
    /**
     * The logger for the class.
     */
    private static final Logger LOGGER = Logger.getLogger("UserInterfaceImplementation");

    /**
     * Constructs a new UserInterfaceImplementation instance.
     */
    public UserInterfaceImplementation() {
        webClient = new UserRESTClient();
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

    /**
     * Method to reset a password
     *
     * @param email the email of the user that wants to reset the password.
     */
    @Override
    public void resetPassword(String email) {
        LOGGER.info("Sending password reset request for " + email);
        webClient.resetPassword(email);
    }
}

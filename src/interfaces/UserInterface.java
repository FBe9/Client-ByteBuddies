package interfaces;

import exceptions.EncryptException;
import exceptions.FindErrorException;
import models.User;

/**
 * Interface for user rest methods.
 * @author irati
 */
public interface UserInterface {

    /**
     * Authenticates a user.
     *
     * @param user The user to be authenticated.
     * @return The authenticated user.
     * @throws FindErrorException If an error occurs during user authentication.
     * @throws EncryptException if there is an error during encrypt
     */
    public User login(User user) throws FindErrorException, EncryptException;
    /**
     * Method to reset a password
     * @param email the email of the user that wants to reset the password.
     */
    public void resetPassword(String email);

}

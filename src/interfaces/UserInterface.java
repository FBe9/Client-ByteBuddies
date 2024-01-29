/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import exceptions.CreateErrorException;
import exceptions.FindErrorException;
import exceptions.UpdateErrorException;
import java.util.Collection;
import java.util.List;
import models.User;

/**
 * Interface for user rest methods
 * @author irati
 */
public interface UserInterface {

    /**
     * Creates a new user.
     *
     * @param user The user to be created.
     * @throws CreateErrorException If an error occurs during user creation.
     */
    public void createUser(User user) throws CreateErrorException;

    /**
     * Updates an existing user.
     *
     * @param user The user to be updated.
     * @throws UpdateErrorException If an error occurs during user update.
     */
    public void updateUser(User user) throws UpdateErrorException;

    /**
     * Retrieves a collection of users.
     *
     * @return A collection of users.
     * @throws FindErrorException If an error occurs during user retrieval.
     */
    public User find(User user) throws FindErrorException;

    /**
     * Retrieves an user .
     *
     * @return An user
     * @throws FindErrorException If an error occurs during user retrieval.
     */
    public Collection<User> findAll() throws FindErrorException;

    /**
     * Retrieves all students.
     *
     * @return A collection of all student users.
     * @throws FindErrorException If an error occurs during user retrieval.
     */
    public Collection<User> findAllStudents() throws FindErrorException;
    /**
     * Retrieves all teachers.
     *
     * @return A collection of all teachers users.
     * @throws FindErrorException If an error occurs during user retrieval.
     */
    public Collection<User> findAllTeachers() throws FindErrorException;
    /**
     * Authenticates a user.
     *
     * @param user The user to be authenticated.
     * @return The authenticated user.
     * @throws FindErrorException If an error occurs during user authentication.
     */
    public User login(User user) throws FindErrorException;
    
    public void resetPassword(String email);

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import exceptions.FindErrorException;
import java.util.Collection;
import models.Teacher;
import models.User;

/**
 * Interface for teacher model
 * @author irati
 */
public interface TeacherInterface {

    /**
     * Retrieves an teacher .
     *
     * @return An teacher
     * @throws FindErrorException If an error occurs during teacher retrieval.
     */
    public Collection<Teacher> findAll() throws FindErrorException;
}

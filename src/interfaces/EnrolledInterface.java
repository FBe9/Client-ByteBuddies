/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import exceptions.CreateErrorException;
import exceptions.DeleteErrorException;
import exceptions.FindErrorException;
import exceptions.UpdateErrorException;
import java.util.Collection;
import models.Enrolled;

/**
 *
 * @author irati
 */
public interface EnrolledInterface {

    public void createEnrolled(Enrolled enrolled) throws CreateErrorException;

    public void deleteEnrolled(String studentId, String subjectId) throws DeleteErrorException;

    public void updateEnrolled(Enrolled enrolled) throws UpdateErrorException;

    public Collection<Enrolled> findById(String id) throws FindErrorException;

    public Collection<Enrolled> findAll() throws FindErrorException;
}

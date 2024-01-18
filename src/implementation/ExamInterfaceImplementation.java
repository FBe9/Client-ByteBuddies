/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementation;

import exceptions.CreateErrorException;
import exceptions.DeleteErrorException;
import exceptions.FindErrorException;
import exceptions.UpdateErrorException;
import interfaces.ExamInterface;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import models.Exam;
import services.ExamRESTClient;

/**
 *
 * @author Alex
 */
public class ExamInterfaceImplementation implements ExamInterface {

    private ExamRESTClient webClient;
    private static final Logger LOGGER = Logger.getLogger("ExamInterfaceImplementation");
    
    public ExamInterfaceImplementation(){
        webClient = new ExamRESTClient();
    }
    
    @Override
    public void createExam(Exam exam) throws CreateErrorException {
        try{
            LOGGER.info("Creating an exam");
            webClient.createExam_XML(exam);
        }catch(ClientErrorException e){
            LOGGER.log(Level.SEVERE, "ExamInterface: Error creating an exam: {0}", e.getMessage());
            throw new CreateErrorException("Error creating an exam: " + e.getMessage());
        }
    }

    @Override
    public void updateExam(Exam exam) throws UpdateErrorException {
        try{
            LOGGER.log(Level.INFO, "Updating exam with id {0}", exam.getId());
            webClient.updateExam_XML(exam);
        }catch(ClientErrorException e){
            LOGGER.log(Level.SEVERE, "ExamInterface: Error updating exam " + exam.getId() + ": {0}", e.getMessage());
            throw new UpdateErrorException("Error updating exam " + exam.getId() + ": " + e.getMessage());
        }
    }

    @Override
    public void deleteExam(Exam exam) throws DeleteErrorException {
        try{
            LOGGER.log(Level.INFO, "Deleteing exam {0}", exam.getId());
            webClient.deleteExam(exam.getId());
        } catch(ClientErrorException ex){
            LOGGER.log(Level.SEVERE, "Error deleting exam {0}", exam.getId());
            throw new DeleteErrorException("Error deleting exam " + exam.getId());
        }
    }

    @Override
    public Collection<Exam> findAllExams() throws FindErrorException {
        List<Exam> exams = null;
        try{
            LOGGER.info("Finding all exams...");
            exams = webClient.findAll_XML(new GenericType<List<Exam>>() {});
        } catch(ClientErrorException ex){
            LOGGER.log(Level.SEVERE, "Error finding exams.");
            throw new FindErrorException("Error finding exams");
        }
        return exams;
    }

    @Override
    public Exam findExamById(Integer id) throws FindErrorException {
        Exam exam = null;
        try{
            LOGGER.log(Level.INFO, "Searching for exam with id: {0}", id);
            exam = webClient.find_XML(Exam.class, id);
        }catch(ClientErrorException ex){
            LOGGER.log(Level.SEVERE, "Error finding exam with id: {0}", id);
            throw new FindErrorException("Error finding exam with id: " + id);
        }
        return exam;
    }

    @Override
    public Collection<Exam> findByDescription(String description) throws FindErrorException {
        Set<Exam> exams = null;
        try{
            LOGGER.log(Level.INFO, "Searching for exam like '{0}'", description);
            exams = webClient.findByDescription_XML(new GenericType<Set<Exam>>() {}, description);
        } catch(ClientErrorException ex){
            LOGGER.log(Level.SEVERE, "Error finding exam like ''{0}''", description);
            throw new FindErrorException("Error finding exam like '" + description + "'");
        }
        return exams;
    }

    @Override
    public Collection<Exam> findBySubject(String subjectId) throws FindErrorException {
        Set<Exam> exams = null;
        try{
            LOGGER.log(Level.INFO, "Searching exams for subject with id {0}", subjectId);
            exams = webClient.findBySubject_XML(new GenericType<Set<Exam>>() {}, subjectId);
        } catch(ClientErrorException e){
            LOGGER.log(Level.SEVERE, "Error finding exams for subject {0}", subjectId);
            throw new FindErrorException("Error finding exams for subject " + subjectId);
        }
        return exams;
    }
    
}

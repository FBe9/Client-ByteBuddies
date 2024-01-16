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
import models.Subject;
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
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "ExamInterface: Error creating an exam: {0}", e.getMessage());
            throw new CreateErrorException("Error creating an exam: " + e.getMessage());
        }
    }

    @Override
    public void updateExam(Exam exam) throws UpdateErrorException {
        try{
            LOGGER.log(Level.INFO, "Updating exam with id {0}", exam.getId());
            webClient.updateExam_XML(exam);
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "ExamInterface: Error updating exam " + exam.getId() + ": {0}", e.getMessage());
            throw new UpdateErrorException("Error updating exam " + exam.getId() + ": " + e.getMessage());
        }
    }

    @Override
    public void deleteExam(Exam exam) throws DeleteErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Exam> findByDescription(String description) throws FindErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Exam> findBySolution(String solutionFilePath) throws FindErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Exam> findBySubject(String subjectId) throws FindErrorException {
        Set<Exam> exams = null;
        try{
            LOGGER.info("Searching exams for subject with id " + subjectId);
            exams = webClient.findBySubject_XML(new GenericType<Set<Exam>>() {}, subjectId);
        } catch(ClientErrorException e){
            LOGGER.log(Level.SEVERE, "Error finding exams for subject {0}", subjectId);
            throw new FindErrorException("Error finding exams for subject " + subjectId);
        }
        return exams;
    }
    
}

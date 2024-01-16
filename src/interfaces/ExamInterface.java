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
import java.util.List;
import models.Exam;

/**
 *
 * @author Alex
 */
public interface ExamInterface {
    
    public void createExam(Exam exam) throws CreateErrorException;
    
    public void updateExam(Exam exam) throws UpdateErrorException;
    
    public void deleteExam(Exam exam) throws DeleteErrorException;
    
    public Collection<Exam> findAllExams()throws FindErrorException;
    
    public Exam findExamById(Integer id) throws FindErrorException;
    
    public Collection<Exam> findByDescription(String description) throws FindErrorException;
    
    public Collection<Exam> findBySolution(String solutionFilePath) throws FindErrorException;
    
    public Collection<Exam> findBySubject(String subject) throws FindErrorException;
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.Date;
import java.util.ResourceBundle;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import interfaces.SubjectManager;
import models.Subject;

/**
 *
 * @author irati
 */
public class SubjectRESTClient {

    private final WebTarget webTarget;
    private final Client client;
    private static final String URI
            = ResourceBundle.getBundle("config.config")
                    .getString("URL");

    public SubjectRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(URI).path("entities.subject");
    }

    public <T> T findSubjectById_XML(Class<T> responseType, Integer id) {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T findAllSubjects_XML(GenericType<T> responseType) {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T findSubjectsByName_XML(GenericType<T> responseType, String name) {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{name}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T findSubjectsByTeacher_XML(GenericType<T> responseType, String teacherName) {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{teacherName}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T findSubjectsByEndDate_XML(GenericType<T> responseType, Date endDate) {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{endDate}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T findSubjectsByInitDate_XML(GenericType<T> responseType, Date initDate) {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{initDate}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T findSubjectsWithXUnits_XML(GenericType<T> responseType, Long number) {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{number}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T findSubjectsWithEnrolledStudentsCount_XML(GenericType<T> responseType, Long number) {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{number}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T findByEnrollments_XML(GenericType<T> responseType, Integer studentId) {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{studentId}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T findSubjectsByTeacherId_XML(GenericType<T> responseType, Integer studentId) {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{studentId}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public void createSubject_XML(Object requestEntity) {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .post(javax.ws.rs.client.Entity.entity(requestEntity,
                        javax.ws.rs.core.MediaType.APPLICATION_XML),
                        Subject.class);
    }

    public void deleteSubject_XML(Integer id) {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request().delete(Subject.class);
    }

    public void updateSubject_XML(Object requestEntity) {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .put(javax.ws.rs.client.Entity.entity(requestEntity,
                        javax.ws.rs.core.MediaType.APPLICATION_XML),
                        Subject.class);
    }

    public void close() {
        client.close();
    }
}

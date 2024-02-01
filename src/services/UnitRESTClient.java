package services;

import java.util.ResourceBundle;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:UnitFacadeREST
 * [entities.unit]<br>
 * USAGE:
 * <pre>
 *        UnitFacadeREST client = new UnitFacadeREST();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Nerea
 */
public class UnitRESTClient {

    private final WebTarget webTarget;
    private final Client client;
    private static final String BASE_URI = ResourceBundle.getBundle("config.config").getString("BASE_URI");

    /**
     * Constructs a new UnitRESTClient instance.
     */
    public UnitRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.unit");
    }

    /**
     * POST method to create a new Unit in XML: uses createUnit business logic
     * method.
     *
     * @param requestEntity The Unit with default inserts to be create.
     * @throws WebApplicationException An Exception with the http errors to be
     * catch.
     */
    public void createUnit_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * POST method to create a new Unit in JSON: uses createUnit business logic
     * method.
     *
     * @param requestEntity The Unit with default inserts to be create.
     * @throws WebApplicationException An Exception with the http errors to be
     * catch.
     */
    public void createUnit_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * PUT method to modify a Unit in XML: uses updateUnit business logic
     * method.
     *
     * @param requestEntity The Unit with the change inserted to be update.
     * @throws WebApplicationException An Exception with the http errors to be
     * catch.
     */
    public void updateAlbum_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * PUT method to modify a Unit in JSON: uses updateUnit business logic
     * method.
     *
     * @param requestEntity The Unit with the change inserted to be update.
     * @throws WebApplicationException An Exception with the http errors to be
     * catch.
     */
    public void updateAlbum_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * DELETE method to remove a Unit: uses removeUnit business logic method.
     *
     * @param id The id for the unit to be deleted.
     * @throws WebApplicationException An Exception with the http errors to be
     * catch.
     */
    public void removeUnit(Integer id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    /**
     * GET method to get an Unit data by id in XML: it uses business method
     * findUnitByID.
     *
     * @param <T> models.Unit.
     * @param responseType The class of the object.
     * @param id The id for the unit to be found.
     * @return A Unit that contains the unit the method found.
     * @throws WebApplicationException An Exception with the http errors to be
     * catch.
     */
    public <T> T findUnitByID_XML(Class<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * GET method to get an Unit data by id in JSON: it uses business method
     * findUnitByID.
     *
     * @param <T> models.Unit.
     * @param responseType The class of the object.
     * @param id The id for the unit to be found.
     * @return A Unit that contains the unit the method found.
     * @throws WebApplicationException An Exception with the http errors to be
     * catch.
     */
    public <T> T findUnitByID_JSON(Class<T> responseType, String id) throws WebApplicationException {

        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * GET method to get all the Unit data in XML: it uses business method
     * findSubjectUnits.
     *
     * @param <T> models.Unit.
     * @param responseType The class of the object.
     * @return A collection of units that the method found.
     * @throws WebApplicationException An Exception with the http errors to be
     * catch.
     */
    public <T> T findAllUnits_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("findAllUnits");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * GET method to get all the Unit data in JSON: it uses business method
     * findSubjectUnits.
     *
     * @param <T> models.Unit.
     * @param responseType The class of the object.
     * @return A collection of units that the method found.
     * @throws WebApplicationException An Exception with the http errors to be
     * catch.
     */
    public <T> T findAllUnits_JSON(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("findAllUnits");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * GET method to get all the Unit data from an especific subject in XML: it
     * uses business method findSubjectUnits.
     *
     * @param <T> models.Unit.
     * @param responseType The class of the object.
     * @param subjectName A String with the name of the Subject.
     * @return A collection of units that the method found.
     * @throws WebApplicationException An Exception with the http errors to be
     * catch.
     */
    public <T> T findSubjectUnits_XML(GenericType<T> responseType, String subjectName) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findSubjectUnits/{0}", new Object[]{subjectName}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * GET method to get all the Unit data from an especific subject in JSON: it
     * uses business method findSubjectUnits.
     *
     * @param <T> models.Unit.
     * @param responseType The class of the object.
     * @param subjectName A String with the name of the Subject.
     * @return A collection of units that the method found.
     * @throws WebApplicationException An Exception with the http errors to be
     * catch.
     */
    public <T> T findSubjectUnits_JSON(GenericType<T> responseType, String subjectName) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findSubjectUnits/{0}", new Object[]{subjectName}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * GET method to get all Units data by name in XML: it uses business method
     * findSubjectUnitsByName.
     *
     * @param <T> models.Unit.
     * @param responseType The class of the object.
     * @param unitName A String with the name of the unit.
     * @param subjectName A String with the name of the Subject.
     * @return A collection of units that the method found.
     * @throws WebApplicationException An Exception with the http errors to be
     * catch.
     */
    public <T> T findSubjectUnitsByName_XML(GenericType<T> responseType, String unitName, String subjectName) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findSubjectUnitsByName/{0}/{1}", new Object[]{unitName, subjectName}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * GET method to get all Units data by name in JSON: it uses business method
     * findSubjectUnitsByName.
     *
     * @param <T> models.Unit.
     * @param responseType The class of the object.
     * @param unitName A String with the name of the unit.
     * @param subjectName A String with the name of the Subject.
     * @return A collection of units that the method found.
     * @throws WebApplicationException An Exception with the http errors to be
     * catch.
     */
    public <T> T findSubjectUnitsByName_JSON(GenericType<T> responseType, String unitName, String subjectName) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findSubjectUnitsByName/{0}/{1}", new Object[]{unitName, subjectName}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * GET method to get all Units data by name in XML: it uses business method
     * findOneSubjectUnitByName.
     *
     * @param <T> models.Unit.
     * @param responseType The class of the object.
     * @param unitName A String with the name of the unit.
     * @param subjectName A String with the name of the Subject.
     * @return A Unit that contains the unit the method found.
     * @throws WebApplicationException An Exception with the http errors to be
     * catch.
     */
    public <T> T findOneSubjectUnitByName_XML(GenericType<T> responseType, String unitName, String subjectName) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findOneSubjectUnitByName/{0}/{1}", new Object[]{unitName, subjectName}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * GET method to get all Units data by name in JSON: it uses business method
     * findOneSubjectUnitByName.
     *
     * @param <T> models.Unit.
     * @param responseType The class of the object.
     * @param unitName A String with the name of the unit.
     * @param subjectName A String with the name of the Subject.
     * @return A Unit that contains the unit the method found.
     * @throws WebApplicationException An Exception with the http errors to be
     * catch.
     */
    public <T> T findOneSubjectUnitByName_JSON(GenericType<T> responseType, String unitName, String subjectName) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findOneSubjectUnitByName/{0}/{1}", new Object[]{unitName, subjectName}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * GET method to get all Units data by date of iitialize the unit in XML: it
     * uses business method findSubjectUnitsByDateInit.
     *
     * @param <T> models.Unit.
     * @param responseType The class of the object.
     * @param dateInit A String with the date init to search.
     * @param subjectName A String with the name of the Subject.
     * @return A collection of units that the method found.
     * @throws WebApplicationException An Exception with the http errors to be
     * catch.
     */
    public <T> T findSubjectUnitsByDateInit_XML(GenericType<T> responseType, String dateInit, String subjectName) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findSubjectUnitsByDateInit/{0}/{1}", new Object[]{dateInit, subjectName}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * GET method to get all Units data by date of iitialize the unit in JSON:
     * it uses business method findSubjectUnitsByDateInit.
     *
     * @param <T> models.Unit.
     * @param responseType The class of the object.
     * @param dateInit A String with the date init to search.
     * @param subjectName A String with the name of the Subject.
     * @return A collection of units that the method found.
     * @throws WebApplicationException An Exception with the http errors to be
     * catch.
     */
    public <T> T findSubjectUnitsByDateInit_JSON(GenericType<T> responseType, String dateInit, String subjectName) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findSubjectUnitsByDateInit/{0}/{1}", new Object[]{dateInit, subjectName}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * GET method to get all Units data by date of end the unit in XML: it uses
     * business method findSubjectUnitsByDateEnd.
     *
     * @param <T> models.Unit.
     * @param responseType The class of the object.
     * @param dateEnd A String with the date end to search.
     * @param subjectName A String with the name of the Subject.
     * @return A collection of units that the method found.
     * @throws WebApplicationException An Exception with the http errors to be
     * catch.
     */
    public <T> T findSubjectUnitsByDateEnd_XML(GenericType<T> responseType, String dateEnd, String subjectName) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findSubjectUnitsByDateEnd/{0}/{1}", new Object[]{dateEnd, subjectName}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * GET method to get all Units data by date of end the unit in JSON: it uses
     * business method findSubjectUnitsByDateEnd.
     *
     * @param <T> models.Unit.
     * @param responseType The class of the object.
     * @param dateEnd A String with the date end to search.
     * @param subjectName A String with the name of the Subject.
     * @return A collection of units that the method found.
     * @throws WebApplicationException An Exception with the http errors to be
     * catch.
     */
    public <T> T findSubjectUnitsByDateEnd_JSON(GenericType<T> responseType, String dateEnd, String subjectName) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findSubjectUnitsByDateEnd/{0}/{1}", new Object[]{dateEnd, subjectName}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * GET method to get all Units data by hours in XML: it uses business method
     * findSubjectUnitsByHours.
     *
     * @param <T> models.Unit.
     * @param responseType The class of the object.
     * @param hours A String with the hours of the unit.
     * @param subjectName A String with the name of the Subject.
     * @return A collection of units that the method found.
     * @throws WebApplicationException An Exception with the http errors to be
     * catch.
     */
    public <T> T findSubjectUnitsByHours_XML(GenericType<T> responseType, String hours, String subjectName) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findSubjectUnitsByHours/{0}/{1}", new Object[]{hours, subjectName}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * GET method to get all Units data by hours in JSON: it uses business
     * method findSubjectUnitsByHours.
     *
     * @param <T> models.Unit.
     * @param responseType The class of the object.
     * @param hours A String with the hours of the unit.
     * @param subjectName A String with the name of the Subject.
     * @return A collection of units that the method found.
     * @throws WebApplicationException An Exception with the http errors to be
     * catch.
     */
    public <T> T findSubjectUnitsByHours_JSON(GenericType<T> responseType, Integer hours, String subjectName) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findSubjectUnitsByHours/{0}/{1}", new Object[]{hours, subjectName}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * GET method to get all Units data by subject where the Teacher in XML: it
     * uses business method findUnitsFromTeacherSubjects.
     *
     * @param <T> models.Unit.
     * @param responseType The class of the object.
     * @param userID A String with The logged user id.
     * @return A collection of units that the method found.
     * @throws WebApplicationException An Exception with the http errors to be
     * catch.
     */
    public <T> T findUnitsFromTeacherSubjects_XML(GenericType<T> responseType, String userID) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findUnitsFromTeacherSubjects/{0}", new Object[]{userID}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * GET method to get all Units data by subject where the Teacher in JSON: it
     * uses business method findUnitsFromTeacherSubjects.
     *
     * @param <T> models.Unit.
     * @param responseType The class of the object.
     * @param userID A String with The logged user id.
     * @return A collection of units that the method found.
     * @throws WebApplicationException An Exception with the http errors to be
     * catch.
     */
    public <T> T findUnitsFromTeacherSubjects_JSON(GenericType<T> responseType, String userID) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findUnitsFromTeacherSubjects/{0}", new Object[]{userID}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * GET method to get all Units data by subject where the Student is
     * matriculated in XML: it uses business method
     * findUnitsFromStudentSubjects.
     *
     * @param <T> models.Unit.
     * @param responseType The class of the object.
     * @param userID A String with The logged user id.
     * @return A collection of units that the method found.
     * @throws WebApplicationException An Exception with the http errors to be
     * catch.
     */
    public <T> T findUnitsFromStudentSubjects_XML(GenericType<T> responseType, String userID) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findUnitsFromStudentSubjects/{0}", new Object[]{userID}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * GET method to get all Units data by subject where the Student is
     * matriculated in JSON: it uses business method
     * findUnitsFromStudentSubjects.
     *
     * @param <T> models.Unit.
     * @param responseType The class of the object.
     * @param userID A String with The logged user id.
     * @return A collection of units that the method found.
     * @throws WebApplicationException An Exception with the http errors to be
     * catch.
     */
    public <T> T findUnitsFromStudentSubjects_JSON(GenericType<T> responseType, String userID) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findUnitsFromStudentSubjects/{0}", new Object[]{userID}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Connection closing method.
     */
    public void close() {
        client.close();
    }

}

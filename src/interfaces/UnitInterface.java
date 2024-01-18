package interfaces;

import exceptions.*;
import java.util.Date;
import java.util.List;
import models.Unit;

/**
 *
 * @author Nerea
 */
public interface UnitInterface {

    /**
     * This method creates a new Unit in the data base.
     *
     * @param unit The Unit entity object containing new Unit data.
     * @throws CreateErrorException Thrown when any error or exception occurs
     * during creation.
     */
    public void createUnit(Unit unit) throws CreateErrorException;

    /**
     * This method updates a movement data in the data store.
     *
     * @param unit The Unit entity object containing modified Unit data.
     * @throws UpdateErrorException Thrown when any error or exception occurs
     * during update.
     */
    public void updateUnit(Unit unit) throws UpdateErrorException;

    /**
     * This method removes a Unit from the data store.
     *
     * @param unit The Unit entity object to be removed.
     * @throws DeleteErrorException Thrown when any error or exception occurs
     * during deletion.
     */
    public void removeUnit(Unit unit) throws DeleteErrorException;

    /**
     * The method finds a unit which id is equals the id the User introduced.
     *
     * @param id A String that contains the id the user introduce.
     * @return The Unit entity object to be found.
     * @throws FindErrorException Thrown when any error or exception occurs
     * during reading.
     */
    public Unit findUnitByID(String id) throws FindErrorException;

    /**
     * The method finds all the units.
     *
     * @return An List of Units that contains the units that the method found.
     * @throws FindErrorException Thrown when any error or exception occurs
     * during reading.
     */
    public List<Unit> findAllUnits() throws FindErrorException;

    /**
     * This method finds all the units which subject name contains the words the
     * user introduced.
     *
     * @param name A String that contains the words the user introduced.
     * @return An List of Units that contains the units that the method found.
     * @throws FindErrorException Thrown when any error or exception occurs
     * during reading.
     */
    public List<Unit> findSubjectUnits(String name) throws FindErrorException;

    /**
     * This method finds all the units that the name contains the words the user
     * introduced and the subject name is the one the user introduced.
     *
     * @param name A String that contains the words the user introduced.
     * @param subject A String with the name of the subject
     * @return An List of Units that contains the units that the method found.
     * @throws FindErrorException Thrown when any error or exception occurs
     * during reading.
     */
    public List<Unit> findSubjectUnitsByName(String name, String subject) throws FindErrorException;

    /**
     * This method finds a unit which name is the one the user introduced and
     * the subject name is the one the user introduced.
     *
     * @param name A String that contains the words the user introduced.
     * @param subject A String with the name of the subject
     * @return The Unit entity object to be found.
     * @throws FindErrorException Thrown when any error or exception occurs
     * during reading.
     */
    public Unit findOneSubjectUnitByName(String name, String subject) throws FindErrorException;

    /**
     * This method finds all the units where the init date of the unit is equals
     * the date the user introduced and the subject name is the one the user
     * introduce.
     *
     * @param dateInit A Date that contains the date the User introduce.
     * @param subject A String with the name of the subject
     * @return An List of Units that contains the units that the method found.
     * @throws FindErrorException Thrown when any error or exception occurs
     * during reading.
     */
    public List<Unit> findSubjectUnitsByDateInit(Date dateInit, String subject) throws FindErrorException;

    /**
     * This method finds all the units where the end date of the unit is equals
     * the date the user introduced and the subject name is the one the user
     * introduce.
     *
     * @param dateEnd A Date that contains the date the User introduce.
     * @param subject A String with the name of the subject
     * @return An List of Units that contains the units that the method found.
     * @throws FindErrorException Thrown when any error or exception occurs
     * during reading.
     */
    public List<Unit> findSubjectUnitsByDateEnd(Date dateEnd, String subject) throws FindErrorException;

    /**
     * This method finds all the units where the hours of the unit are equals
     * the hours the user introduced and the subject name is the one the user
     * introduce.
     *
     * @param hours A String with the number the user introduce.
     * @param subject A String with the name of the subject
     * @return An List of Units that contains the units that the method found.
     * @throws FindErrorException Thrown when any error or exception occurs
     * during reading.
     */
    public List<Unit> findSubjectUnitsByHours(String hours, String subject) throws FindErrorException;
    
    /**
     * This method finds all the units from the subjects where the Teacher
     * teachs.
     *
     * @param userId A String with the id of the user that is logged to the
     * application.
     * @return An List of Units that contains the units that the method found.
     * @throws FindErrorException Thrown when any error or exception occurs
     * during reading.
     */
    public List<Unit> findUnitsFromTeacherSubjects(String userId) throws FindErrorException;

    /**
     * This method finds all the units from the subjects where the Student is
     * matriculated.
     *
     * @param userId A String with the id of the user that is logged to the
     * application.
     * @return An List of Units that contains the units that the method found.
     * @throws FindErrorException Thrown when any error or exception occurs
     * during reading.
     */
    public List<Unit> findUnitsFromStudentSubjects(String userId) throws FindErrorException;

}

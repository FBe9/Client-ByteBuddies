/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementation;

import exceptions.*;
import interfaces.UnitInterface;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import models.Unit;
import services.UnitRESTClient;

/**
 *
 * @author Nerea
 */
public class UnitManagerImplementation implements UnitInterface {

    private final UnitRESTClient webClient;
    private static final Logger LOGGER = Logger.getLogger("package implementation");

    public UnitManagerImplementation() {
        webClient = new UnitRESTClient();
    }
    
    /**
     * This method creates a new Unit in the data base.
     *
     * @param unit The Unit entity object containing new Unit data.
     * @throws CreateErrorException Thrown when any error or exception occurs
     * during creation.
     */
    @Override
    public void createUnit(Unit unit) throws CreateErrorException {
        try {
            LOGGER.log(Level.INFO, "Creating a new Unit id= {0}", unit.getId());
            webClient.createUnit_XML(unit);

        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "UnitManagerImplementation: Error creating a unit{0}", e.getMessage());
            throw new CreateErrorException("Error creating a unit" + e.getMessage());
        }
    }
    
     /**
     * This method updates a movement data in the data store.
     *
     * @param unit The Unit entity object containing modified Unit data.
     * @throws UpdateErrorException Thrown when any error or exception occurs
     * during update.
     */
    @Override
    public void updateUnit(Unit unit) throws UpdateErrorException {
        try {
            LOGGER.log(Level.INFO, "Updating the unit  id= {0}", unit.getId());
            webClient.updateAlbum_XML(unit);
        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "UnitManagerImplementation: Error updating a unit{0}", e.getMessage());
            throw new UpdateErrorException("Error updating a unit" + e.getMessage());
        }
    }

    /**
     * This method removes a Unit from the data store.
     *
     * @param unit The Unit entity object to be removed.
     * @throws DeleteErrorException Thrown when any error or exception occurs
     * during deletion.
     */
    @Override
    public void removeUnit(Unit unit) throws DeleteErrorException {
        try {
            LOGGER.log(Level.INFO, "Deleting the unit  id= {0}", unit.getId());
            webClient.removeUnit(Integer.parseInt(unit.getId()));

        } catch (ClientErrorException e) {
            LOGGER.severe(e.getMessage());
            throw new DeleteErrorException(e.getMessage());
        }
    }
    
    /**
     * The method finds a unit which id is equals the id the User introduced.
     *
     * @param id A String that contains the id the user introduce.
     * @return The Unit entity object to be found.
     * @throws FindErrorException Thrown when any error or exception occurs
     * during reading.
     */
    @Override
    public Unit findUnitByID(String id) throws FindErrorException {
        Unit unit = null;
        try {
            unit = webClient.findUnitByID_XML(Unit.class, id);
        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "UnitManagerImplementation ->  findUnitByID(Integer id) {0}", e.getMessage());
            throw new FindErrorException("Error finding unit" + e.getMessage());
        }
        return unit;
    }
    
    /**
     * The method finds all the units.
     *
     * @return An List of Units that contains the units that the method found.
     * @throws FindErrorException Thrown when any error or exception occurs
     * during reading.
     */
    @Override
    public List<Unit> findAllUnits() throws FindErrorException {
        List<Unit> units = null;
        try {
            units = webClient.findAllUnits_XML(new GenericType<List<Unit>>() {
            });

        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "UnitManagerImplementation ->  findAllUnits() {0}", e.getMessage());
            throw new FindErrorException("Error finding unit" + e.getMessage());
        }
        return units;
    }

    /**
     * This method finds all the units which subject name contains the words the
     * user introduced.
     *
     * @param name A String that contains the words the user introduced.
     * @return An List of Units that contains the units that the method found.
     * @throws FindErrorException Thrown when any error or exception occurs
     * during reading.
     */
    @Override
    public List<Unit> findSubjectUnits(String name) throws FindErrorException {
        List<Unit> units = null;
        try {
            units = webClient.findSubjectUnits_XML(new GenericType<List<Unit>>() {
            }, name);

        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "UnitManagerImplementation ->  findSubjectUnits(String name) {0}", e.getMessage());
            throw new FindErrorException("Error finding unit" + e.getMessage());
        }
        return units;
    }

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
    @Override
    public List<Unit> findSubjectUnitsByName(String name, String subject) throws FindErrorException {
        List<Unit> units = null;
        try {
            units = webClient.findAllUnits_XML(new GenericType<List<Unit>>() {
            });

        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "UnitManagerImplementation ->  findSubjectUnitsByName(String name, String subject) {0}", e.getMessage());
            throw new FindErrorException("Error finding unit" + e.getMessage());
        }
        return units;
    }

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
    @Override
    public Unit findOneSubjectUnitByName(String name, String subject) throws FindErrorException {
        Unit unit = null;
        try {
            unit = webClient.findOneSubjectUnitByName_XML(new GenericType<Unit>() {
            }, name, subject);

        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "UnitManagerImplementation ->  findOneSubjectUnitByName(String name, String subject) {0}", e.getMessage());
            throw new FindErrorException("Error finding unit" + e.getMessage());
        }
        return unit;
    }

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
    @Override
    public List<Unit> findSubjectUnitsByDateInit(Date dateInit, String subject) throws FindErrorException {
        List<Unit> units = null;
        try {
            units = webClient.findSubjectUnitsByDateInit_XML(new GenericType<List<Unit>>() {
            }, subject, subject);

        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "UnitManagerImplementation ->  findSubjectUnitsByDateInit(Date dateInit, String subject) {0}", e.getMessage());
            throw new FindErrorException("Error finding unit" + e.getMessage());
        }
        return units;
    }

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
    @Override
    public List<Unit> findSubjectUnitsByDateEnd(Date dateEnd, String subject) throws FindErrorException {
        List<Unit> units = null;
        try {
            units = webClient.findSubjectUnitsByDateEnd_XML(new GenericType<List<Unit>>() {
            }, subject, subject);

        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "UnitManagerImplementation ->  findSubjectUnitsByDateEnd(Date dateEnd, String subject) {0}", e.getMessage());
            throw new FindErrorException("Error finding unit" + e.getMessage());
        }
        return units;
    }

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
    @Override
    public List<Unit> findSubjectUnitsByHours(String hours, String subject) throws FindErrorException {
        List<Unit> units = null;
        try {
            units = webClient.findSubjectUnitsByHours_XML(new GenericType<List<Unit>>() {
            }, hours, subject);

        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "UnitManagerImplementation ->  findSubjectUnitsByHours(Integer hours, String subject) {0}", e.getMessage());
            throw new FindErrorException("Error finding unit" + e.getMessage());
        }
        return units;
    }

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
    @Override
    public List<Unit> findUnitsFromTeacherSubjects(String userId) throws FindErrorException {
        List<Unit> units = null;
        try {
            LOGGER.info("Find all subjects units that a student has");
            units = webClient.findUnitsFromTeacherSubjects_XML(new GenericType<List<Unit>>() {},
                    userId);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UnitManagerImplementation ->  findUnitsFromTeacherSubjects(String userId) {0}", e.getMessage());
            throw new FindErrorException("Error finding Unit" + e.getMessage());
        }
        return units;
    }

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
    @Override
    public List<Unit> findUnitsFromStudentSubjects(String userId) throws FindErrorException {
        List<Unit> units = null;
        try {
            LOGGER.info("Find all subjects units that a teacher has");
            units = webClient.findUnitsFromStudentSubjects_XML(new GenericType<List<Unit>>() {
            }, userId);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UnitManagerImplementation ->  findUnitsFromStudentSubjects(String userId) {0}", e.getMessage());
            throw new FindErrorException("Error finding unit" + e.getMessage());
        }
        return units;
    }

}

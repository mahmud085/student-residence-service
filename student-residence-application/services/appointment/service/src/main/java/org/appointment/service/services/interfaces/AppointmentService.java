package org.appointment.service.services.interfaces;

import org.appointment.common.exceptions.*;
import org.appointment.dataaccess.data.models.Appointment;
import org.appointment.dataaccess.models.PaginatedDataList;
import org.appointment.service.models.Contract;
import org.appointment.service.models.NewAppointment;

import java.time.LocalDate;
import java.util.List;

/**
 * AppointmentService is the service interface for appointment.
 * 
 * @author Tawhidul Islam
 *
 */
public interface AppointmentService {
	
	/**
	 * This method creates new appointment
	 * @param newAppointment
	 * @param contract
	 * @param contextUserId
	 * @return {@link Appointment}
	 * @throws ValidationException
	 * @throws InvalidOperationException
	 */
    Appointment createAppointment(NewAppointment newAppointment, Contract contract, String contextUserId) throws ValidationException, InvalidOperationException;
    /**
     * This method gives the {@link Appointment}
     * @param appointmentId
     * @return {@link Appointment}
     * @throws ObjectNotFoundException
     */
    Appointment getAppointment(String appointmentId) throws ObjectNotFoundException;
    /**
     * This method gives all appointment list
     * @return {@link Appointment}
     * @throws ObjectNotFoundException
     */
    List<Appointment> getAllAppointments() throws ObjectNotFoundException;
    /**
     * Pagination management for appointment list
     * @param pageNum
     * @param pageSize
     * @return {@link Appointment}
     */
    PaginatedDataList<Appointment> getAllAppointments(int pageNum, int pageSize);
    /**
     * List of get all appointments
     * @param desiredDate
     * @return {@link java.util.List} of {@link Appoitment}
     */
    
    List<Appointment> getAllAppointments(LocalDate desiredDate);
    /**
     * Paginated list of get all appointments
     * @param desiredDate
     * @param pageNum
     * @param pageSize
     * @return {@link PaginatedDataList} of {@Link Appointment}
     */
    PaginatedDataList<Appointment> getAllAppointments(LocalDate desiredDate, int pageNum, int pageSize);
    /**
     * This method is to accept appointment
     * @param appointmentId
     * @return {@link Appointment}
     * @throws InvalidOperationException
     * @throws ObjectNotFoundException
     * @throws OperationAlreadyExecutedException
     */
    Appointment acceptAppointment(String appointmentId) throws InvalidOperationException, ObjectNotFoundException, OperationAlreadyExecutedException;
    /**
     * This method is to deny appointment
     * @param appointmentId
     * @return {@link Appointment}
     * @throws InvalidOperationException
     * @throws ObjectNotFoundException
     * @throws OperationAlreadyExecutedException
     */
    Appointment denyAppointment(String appointmentId) throws InvalidOperationException, ObjectNotFoundException, OperationAlreadyExecutedException;
    /**
     * This method is to get appointment by contractors id
     * @param contractorsUserID
     * @return -list of appointments
     */
    List<Appointment> getAppointmentsByContractor(String contractorsUserID);
}

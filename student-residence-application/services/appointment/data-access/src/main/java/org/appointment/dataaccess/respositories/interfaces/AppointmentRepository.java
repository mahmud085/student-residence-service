package org.appointment.dataaccess.respositories.interfaces;

import org.appointment.common.exceptions.ObjectNotFoundException;
import org.appointment.dataaccess.data.enums.AppointmentStatus;
import org.appointment.dataaccess.data.models.Appointment;
import org.appointment.dataaccess.models.PaginatedDataList;

import java.time.LocalDate;
import java.util.List;
/**
 * AppointmentRepository is the repository interface for appointment.
 * @author Tawhidul Islam Pritam
 *
 */
public interface AppointmentRepository extends GenericRepository<Appointment> {

	/**
	 * Retrieves appointments with pagination.
	 * @param pageNum
	 * @param pageSize
	 * @return Paginated Data List of appointment.
	 */
	PaginatedDataList<Appointment> getAll(int pageNum, int pageSize);
    /**
     * Retrieves all appointments.
     * @return List of  {@link Appointment}
     */
    List<Appointment> getAll();
    /**
     * Retrieves all appointments filtered by desired date.
     * @param desiredDateFilter
     * @return List of  {@link Appointment} filtered by desired date.
     */
    List<Appointment> getAll(LocalDate desiredDateFilter);
    /**
     * Retrieves appointments filtered by the given desired date with pagination.
     * @param desiredDateFilter
     * @param pageNum
     * @param pageSize
     * @return {@link PaginatedDataList} of all {@link Appointment}
     */
    PaginatedDataList<Appointment> getAll(LocalDate desiredDateFilter, int pageNum, int pageSize);
    /**
     * Retrieve a single appointment
     * @param appointmentId
     * @return {@link Appointment}
     * @throws ObjectNotFoundException
     */
    Appointment getById(String appointmentId) throws ObjectNotFoundException;
    /**
     * Update Appointment status for a given appointment id.
     * @param appointmentId
     * @param status
     * @throws ObjectNotFoundException
     */
    void updateAppointmentStatus(String appointmentId, AppointmentStatus status) throws ObjectNotFoundException;
    /**
     * Retrieves all appointments created by a user.
     * @param createdByUserId
     * @return List of {@link Appointment}
     */
    List<Appointment> getAll(String createdByUserId);
}

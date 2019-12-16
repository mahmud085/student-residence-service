package org.appointment.service.services.implementations;

import com.google.inject.Inject;
import org.appointment.common.exceptions.InvalidOperationException;
import org.appointment.common.exceptions.ValidationException;
import org.appointment.common.helpers.DateHelper;
import org.appointment.common.helpers.ValidationHelper;
import org.appointment.dataaccess.data.models.Appointment;
import org.appointment.dataaccess.respositories.interfaces.AppointmentRepository;
import org.appointment.dataaccess.respositories.interfaces.RoomRepository;
import org.appointment.service.models.NewAppointment;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AppointmentServiceImpl implements org.appointment.service.services.interfaces.AppointmentService {
    @Inject
    private AppointmentRepository appointmentRepository;

    @Inject
    private RoomRepository roomRepository;

    @Override
    public Appointment createAppointment(NewAppointment newAppointment, String contextUserId) throws ValidationException, InvalidOperationException {
        LocalDate createdOn = DateHelper.getCurrentDate();

        ValidationHelper<NewAppointment> validationHelper = new ValidationHelper<>();
        validationHelper.validate(newAppointment);


        Appointment appointment = new Appointment() {
            {
                //setAppointmentId(UUID.randomUUID().toString());
                setContractorsName(newAppointment.getContractorsName().trim());
                setRoomNumber(newAppointment.getRoomNumber().trim());
                setAppointmentType(newAppointment.getAppointmentType());
                setIssue(newAppointment.getIssue());
                setPriority(newAppointment.getPriority());
                setDesiredDate(newAppointment.getDesiredDate());
                setStatus(newAppointment.getStatus());
                setCreatedBy(contextUserId);
                setCreatedOn(createdOn);
            }
        };

        return appointmentRepository.add(appointment);
    }

}

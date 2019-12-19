package org.appointment.dataaccess.store;

import org.appointment.dataaccess.data.enums.AppointmentPriority;
import org.appointment.dataaccess.data.enums.AppointmentStatus;
import org.appointment.dataaccess.data.enums.AppointmentType;
import org.appointment.dataaccess.data.models.Appointment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    public static List<Appointment> appointments = new ArrayList<Appointment>(){
        {
            add(new Appointment() {
                {
                    setId(1);
                    setAppointmentId("A1");
                    setContractorsName("Resident 1");
                    setContractId("C1");
                    setRoomNumber("Room_1");
                    setAppointmentType(AppointmentType.MoveIn);
                    setIssue("Some Issue");
                    setPriority(AppointmentPriority.High);
                    setDesiredDate(LocalDate.parse("2019-12-20"));
                    setStatus(AppointmentStatus.Received);
                    setCreatedBy("CT1");
                    setCreatedOn(LocalDate.now());
                }
            });

            add(new Appointment() {
                {
                    setId(1);
                    setAppointmentId("A2");
                    setContractorsName("Resident 2");
                    setContractId("C2");
                    setRoomNumber("Room_2");
                    setAppointmentType(AppointmentType.MoveOut);
                    setIssue("Some Issue");
                    setPriority(AppointmentPriority.High);
                    setDesiredDate(LocalDate.parse("2019-06-20"));
                    setStatus(AppointmentStatus.Received);
                    setCreatedBy("CT1");
                    setCreatedOn(LocalDate.now());
                }
            });

            add(new Appointment() {
                {
                    setId(1);
                    setAppointmentId("A3");
                    setContractorsName("Resident 3");
                    setContractId("C3");
                    setRoomNumber("Room_3");
                    setAppointmentType(AppointmentType.Miscellaneous);
                    setIssue("Some Issue");
                    setPriority(AppointmentPriority.High);
                    setDesiredDate(LocalDate.parse("2019-12-20"));
                    setStatus(AppointmentStatus.Received);
                    setCreatedBy("CT1");
                    setCreatedOn(LocalDate.now());
                }
            });
        }
    };
}

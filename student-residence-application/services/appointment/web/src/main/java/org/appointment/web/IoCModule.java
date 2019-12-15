package org.appointment.web;

import com.google.inject.AbstractModule;
import org.appointment.dataaccess.respositories.implementations.AppointmentRepositoryImpl;
import org.appointment.dataaccess.respositories.implementations.RoomRepositoryImpl;
import org.appointment.dataaccess.respositories.interfaces.AppointmentRepository;
import org.appointment.dataaccess.respositories.interfaces.RoomRepository;
import org.appointment.service.services.implementations.AppointmentServiceImpl;
import org.appointment.service.services.interfaces.AppointmentService;


public class IoCModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(AppointmentService .class)
            .to(AppointmentServiceImpl.class);

        bind(AppointmentRepository.class)
                .to(AppointmentRepositoryImpl.class);

        bind(RoomRepository.class)
                .to(RoomRepositoryImpl.class);
    }
}

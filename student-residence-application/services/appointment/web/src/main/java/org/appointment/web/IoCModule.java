package org.appointment.web;

import com.google.inject.AbstractModule;


public class IoCModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ContractService .class)
            .to(AppointmentServiceImpl .class);

        bind(ContractRepository.class)
                .to(AppointmentRepositoryImpl.class);

        bind(RoomRepository.class)
                .to(RoomRepositoryImpl.class);
    }
}

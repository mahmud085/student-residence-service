package org.contract.web;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import org.contract.dataaccess.context.contract.ContractDBManagerFactory;
import org.contract.dataaccess.context.DBManagerFactory;
import org.contract.dataaccess.respositories.implementations.ContractRepositoryImpl;
import org.contract.dataaccess.respositories.implementations.RoomRepositoryImpl;
import org.contract.dataaccess.respositories.interfaces.ContractRepository;
import org.contract.dataaccess.respositories.interfaces.RoomRepository;
import org.contract.service.services.implementations.ContractServiceImpl;
import org.contract.service.services.interfaces.ContractService;

public class IoCModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ContractService .class)
            .to(ContractServiceImpl .class);

        bind(ContractRepository.class)
                .to(ContractRepositoryImpl.class);

        bind(RoomRepository.class)
                .to(RoomRepositoryImpl.class);

        bind(DBManagerFactory.class)
                .annotatedWith(Names.named("ContractDB"))
                .to(ContractDBManagerFactory.class)
                .in(Scopes.SINGLETON);
    }
}

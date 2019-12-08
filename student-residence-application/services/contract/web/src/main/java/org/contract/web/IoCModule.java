package org.contract.web;

import com.google.inject.AbstractModule;
import org.contract.dataaccess.respositories.implementations.ContractRepositoryImpl;
import org.contract.dataaccess.respositories.interfaces.ContractRepository;
import org.contract.service.services.implementations.ContractServiceImpl;
import org.contract.service.services.interfaces.ContractService;

public class IoCModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ContractService .class)
            .to(ContractServiceImpl .class);

        bind(ContractRepository.class)
                .to(ContractRepositoryImpl.class);
    }
}

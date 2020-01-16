package org.contract.dataaccess.context.contract;

import org.contract.dataaccess.context.DBManagerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ContractDBManagerFactory implements DBManagerFactory {
    private EntityManagerFactory entityManagerFactory;

    public ContractDBManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("contract_service_jpa");
    }

    @Override
    public EntityManager createEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}

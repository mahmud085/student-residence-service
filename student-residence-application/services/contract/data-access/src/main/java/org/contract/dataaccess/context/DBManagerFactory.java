package org.contract.dataaccess.context;

import javax.persistence.EntityManager;

public interface DBManagerFactory {
    EntityManager createEntityManager();
}

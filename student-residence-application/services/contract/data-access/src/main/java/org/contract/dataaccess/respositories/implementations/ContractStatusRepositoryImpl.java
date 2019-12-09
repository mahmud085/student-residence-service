package org.contract.dataaccess.respositories.implementations;

import org.contract.common.exceptions.ObjectNotFoundException;
import org.contract.dataaccess.data.models.ContractStatus;
import org.contract.dataaccess.respositories.interfaces.ContractStatusRepository;
import org.contract.dataaccess.store.DataStore;

import java.util.List;

public class ContractStatusRepositoryImpl implements ContractStatusRepository {
    private static int id = 0;

    @Override
    public ContractStatus add(ContractStatus contractStatus) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ContractStatus get(String contractStatusId) {
        return DataStore.contractStatuses.stream()
                .filter(x -> x.getContractStatusId().toLowerCase().equals(contractStatusId.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<ContractStatus> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(ContractStatus contractStatus) throws ObjectNotFoundException {
        throw new UnsupportedOperationException();
    }

    private synchronized int getNewId() {
        return ++id;
    }

}

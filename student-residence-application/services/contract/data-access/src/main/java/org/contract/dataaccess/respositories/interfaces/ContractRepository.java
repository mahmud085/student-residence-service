package org.contract.dataaccess.respositories.interfaces;

import org.contract.dataaccess.exceptions.PaginationRangeOutOfBoundException;
import org.contract.dataaccess.data.models.Contract;
import org.contract.dataaccess.models.PaginatedDataList;

import java.util.List;

public interface ContractRepository extends GenericRepository<Contract> {
    PaginatedDataList<Contract> getAll(int pageNum, int pageSize) throws PaginationRangeOutOfBoundException;
    List<Contract> getAll(String contractorsNameFilter);
    PaginatedDataList<Contract> getAll(String contractorsNameFilter, int pageNum, int pageSize) throws PaginationRangeOutOfBoundException;
}

package org.contract.service.services.interfaces;

import org.contract.common.exceptions.ObjectNotFoundException;
import org.contract.dataaccess.data.models.Contract;
import org.contract.common.exceptions.PaginationRangeOutOfBoundException;
import org.contract.dataaccess.models.PaginatedDataList;
import org.contract.common.exceptions.InvalidOperationException;
import org.contract.common.exceptions.ValidationException;
import org.contract.service.models.NewContract;

import java.time.LocalDate;
import java.util.List;

public interface ContractService {
    Contract createContract(NewContract newContract) throws ValidationException, InvalidOperationException;
    Contract getContract(String contractId) throws ObjectNotFoundException;
    List<Contract> getContracts();
    PaginatedDataList<Contract> getContracts(int pageNum, int pageSize) throws PaginationRangeOutOfBoundException;
    List<Contract> getContracts(String contractorsNameFilter);
    PaginatedDataList<Contract> getContracts(String contractorsNameFilter, int pageNum, int pageSize) throws PaginationRangeOutOfBoundException;
    void confirmContract(String contractId) throws ObjectNotFoundException, InvalidOperationException;
    void extendContract(String contractId, LocalDate newEndDate) throws ObjectNotFoundException, ValidationException,  InvalidOperationException;
    void terminateContract(String contractId, LocalDate newEndDate) throws ObjectNotFoundException, ValidationException, InvalidOperationException;
}

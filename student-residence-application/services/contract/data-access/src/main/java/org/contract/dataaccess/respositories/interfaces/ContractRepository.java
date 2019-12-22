package org.contract.dataaccess.respositories.interfaces;

import org.contract.dataaccess.data.models.Contract;
import org.contract.dataaccess.models.PaginatedDataList;

import java.time.LocalDate;
import java.util.List;

public interface ContractRepository extends GenericRepository<Contract> {
    Contract getActiveContractByRoomNumberInDateRange(String roomNumber, LocalDate startDate, LocalDate endDate);
    List<Contract> getAllByContractorsUserId(String contractorsUserId);
    PaginatedDataList<Contract> getAll(int pageNum, int pageSize);
    List<Contract> getAll(String contractorsNameFilter);
    PaginatedDataList<Contract> getAll(String contractorsNameFilter, int pageNum, int pageSize);
}

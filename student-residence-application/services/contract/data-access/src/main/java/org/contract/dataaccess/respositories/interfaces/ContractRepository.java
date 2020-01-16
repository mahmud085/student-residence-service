package org.contract.dataaccess.respositories.interfaces;

import org.contract.dataaccess.data.models.Contract;
import org.contract.dataaccess.models.PaginatedDataList;

import java.time.LocalDate;
import java.util.List;

/**
 * ContractRepository is the repository interface for contract.
 *
 * @author Mohammed Mostakim Ornob
 *
 */
public interface ContractRepository extends GenericRepository<Contract> {
    /**
     * Retrieves the active contract with specified parameters.
     * @param roomNumber Number of the room.
     * @param startDate Date contract starts on.
     * @param endDate Date contract ends on.
     * @return Active contract for the specified parameters. Returns null if no active contract is found with the specified parameters.
     */
    Contract getActiveContractByRoomNumberInDateRange(String roomNumber, LocalDate startDate, LocalDate endDate);

    /**
     * Retrieves contracts of a specific contractor.
     * @param contractorsUserId User Id of the contractor of whose the contracts are to be retrieved.
     * @return List of contracts of the specified contractor. Returns empty list if no contract is found for the specified contractor.
     */
    List<Contract> getAllByContractorsUserId(String contractorsUserId);

    /**
     * Retrieves contracts with pagination.
     * @param pageNum Number of the page.
     * @param pageSize Size of the page.
     * @return Paginated Data List of contract.
     */
    PaginatedDataList<Contract> getAll(int pageNum, int pageSize);

    /**
     * Retrieves contracts filtered by the given Contractor's name.
     * @param contractorsNameFilter Name of Contractor to be applied as filter.
     * @return List of filtered contracts. Returns empty list if no contract is found for the specified filter.
     */
    List<Contract> getAll(String contractorsNameFilter);

    /**
     * Retrieves contracts filtered by the given Contractor's name with pagination.
     * @param contractorsNameFilter Name of Contractor to be applied as filter.
     * @param pageNum Number of the page.
     * @param pageSize Size of the page.
     * @return Paginated Data List of contract.
     */
    PaginatedDataList<Contract> getAll(String contractorsNameFilter, int pageNum, int pageSize);
}

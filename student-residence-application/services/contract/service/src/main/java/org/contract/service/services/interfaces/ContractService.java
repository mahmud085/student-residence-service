package org.contract.service.services.interfaces;

import org.contract.common.exceptions.ObjectNotFoundException;
import org.contract.common.exceptions.OperationAlreadyExecutedException;
import org.contract.dataaccess.data.models.Contract;
import org.contract.dataaccess.models.PaginatedDataList;
import org.contract.common.exceptions.InvalidOperationException;
import org.contract.common.exceptions.ValidationException;
import org.contract.service.models.NewContract;

import java.time.LocalDate;
import java.util.List;

/**
 * ContractService is the service interface for contract.
 *
 * @author Mohammed Mostakim Ornob
 *
 */
public interface ContractService {
    /**
     * Creates a contract.
     * @param newContract Object containing data for the contract to be created.
     * @param contextUserId User Id of the user who is creating the contract.
     * @throws ValidationException Thrown if the given data for creating the contract is invalid.
     * @throws InvalidOperationException Thrown if the creation operation is invalid.
     * @return Created contract.
     */
    Contract createContract(NewContract newContract, String contextUserId) throws ValidationException, InvalidOperationException;

    /**
     * Retrieves a specific contract.
     * @param contractId Id of the contract to be retrieved.
     * @return Contract with the specified id.
     * @throws ObjectNotFoundException Thrown if no contract is found with the specific id.
     */
    Contract getContract(String contractId) throws ObjectNotFoundException;

    /**
     * Retrieves all contracts.
     * @return List of all contracts. Returns empty list if no contract is found.
     */
    List<Contract> getContracts();

    /**
     * Retrieves contracts of a specific contractor.
     * @param contractorsUserID User Id of the contractor of whose the contracts are to be retrieved.
     * @return List of contracts of the specified contractor. Returns empty list if no contract is found for the specified contractor.
     */
    List<Contract> getContractsByContractor(String contractorsUserID);

    /**
     * Retrieves contracts with pagination.
     * @param pageNum Number of the page.
     * @param pageSize Size of the page.
     * @return Paginated Data List of contract.
     */
    PaginatedDataList<Contract> getContracts(int pageNum, int pageSize);

    /**
     * Retrieves contracts filtered by the given Contractor's name.
     * @param contractorsNameFilter Name of Contractor to be applied as filter.
     * @return List of filtered contracts. Returns empty list if no contract is found for the specified filter.
     */
    List<Contract> getContracts(String contractorsNameFilter);

    /**
     * Retrieves contracts filtered by the given Contractor's name with pagination.
     * @param contractorsNameFilter Name of Contractor to be applied as filter.
     * @param pageNum Number of the page.
     * @param pageSize Size of the page.
     * @return Paginated Data List of contract.
     */
    PaginatedDataList<Contract> getContracts(String contractorsNameFilter, int pageNum, int pageSize);

    /**
     * Confirms a specific contract.
     * @param contractId Id of the contract to be confirmed.
     * @throws ObjectNotFoundException Thrown if no contract is found with the specific id.
     * @throws InvalidOperationException Thrown if the confirm operation is invalid.
     * @throws OperationAlreadyExecutedException Thrown if the contract is already confirmed.
     */
    void confirmContract(String contractId) throws ObjectNotFoundException, InvalidOperationException, OperationAlreadyExecutedException;

    /**
     * Extends a specific contract.
     * @param contractId Id of the contract to be extended.
     * @param newEndDate New End Date after the extension.
     * @throws ObjectNotFoundException Thrown if no contract is found with the specific id.
     * @throws ValidationException Thrown if the newEndDate parameter value if invalid for extension.
     * @throws InvalidOperationException Thrown if the extension operation is invalid.
     */
    void extendContract(String contractId, LocalDate newEndDate) throws ObjectNotFoundException, ValidationException,  InvalidOperationException;

    /**
     * Terminates a specific contract.
     * @param contractId Id of the contract to be terminated.
     * @param newEndDate New End Date after the termination.
     * @throws ObjectNotFoundException Thrown if no contract is found with the specific id.
     * @throws ValidationException Thrown if the newEndDate parameter value if invalid for termination.
     * @throws InvalidOperationException Thrown if the termination operation is invalid.
     */
    void terminateContract(String contractId, LocalDate newEndDate) throws ObjectNotFoundException, ValidationException, InvalidOperationException;
}

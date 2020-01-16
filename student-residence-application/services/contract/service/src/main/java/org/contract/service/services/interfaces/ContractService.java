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
 * class manages all the end points of contract service.
 *
 */
public interface ContractService {
    /**
     *  Method to create a new contract.
     *  Users only with Admin role can invoke this method.
     *
     *  @param  newContract  a contract object giving the information needed to create a contract
     *  @param  contextUserId
     *  @return      a newly created Contract
     */
    Contract createContract(NewContract newContract, String contextUserId) throws ValidationException, InvalidOperationException;

    /**
     *  Method to retrieve a particular contract.
     *  Users only with Admin and Resident roles can invoke this method.
     *  Resident users can retreive only their contracts.
     *
     *  @param  contractId  ID of the contract to be retreived
     *  @return   specific Contract with the given contractId
     */
    Contract getContract(String contractId) throws ObjectNotFoundException;

    /**
     *  Method to retrieve multiple contracts.
     *  Use allowed query parameters to filter and pagination.
     *  Users only with Admin role can invoke this method.
     *
     *  @return   list of contracts
     */
    List<Contract> getContracts();

    /**
     *  Method to retrieve contracts of a particular contractor.
     *
     *  @param  contractorsUserID  ID of the resident user of whom the contracts to be retreived
     *  @return   list of contracts of a particular contractor.
     */

    List<Contract> getContractsByContractor(String contractorsUserID);

    /**
     *  Method to retrieve Paginated multiple contracts.
     *  Use allowed query parameters to filter and pagination.
     *  Users only with Admin role can invoke this method.
     *
     *  @return   list of contracts
     */
    PaginatedDataList<Contract> getContracts(int pageNum, int pageSize);

    /**
     *  Method to retrieve filtered contracts using contractors name filter.
     *  Users only with Admin role can invoke this method.
     *
     *  @return   list of filtered contracts
     */
    List<Contract> getContracts(String contractorsNameFilter);

    /**
     *  Method to retrieve Paginated multiple contracts with filter.
     *  Users only with Admin role can invoke this method.
     *
     *  @return   list of contracts
     */
    PaginatedDataList<Contract> getContracts(String contractorsNameFilter, int pageNum, int pageSize);

    /**
     *  Method to confirm a particular contract.
     *  Users only with Resident role can invoke this method.
     *
     *  @param  contractId  ID of the contract to be retreived and confirmed.
     */
    void confirmContract(String contractId) throws ObjectNotFoundException, InvalidOperationException, OperationAlreadyExecutedException;

    /**
     *  Method to extend a particular contract.
     *  Users only with Resident role can invoke this method.
     *
     *  @param  contractId  ID of the contract to be retreived and extend.
     *  @param  newEndDate  new extension date.
     */
    void extendContract(String contractId, LocalDate newEndDate) throws ObjectNotFoundException, ValidationException,  InvalidOperationException;

    /**
     *  Method to terminate a particular contract.
     *  Users only with Resident role can invoke this method.
     *
     *  @param  contractId  ID of the contract to be retreived and terminate.
     *  @param  newEndDate  new termination date.
     */
    void terminateContract(String contractId, LocalDate newEndDate) throws ObjectNotFoundException, ValidationException, InvalidOperationException;
}

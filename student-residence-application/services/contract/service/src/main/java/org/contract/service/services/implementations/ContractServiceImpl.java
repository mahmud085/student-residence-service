package org.contract.service.services.implementations;

import com.google.inject.Inject;
import javassist.tools.rmi.ObjectNotFoundException;
import org.contract.dataaccess.exceptions.PaginationRangeOutOfBoundException;
import org.contract.dataaccess.data.models.Contract;
import org.contract.dataaccess.models.ContractStatus;
import org.contract.dataaccess.models.PaginatedDataList;
import org.contract.dataaccess.respositories.interfaces.ContractRepository;
import org.contract.service.exceptions.InvalidOperationException;
import org.contract.service.exceptions.ValidationException;
import org.contract.service.helpers.DateHelper;
import org.contract.service.helpers.ValidationHelper;
import org.contract.service.models.NewContract;
import org.contract.service.services.interfaces.ContractService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public class ContractServiceImpl implements ContractService {
    @Inject
    private ContractRepository contractRepository;

    @Override
    public Contract createContract(NewContract newContract) throws ValidationException {
        ValidationHelper<NewContract> validationHelper = new ValidationHelper();
        validationHelper.validate(newContract);

        if (newContract.getEndDate() != null && newContract.getEndDate().isBefore(newContract.getStartDate())) {
            throw new ValidationException("End Date is not valid.");
        }

        Contract contract = new Contract() {
            {
                setContractId(UUID.randomUUID().toString());
                setContractorsName(newContract.getContractorsName());
                setContractorsEmail(newContract.getContractorsEmail());
                setContractorsPhone(newContract.getContractorsPhone());
                setRoomNumber(newContract.getRoomNumber());
                setStartDate(newContract.getStartDate());
                setEndDate(newContract.getEndDate());
                setStatus(newContract.getStatus());
                setCreatedOn(DateHelper.getCurrentDate());
            }
        };

        return contractRepository.add(contract);
    }

    @Override
    public Contract getContract(String contractId) {
        return contractRepository.get(contractId);
    }

    @Override
    public List<Contract> getContracts() {
        return contractRepository.getAll();
    }

    @Override
    public PaginatedDataList<Contract> getContracts(int pageNum, int pageSize) throws PaginationRangeOutOfBoundException {
        return contractRepository.getAll(pageNum, pageSize);
    }

    @Override
    public List<Contract> getContracts(String contractorsNameFilter) {
        return contractRepository.getAll(contractorsNameFilter);
    }

    @Override
    public PaginatedDataList<Contract> getContracts(String contractorsNameFilter, int pageNum, int pageSize) throws PaginationRangeOutOfBoundException {
        return contractRepository.getAll(contractorsNameFilter, pageNum, pageSize);
    }

    @Override
    public void confirmContract(String contractId) throws ObjectNotFoundException, InvalidOperationException {
        Contract contract = contractRepository.get(contractId);

        if (contract == null) {
            throw new ObjectNotFoundException("No Contract found for specified Contract ID.");
        }

        LocalDate currentDate = DateHelper.getCurrentDate();

        // Business logic
        LocalDate dateTwoWeeksAfterCreation = contract.getCreatedOn().plusWeeks(2);
        if (currentDate.isAfter(dateTwoWeeksAfterCreation)) {
            // invalid confirmation
            throw new InvalidOperationException("");
        }

        contract.setStatus(ContractStatus.Confirmed);
        contractRepository.update(contract);
    }

    @Override
    public void extendContract(String contractId, LocalDate newEndDate) throws ObjectNotFoundException, ValidationException, InvalidOperationException {
        Contract contract = contractRepository.get(contractId);

        if (contract == null) {
            throw new ObjectNotFoundException("No Contract found for specified Contract ID.");
        }

        LocalDate currentDate = DateHelper.getCurrentDate();
        LocalDate currentEndDate = contract.getEndDate();

        if (newEndDate == null || newEndDate.isBefore(currentDate) || newEndDate.isEqual(currentEndDate) || newEndDate.isBefore(currentEndDate)) {
            throw new ValidationException("Invalid End Date");
        }

        // Business logic
        LocalDate dateSixMonthsAfterCurrentEndDate = currentEndDate.plusMonths(6);
        if (newEndDate.isAfter(dateSixMonthsAfterCurrentEndDate)) {
            // invalid extension
            throw new InvalidOperationException("");
        }

        LocalDate dateThreeMonthsBeforeCurrentEndDate = currentEndDate.plusMonths(-3);
        if (currentDate.isAfter(dateThreeMonthsBeforeCurrentEndDate)) {
            // invalid extension operation
            throw new InvalidOperationException("");
        }

        contract.setEndDate(newEndDate);
        contractRepository.update(contract);
    }

    @Override
    public void terminateContract(String contractId, LocalDate newEndDate) throws ObjectNotFoundException, ValidationException, InvalidOperationException {
        Contract contract = contractRepository.get(contractId);

        if (contract == null) {
            throw new ObjectNotFoundException("No Contract found for specified Contract ID.");
        }

        LocalDate currentDate = DateHelper.getCurrentDate();
        LocalDate currentEndDate = contract.getEndDate();

        if (newEndDate == null || newEndDate.isBefore(currentDate) || newEndDate.isEqual(currentEndDate) || newEndDate.isAfter(currentEndDate)) {
            throw new ValidationException("Invalid End Date");
        }

        // Business logic
        LocalDate dateThreeMonthsBeforeNewEndDate = newEndDate.plusMonths(-3);
        if (currentDate.isAfter(dateThreeMonthsBeforeNewEndDate)) {
            // invalid extension operation
            throw new InvalidOperationException("");
        }

        contract.setEndDate(newEndDate);
        contractRepository.update(contract);
    }
}

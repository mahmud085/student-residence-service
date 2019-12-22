package org.contract.service.services.implementations;

import com.google.inject.Inject;
import org.contract.common.Messages;
import org.contract.common.exceptions.InvalidOperationException;
import org.contract.common.exceptions.ObjectNotFoundException;
import org.contract.common.exceptions.PaginationRangeOutOfBoundException;
import org.contract.common.exceptions.ValidationException;
import org.contract.common.helpers.DateHelper;
import org.contract.common.helpers.ValidationHelper;
import org.contract.dataaccess.data.enums.ContractStatus;
import org.contract.dataaccess.data.models.Contract;
import org.contract.dataaccess.models.PaginatedDataList;
import org.contract.dataaccess.respositories.interfaces.ContractRepository;
import org.contract.dataaccess.respositories.interfaces.RoomRepository;
import org.contract.service.models.NewContract;
import org.contract.service.models.User;
import org.contract.service.models.UserRole;
import org.contract.service.services.interfaces.ContractService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ContractServiceImpl implements ContractService {
    @Inject
    private ContractRepository contractRepository;

    @Inject
    private RoomRepository roomRepository;

    @Override
    public Contract createContract(NewContract newContract, String contextUserId) throws ValidationException, InvalidOperationException {
        LocalDate createdOn = DateHelper.getCurrentDate();

        ValidationHelper<NewContract> validationHelper = new ValidationHelper<>();
        validationHelper.validate(newContract);

        User contractorUser = getUser(newContract.getContractorsUserId());
        if (contractorUser == null) {
            throw new ValidationException(Messages.INVALID_CONTRACTORS_USER_ID);
        }

        if (contractorUser.getRole() != UserRole.Resident) {
            throw new ValidationException(Messages.INVALID_CONTRACTORS_USER_ROLE);
        }

        if (roomRepository.get(newContract.getRoomNumber()) == null) {
            throw new ValidationException(Messages.INVALID_ROOM_NUMBER);
        }

        if (newContract.getStartDate().isBefore(createdOn)) {
            throw new InvalidOperationException(Messages.CONTRACT_CREATION_START_DATE_IN_PAST);
        }

        if (!newContract.getEndDate().isAfter(newContract.getStartDate())) {
            throw new InvalidOperationException(Messages.INVALID_END_DATE);
        }

        if (newContract.getStatus() == ContractStatus.Unconfirmed && !newContract.getStartDate().isAfter(getLastDateForConfirmation(createdOn))) {
            throw new InvalidOperationException(Messages.CONTRACT_CREATION_START_DATE_TOO_EARLY);
        }

        if (contractRepository.getActiveContractByRoomNumberInDateRange(newContract.getRoomNumber(), newContract.getStartDate(), newContract.getEndDate()) != null) {
            throw new InvalidOperationException(Messages.CONTRACT_CREATION_CONTRACT_ALREADY_EXIST_FOR_ROOM);
        }

        Contract contract = new Contract() {
            {
                setContractId(UUID.randomUUID().toString());
                setContractorsName(newContract.getContractorsName().trim());
                setContractorsUserId(newContract.getContractorsUserId());
                setContractorsEmail(newContract.getContractorsEmail().trim());
                setContractorsPhone(newContract.getContractorsPhone().trim());
                setRoomNumber(newContract.getRoomNumber().trim());
                setStartDate(newContract.getStartDate());
                setEndDate(newContract.getEndDate());
                setStatus(newContract.getStatus());
                setCreatedBy(contextUserId);
                setCreatedOn(createdOn);
            }
        };

        return contractRepository.add(contract);
    }

    @Override
    public Contract getContract(String contractId) throws ObjectNotFoundException {
        Contract contract = contractRepository.get(contractId);

        if (contract == null) {
            throw new ObjectNotFoundException(Messages.CONTRACT_NOT_FOUND_WITH_ID);
        }

        return contract;
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
    public List<Contract> getContractsByContractor(String contractorsUserID) {
        return contractRepository.getAllByContractorsUserId(contractorsUserID);
    }

    @Override
    public PaginatedDataList<Contract> getContracts(String contractorsNameFilter, int pageNum, int pageSize) throws PaginationRangeOutOfBoundException {
        return contractRepository.getAll(contractorsNameFilter, pageNum, pageSize);
    }

    @Override
    public void confirmContract(String contractId) throws ObjectNotFoundException, InvalidOperationException {
        Contract contract = contractRepository.get(contractId);

        if (contract == null) {
            throw new ObjectNotFoundException(Messages.CONTRACT_NOT_FOUND_WITH_ID);
        }

        if (contract.getStatus() == ContractStatus.Confirmed) {
            return;
        }

        LocalDate currentDate = DateHelper.getCurrentDate();

        // Business logic
        LocalDate dateTwoWeeksAfterCreation = contract.getCreatedOn().plusWeeks(2);
        if (currentDate.isAfter(dateTwoWeeksAfterCreation)) {
            throw new InvalidOperationException(Messages.CONTRACT_CONFIRMATION_WINDOW_EXPIRED);
        }

        contract.setStatus(ContractStatus.Confirmed);
        contractRepository.update(contract);
    }

    @Override
    public void extendContract(String contractId, LocalDate newEndDate) throws ObjectNotFoundException, ValidationException, InvalidOperationException {
        Contract contract = contractRepository.get(contractId);

        if (contract == null) {
            throw new ObjectNotFoundException(Messages.CONTRACT_NOT_FOUND_WITH_ID);
        }

        LocalDate currentDate = DateHelper.getCurrentDate();
        LocalDate currentEndDate = contract.getEndDate();

        if (newEndDate == null || DateHelper.isDateBeforeOrEqualToday(newEndDate) || newEndDate.isBefore(currentEndDate)) {
            throw new ValidationException(Messages.INVALID_END_DATE);
        }

        // Business logic
        LocalDate dateThreeMonthsBeforeCurrentEndDate = currentEndDate.plusMonths(-3);
        if (currentDate.isAfter(dateThreeMonthsBeforeCurrentEndDate)) {
            throw new InvalidOperationException(Messages.CONTRACT_EXTENSION_INVALID_OPERATION_DATE);
        }

        LocalDate dateSixMonthsAfterCurrentEndDate = currentEndDate.plusMonths(6);
        if (newEndDate.isAfter(dateSixMonthsAfterCurrentEndDate)) {
            throw new InvalidOperationException(Messages.CONTRACT_EXTENSION_INVALID_EXTENSION_PERIOD);
        }

        contract.setEndDate(newEndDate);
        contractRepository.update(contract);
    }

    @Override
    public void terminateContract(String contractId, LocalDate newEndDate) throws ObjectNotFoundException, ValidationException, InvalidOperationException {
        Contract contract = contractRepository.get(contractId);

        if (contract == null) {
            throw new ObjectNotFoundException(Messages.CONTRACT_NOT_FOUND_WITH_ID);
        }

        LocalDate currentDate = DateHelper.getCurrentDate();
        LocalDate currentEndDate = contract.getEndDate();

        if (newEndDate == null || DateHelper.isDateBeforeOrEqualToday(newEndDate) || newEndDate.isAfter(currentEndDate)) {
            throw new ValidationException(Messages.INVALID_END_DATE);
        }

        // Business logic
        LocalDate dateThreeMonthsBeforeNewEndDate = newEndDate.plusMonths(-3);
        if (currentDate.isAfter(dateThreeMonthsBeforeNewEndDate)) {
            throw new InvalidOperationException(Messages.CONTRACT_TERMINATION_INVALID_OPERATION_DATE);
        }

        contract.setEndDate(newEndDate);
        contractRepository.update(contract);
    }

    private LocalDate getLastDateForConfirmation(LocalDate createdOn) {
        return createdOn.plusWeeks(2);
    }

    private User getUser(String userId) {

        // this is dummy data, will be fully implemented after the auth service is ready
        return new User() {
            {
                setUserId(userId);
                setRole(UserRole.Resident);
            }
        };
    }
}

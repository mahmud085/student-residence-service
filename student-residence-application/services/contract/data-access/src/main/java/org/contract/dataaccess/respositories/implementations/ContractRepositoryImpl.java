package org.contract.dataaccess.respositories.implementations;

import org.contract.common.Messages;
import org.contract.common.exceptions.ObjectNotFoundException;
import org.contract.common.exceptions.PaginationRangeOutOfBoundException;
import org.contract.common.helpers.DateHelper;
import org.contract.common.helpers.PaginationHelper;
import org.contract.dataaccess.data.enums.ContractStatus;
import org.contract.dataaccess.data.models.Contract;
import org.contract.dataaccess.models.PaginatedDataList;
import org.contract.dataaccess.respositories.interfaces.ContractRepository;
import org.contract.dataaccess.store.DataStore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContractRepositoryImpl implements ContractRepository {
    private static int id = 0;

    @Override
    public Contract add(Contract contract) {
        contract.setId(getNewId());

        DataStore.contracts.add(contract);

        return contract;
    }

    @Override
    public Contract get(String contractId) {
        Contract contract = DataStore.contracts.stream()
                .filter(x -> x.getContractId().equals(contractId))
                .findFirst()
                .orElse(null);

        return contract != null ? contract.clone() : null;
    }

    @Override
    public List<Contract> getAll() {
        return new ArrayList<>(DataStore.contracts);
    }

    @Override
    public void update(Contract contract) throws ObjectNotFoundException {
        Contract contractToUpdate = DataStore.contracts.stream()
                .filter(x -> x.getContractId().equals(contract.getContractId()))
                .findFirst()
                .orElse(null);

        if (contractToUpdate == null) {
            throw new ObjectNotFoundException(Messages.CONTRACT_NOT_FOUND_WITH_ID);
        }

        contractToUpdate.setContractorsName(contract.getContractorsName());
        contractToUpdate.setContractorsEmail(contract.getContractorsEmail());
        contractToUpdate.setContractorsPhone(contract.getContractorsPhone());
        contractToUpdate.setRoomNumber(contract.getRoomNumber());
        contractToUpdate.setStartDate(contract.getStartDate());
        contractToUpdate.setEndDate(contract.getEndDate());
        contractToUpdate.setContractStatus(contract.getContractStatus());
    }

    @Override
    public Contract getActiveContractByRoomNumberInDateRange(String roomNumber, LocalDate startDate, LocalDate endDate) {
        Contract contract = DataStore.contracts.stream()
                .filter(x -> isContractPending(x) || isContractValidWithInDateRange(x, startDate, endDate))
                .findFirst()
                .orElse(null);

        return contract != null ? contract.clone() : null;
    }

    @Override
    public PaginatedDataList<Contract> getAll(int pageNum, int pageSize) throws PaginationRangeOutOfBoundException {
        List<Contract> contractList = DataStore.contracts;

        return getPaginatedContractList(pageNum, pageSize, contractList);
    }

    @Override
    public List<Contract> getAll(String contractorsNameFilter) {
        return DataStore.contracts
                .stream()
                .filter(x -> containsIgnoreCase(x.getContractorsName(), contractorsNameFilter))
                .collect(Collectors.toList());    }

    @Override
    public PaginatedDataList<Contract> getAll(String contractorsNameFilter, int pageNum, int pageSize) throws PaginationRangeOutOfBoundException {
        List<Contract> filteredContractList = DataStore.contracts
                .stream()
                .filter(x -> containsIgnoreCase(x.getContractorsName(), contractorsNameFilter))
                .collect(Collectors.toList());

        return getPaginatedContractList(pageNum, pageSize, filteredContractList);
    }

    private synchronized int getNewId() {
        return ++id;
    }

    private boolean containsIgnoreCase(String str, String subString) {
        return str.trim().toLowerCase().contains(subString.trim().toLowerCase());
    }

    private PaginatedDataList<Contract> getPaginatedContractList(int pageNum, int pageSize, List<Contract> contractList) throws PaginationRangeOutOfBoundException {
        PaginationHelper paginationHelper = new PaginationHelper(pageNum, pageSize, contractList.size());

        if (paginationHelper.isIndexOutOfRange()) {
            throw new PaginationRangeOutOfBoundException("Pagination attributes exceeds data range.");
        }

        int startIndex = paginationHelper.getStartIndex();
        int endIndex = paginationHelper.getEndIndex();

        return new PaginatedDataList<Contract>() {
            {
                setData(contractList.subList(startIndex, endIndex));
                setTotalDataCount(contractList.size());
            }
        };
    }

    private boolean isContractPending(Contract contract) {
        LocalDate pendingValidTill = contract.getCreatedOn().plusWeeks(2);

        return DateHelper.isDateAfterOrEqualToday(pendingValidTill);
    }

    private boolean isContractValidWithInDateRange(Contract contract, LocalDate startDate, LocalDate endDate) {
        return (contract.getContractStatus() == ContractStatus.Confirmed && isContractPeriodCollide(contract, startDate, endDate));
    }

    private boolean isContractPeriodCollide(Contract contract, LocalDate startDate, LocalDate endDate) {
        return isDateBetweenContractPeriod(contract, startDate)
                || isDateBetweenContractPeriod(contract, endDate);
    }

    private boolean isDateBetweenContractPeriod(Contract contract, LocalDate date) {
        LocalDate contractStartDate = contract.getStartDate();
        LocalDate contractEndDate = contract.getEndDate();

        return (contractStartDate.isEqual(date) || contractStartDate.isBefore(date))
                && (contractEndDate.isEqual(date) || contractEndDate.isAfter(date));
    }
}

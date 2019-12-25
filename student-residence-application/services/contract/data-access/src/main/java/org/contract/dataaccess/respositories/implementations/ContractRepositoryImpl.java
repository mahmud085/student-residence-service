package org.contract.dataaccess.respositories.implementations;

import org.contract.common.Messages;
import org.contract.common.exceptions.ObjectNotFoundException;
import org.contract.dataaccess.data.enums.ContractStatus;
import org.contract.dataaccess.data.models.Contract;
import org.contract.dataaccess.models.PaginatedDataList;
import org.contract.dataaccess.respositories.interfaces.ContractRepository;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

public class ContractRepositoryImpl implements ContractRepository {

    private EntityManagerFactory entityManagerFactory;

    public ContractRepositoryImpl() {
        entityManagerFactory = Persistence.createEntityManagerFactory( "contract_service_jpa" );
    }

    @Override
    public Contract add(Contract contract) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Contract newContract = new Contract();
        newContract.setContractId(contract.getContractId());
        newContract.setCreatedBy(contract.getCreatedBy());
        newContract.setCreatedOn(contract.getCreatedOn());

        mapMutableFields(newContract, contract);

        entityManager.persist(newContract);
        entityManager.getTransaction().commit();

        return newContract;
    }

    @Override
    public Contract get(String contractId) {
        try {
            Query query = entityManagerFactory.createEntityManager().createNamedQuery("Contract.findSingleByContractId");
            query.setParameter("contractId", contractId);

            return (Contract) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Contract> getAll() {
        Query query = entityManagerFactory.createEntityManager().createNamedQuery("Contract.getAll");

        return query.getResultList();
    }

    @Override
    public void update(Contract contract) throws ObjectNotFoundException {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

            Query query = entityManager.createNamedQuery("Contract.findSingleByContractId");
            query.setParameter("contractId", contract.getContractId());

            Contract contractToUpdate = (Contract) query.getSingleResult();
            mapMutableFields(contractToUpdate, contract);

            entityManager.getTransaction().commit();
        } catch (NoResultException ex) {
            throw new ObjectNotFoundException(Messages.CONTRACT_NOT_FOUND_WITH_ID);
        }
    }

    @Override
    public Contract getActiveContractByRoomNumberInDateRange(String roomNumber, LocalDate startDate, LocalDate endDate) {
        LocalDate earliestPossibleCreatedOnForPending = LocalDate.now().minusWeeks(2);

        try {
            Query query = entityManagerFactory.createEntityManager().createNamedQuery("Contract.findActiveContractForRoomByDates");
            query.setParameter("roomNumber", roomNumber);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            query.setParameter("earliestPossibleCreatedOnForPending", earliestPossibleCreatedOnForPending);
            query.setParameter("confirmedStatus", ContractStatus.Confirmed);

            return (Contract) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Contract> getAllByContractorsUserId(String contractorsUserId) {
        Query query = entityManagerFactory.createEntityManager().createNamedQuery("Contract.findMultipleByContractorsUserId");
        query.setParameter("contractorsUserId", contractorsUserId);

        return query.getResultList();
    }

    @Override
    public PaginatedDataList<Contract> getAll(int pageNum, int pageSize) {
        Query countQuery = entityManagerFactory.createEntityManager().createNamedQuery("Contract.getCount");
        Query retrieveQuery = entityManagerFactory.createEntityManager().createNamedQuery("Contract.getAll");
        retrieveQuery.setFirstResult((pageNum - 1) * pageSize);
        retrieveQuery.setMaxResults(pageSize);

        return new PaginatedDataList() {
            {
                setData(retrieveQuery.getResultList());
                setTotalDataCount((int) (long) countQuery.getSingleResult());
            }
        };
    }

    @Override
    public List<Contract> getAll(String contractorsNameFilter) {
        Query query = entityManagerFactory.createEntityManager().createNamedQuery("Contract.filterByContractorsName");
        query.setParameter("contractorsNameFilterText", contractorsNameFilter);

        return query.getResultList();
    }

    @Override
    public PaginatedDataList<Contract> getAll(String contractorsNameFilter, int pageNum, int pageSize) {
        Query countQuery = entityManagerFactory.createEntityManager().createNamedQuery("Contract.getCountFilterByContractorsName");
        countQuery.setParameter("contractorsNameFilterText", contractorsNameFilter);

        Query retrieveQuery = entityManagerFactory.createEntityManager().createNamedQuery("Contract.filterByContractorsName");
        retrieveQuery.setParameter("contractorsNameFilterText", contractorsNameFilter);
        retrieveQuery.setFirstResult((pageNum - 1) * pageSize);
        retrieveQuery.setMaxResults(pageSize);

        return new PaginatedDataList() {
            {
                setData(retrieveQuery.getResultList());
                setTotalDataCount((int) (long) countQuery.getSingleResult());
            }
        };
    }

    private void mapMutableFields(Contract to, Contract from) {
        to.setContractorsUserId(from.getContractorsUserId());
        to.setContractorsName(from.getContractorsName());
        to.setContractorsEmail(from.getContractorsEmail());
        to.setContractorsPhone(from.getContractorsPhone());
        to.setRoomNumber(from.getRoomNumber());
        to.setStartDate(from.getStartDate());
        to.setEndDate(from.getEndDate());
        to.setStatus(from.getStatus());
    }
}

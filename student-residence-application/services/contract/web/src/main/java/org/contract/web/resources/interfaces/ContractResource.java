package org.contract.web.resources.interfaces;

import org.contract.service.models.NewContract;
import org.contract.web.models.ContractUpdateRequest;

import javax.ws.rs.core.Response;

public interface ContractResource {
    Response createContract(NewContract newContract);
    Response getContract(String contractId);
    Response getContracts(String contractorsName, int pageNum, int pageSize);
    Response updateContract(String contractId, ContractUpdateRequest contractUpdateRequest);
}

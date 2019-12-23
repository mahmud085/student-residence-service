package org.contract.web.resources.interfaces;

import javax.ws.rs.core.Response;

public interface ContractorResource {
    Response getContractsOfContractor(String contractorsUserId);
}

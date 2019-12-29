package org.contract.web.helpers;

import org.contract.dataaccess.data.models.Contract;
import org.contract.web.Constants;
import org.contract.web.models.ContractResponse;
import org.contract.web.models.Hateoas;

import java.util.ArrayList;

public class HateoasResponseHelper {
    public static ContractResponse getContractResponse(String baseUri, Contract contract) {
        return new ContractResponse() {
            {
                setId(contract.getId());
                setContractId(contract.getContractId());
                setContractorsUserId(contract.getContractorsUserId());
                setContractorsName(contract.getContractorsName());
                setContractorsEmail(contract.getContractorsEmail());
                setContractorsPhone(contract.getContractorsPhone());
                setRoomNumber(contract.getRoomNumber());
                setStartDate(contract.getStartDate());
                setEndDate(contract.getEndDate());
                setStatus(contract.getStatus());
                setCreatedBy(contract.getCreatedBy());
                setCreatedOn(contract.getCreatedOn());
                setLinks(new ArrayList<Hateoas>(){
                    {
                        add(new Hateoas(){
                            {
                                setHref(String.format("%s%s/%s", baseUri, Constants.RESOURCE_PATH_CONTRACT, contract.getContractId()));
                                setRel("self");
                            }
                        });
                    }
                });
            }
        };
    }
}

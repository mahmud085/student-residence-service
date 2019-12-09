package org.contract.dataaccess.store;

import org.contract.dataaccess.data.models.Contract;
import org.contract.dataaccess.data.models.ContractStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataStore {
    public static List<Contract> contracts = new ArrayList<>();

    public static List<ContractStatus> contractStatuses = Arrays.asList(
      new ContractStatus() {
          {
              setId(1);
              setContractStatusId("Confirmed");
          }
      },
      new ContractStatus() {
          {
              setId(2);
              setContractStatusId("Unconfirmed");
          }
      }
    );
}

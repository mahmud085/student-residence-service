package org.contract.dataaccess.store;

import org.contract.dataaccess.data.enums.ContractStatus;
import org.contract.dataaccess.data.models.Contract;
import org.contract.dataaccess.data.models.Room;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataStore {
    public static List<Contract> contracts = new ArrayList<Contract>() {
        {
            add(new Contract() {
                {
                    setId(1);
                    setContractId("C1");
                    setContractorsUserId("RU1");
                    setContractorsName("Resident 1");
                    setContractorsEmail("resident1@domain.com");
                    setContractorsPhone("+0000000");
                    setRoomNumber("Room_1");
                    setStartDate(LocalDate.parse("2020-01-01"));
                    setEndDate(LocalDate.parse("2020-06-30"));
                    setContractStatus(ContractStatus.Unconfirmed);
                    setCreatedBy("AD1");
                    setCreatedOn(LocalDate.parse("2019-12-01"));
                }
            });

            add(new Contract() {
                {
                    setId(2);
                    setContractId("C2");
                    setContractorsUserId("RU2");
                    setContractorsName("Resident 2");
                    setContractorsEmail("resident2@domain.com");
                    setContractorsPhone("+0000000");
                    setRoomNumber("Room_2");
                    setStartDate(LocalDate.parse("2020-01-01"));
                    setEndDate(LocalDate.parse("2020-06-30"));
                    setContractStatus(ContractStatus.Confirmed);
                    setCreatedBy("AD1");
                    setCreatedOn(LocalDate.parse("2019-12-01"));
                }
            });

            add(new Contract() {
                {
                    setId(3);
                    setContractId("C3");
                    setContractorsUserId("RU3");
                    setContractorsName("Resident 3");
                    setContractorsEmail("resident3@domain.com");
                    setContractorsPhone("+0000000");
                    setRoomNumber("Room_3");
                    setStartDate(LocalDate.parse("2020-01-01"));
                    setEndDate(LocalDate.parse("2020-06-30"));
                    setContractStatus(ContractStatus.Confirmed);
                    setCreatedBy("AD1");
                    setCreatedOn(LocalDate.parse("2019-12-01"));
                }
            });
        }
    };
    public static List<Room> rooms = new ArrayList<Room>() {
        {
            add(new Room() {
                {
                    setId(1);
                    setRoomNumber("Room_1");
                }
            });
            add(new Room() {
                {
                    setId(1);
                    setRoomNumber("Room_2");
                }
            });
            add(new Room() {
                {
                    setId(1);
                    setRoomNumber("Room_3");
                }
            });
        }
    };
}

package org.contract.dataaccess.store;

import org.contract.dataaccess.data.models.Contract;
import org.contract.dataaccess.data.models.Room;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    public static List<Contract> contracts = new ArrayList<>();
    public static List<Room> rooms = new ArrayList<Room>() {
        {
            add(new Room() {
                {
                    setId(1);
                    setNumber("Room_1");
                }
            });
            add(new Room() {
                {
                    setId(1);
                    setNumber("Room_2");
                }
            });
            add(new Room() {
                {
                    setId(1);
                    setNumber("Room_3");
                }
            });
        }
    };
}

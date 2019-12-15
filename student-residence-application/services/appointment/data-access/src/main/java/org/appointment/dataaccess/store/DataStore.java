package org.appointment.dataaccess.store;

import org.appointment.dataaccess.data.models.Appointment;
import org.appointment.dataaccess.data.models.Room;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    public static List<Appointment> appointments = new ArrayList<>();
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

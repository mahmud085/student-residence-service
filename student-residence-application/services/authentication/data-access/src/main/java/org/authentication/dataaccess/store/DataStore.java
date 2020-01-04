package org.authentication.dataaccess.store;

import org.authentication.dataaccess.data.enums.UserType;
import org.authentication.dataaccess.data.models.Authentication;
import org.authentication.dataaccess.data.models.User;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    public static List<User> user = new ArrayList<User>(){
        {
            add(new User() {
                {
                    setId(1);
                    setUserId("hunterbd");
                    setUserName("Sumon");
                    setUserType(UserType.Resident);
                    setPassword("1234");
                }
            });

            add(new User() {
                {
                    setId(2);
                    setUserId("Knowme?");
                    setUserName("Imti");
                    setUserType(UserType.Caretaker);
                    setPassword("ulala");
                }
            });


        }
    };

    public static List<Authentication> authentications = new ArrayList<Authentication>(){
        {
            add(new Authentication() {
                {
                    setId(1);
                    setUserId("Knowme");
                    setAccessToken("lk24s36");
                    //setGeneratedTime("2019-12-26 11:22:32");
                    //setExpiryTime("2019-12-26 14:22:32");
                }
            });


        }
    };
}

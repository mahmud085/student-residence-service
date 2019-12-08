package org.contract.common.helpers;

import org.contract.common.Constants;

import java.time.LocalDate;
import java.util.Date;

public class DateHelper {
    public static LocalDate getCurrentDate() {
        return (new Date()).toInstant()
                .atZone(Constants.DEFAULT_TIME_ZONE_ID)
                .toLocalDate();
    }
}

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

    public static boolean isDateBeforeOrEqualToday(LocalDate date) {
        LocalDate currentDate = DateHelper.getCurrentDate();

        return  date.isBefore(currentDate) || date.isEqual(currentDate);
    }

    public static boolean isDateAfterOrEqualToday(LocalDate date) {
        LocalDate currentDate = DateHelper.getCurrentDate();

        return  date.isAfter(currentDate) || date.isEqual(currentDate);
    }
}

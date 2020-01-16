package org.contract.common.helpers;

import org.contract.common.Constants;

import java.time.LocalDate;
import java.util.Date;

/**
 * helper to check the date is equal or before the current date
 *
 */

public class DateHelper {
    public static boolean isDateBeforeOrEqualToday(LocalDate date) {
        LocalDate currentDate = LocalDate.now();

        return  date.isBefore(currentDate) || date.isEqual(currentDate);
    }
}

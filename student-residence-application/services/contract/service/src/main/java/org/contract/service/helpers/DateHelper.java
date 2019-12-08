package org.contract.service.helpers;

import org.contract.service.ServiceConstants;

import java.time.LocalDate;
import java.util.Date;

public class DateHelper {
    public static LocalDate getCurrentDate() {
        return (new Date()).toInstant()
                .atZone(ServiceConstants.DEFAULT_TIME_ZONE_ID)
                .toLocalDate();
    }
}

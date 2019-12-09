package org.contract.common;

import java.time.ZoneId;

public class Constants {
    public static final int INDEX_OFFSET = 1;
    public static final ZoneId DEFAULT_TIME_ZONE_ID = ZoneId.systemDefault();
    public static final String REGEX_VALID_PHONE = "\"^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\\\s\\\\./0-9]*$\"";
}

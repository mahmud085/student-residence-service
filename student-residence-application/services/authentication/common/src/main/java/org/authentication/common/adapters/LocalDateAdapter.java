package org.authentication.common.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String dateString) {
        if (dateString == null || dateString.isEmpty())
            return null;

        return LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
    }

    @Override
    public String marshal(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }

        return DateTimeFormatter.ISO_DATE.format(localDate);
    }
}

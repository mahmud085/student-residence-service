package org.contract.dataaccess.helpers;

import org.contract.dataaccess.data.models.Entity;

import java.util.Comparator;
import java.util.List;

public class DataHelper {
    public static <T extends Entity> int getNewId(List<T> list) {
        if (list.isEmpty()) {
            return 1;
        }

        list.sort(Comparator.comparingInt(Entity::getId));

        return list.get(0).getId() + 1;
    }
}

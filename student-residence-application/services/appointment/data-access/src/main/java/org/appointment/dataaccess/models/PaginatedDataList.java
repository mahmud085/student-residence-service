package org.contract.dataaccess.models;

import java.util.List;

public class PaginatedDataList<T> {
    private List<T> data;
    private int totalDataCount;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotalDataCount() {
        return totalDataCount;
    }

    public void setTotalDataCount(int totalDataCount) {
        this.totalDataCount = totalDataCount;
    }
}

package org.appointment.common.helpers;

import org.appointment.common.Constants;

public class PaginationHelper {
    private int pageNum;
    private int pageSize;
    private int dataCount;

    public PaginationHelper(int pageNum, int pageSize, int dataCount) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.dataCount = dataCount;
    }

    public int getStartIndex() {
        return (((pageNum - 1) * pageSize) + 1) - Constants.INDEX_OFFSET;
    }

    public int getEndIndex() {
        int actualEndIndex = dataCount - Constants.INDEX_OFFSET;
        int endIndex = (pageNum * pageSize) - Constants.INDEX_OFFSET;

        return Math.min(endIndex, actualEndIndex);
    }

    public boolean isIndexOutOfRange() {
        return getStartIndex() > getEndIndex();
    }
}

package org.contract.dataaccess.helpers;

import org.contract.dataaccess.DataConstants;

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
        return (((pageNum - 1) * pageSize) + 1) - DataConstants.INDEX_OFFSET;
    }

    public int getEndIndex() {
        int actualEndIndex = dataCount - DataConstants.INDEX_OFFSET;
        int endIndex = (pageNum * pageSize) - DataConstants.INDEX_OFFSET;

        return Math.min(endIndex, actualEndIndex);
    }

    public boolean isIndexOutOfRange() {
        return getStartIndex() > getEndIndex();
    }
}

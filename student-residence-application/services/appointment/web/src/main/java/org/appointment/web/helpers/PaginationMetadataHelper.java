package org.appointment.web.helpers;

import org.appointment.web.models.PaginationMetadata;

public class PaginationMetadataHelper {
    private final int currentPageNum;
    private final int pageSize;
    private final int totalDataCount;
    private final String endpointPath;
    private final boolean isPaginationRequested;
    private final int firstPageNum;
    private final int lastPageNum;

    public PaginationMetadataHelper(boolean isPaginationRequested, String endpointPath, int currentPageNum, int pageSize, int totalDataCount) {
        this.isPaginationRequested = isPaginationRequested;
        this.endpointPath = endpointPath;
        this.currentPageNum = currentPageNum;
        this.pageSize = pageSize;
        this.totalDataCount = totalDataCount;
        this.firstPageNum = 1;
        this.lastPageNum = (int) Math.ceil((float) totalDataCount / pageSize);
    }

    public PaginationMetadata buildPaginationMetadata() {
        return new PaginationMetadata() {
            {
                setFirst(isPaginationRequested ? getFirstHref() : null);
                setLast(isPaginationRequested ? getLastHref() : null);
                setPrevious(isPaginationRequested ? getPreviousHref() : null);
                setNext(isPaginationRequested ? getNextHref() : null);
            }
        };
    }

    private String getPreviousHref() {
        int previousPageNum = currentPageNum - 1;

        if (previousPageNum < firstPageNum) {
            return null;
        }

        return buildUrl(previousPageNum);
    }

    private String getNextHref() {
        int nextPageNum = currentPageNum + 1;

        if (nextPageNum > lastPageNum) {
            return null;
        }

        return buildUrl(nextPageNum);
    }

    private String getFirstHref() {
        if (firstPageNum == currentPageNum) {
            return null;
        }

        return buildUrl(firstPageNum);
    }

    private String getLastHref() {
        if (lastPageNum == currentPageNum) {
            return null;
        }

        return buildUrl(lastPageNum);
    }

    private String buildUrl(int pageNum) {
        return String.format("%s?pageNum=%d&pageSize=%d", endpointPath, pageNum, pageSize);
    }
}

package org.contract.web.helpers;

import org.contract.web.models.PaginationMetadata;

import java.util.Map;

public class PaginationMetadataHelper {
    private final int currentPageNum;
    private final int pageSize;
    private final int totalDataCount;
    private final String endpointPath;
    private final boolean isPaginationRequested;
    private final int firstPageNum;
    private final int lastPageNum;
    private final Map<String, String> queryParams;

    public PaginationMetadataHelper(boolean isPaginationRequested, String endpointPath, int currentPageNum, int pageSize, int totalDataCount, Map<String, String> queryParams) {
        this.isPaginationRequested = isPaginationRequested;
        this.endpointPath = endpointPath;
        this.currentPageNum = currentPageNum;
        this.pageSize = pageSize;
        this.totalDataCount = totalDataCount;
        this.firstPageNum = 1;
        this.lastPageNum = (int) Math.ceil((float) totalDataCount / pageSize);
        this.queryParams = queryParams;
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

        if (previousPageNum > lastPageNum) {
            return getLastHref();
        }

        return buildUrl(previousPageNum);
    }

    private String getNextHref() {
        int nextPageNum = currentPageNum + 1;

        if (nextPageNum > lastPageNum) {
            return null;
        }

        if (nextPageNum < firstPageNum) {
            return getFirstHref();
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
        if (lastPageNum < 1 || lastPageNum == currentPageNum) {
            return null;
        }

        return buildUrl(lastPageNum);
    }

    private String buildUrl(int pageNum) {
        return String.format("%s?%spageNum=%d&pageSize=%d", endpointPath, getQueryParamString() , pageNum, pageSize);
    }

    private String getQueryParamString() {
        String queryParamsStr = "";
        if (queryParams == null) {
            return queryParamsStr;
        }

        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            queryParamsStr += String.format("%s=%s&", entry.getKey(), entry.getValue());
        }

        return queryParamsStr;
    }
}

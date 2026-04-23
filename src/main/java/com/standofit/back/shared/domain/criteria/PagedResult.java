package com.standofit.back.shared.domain.criteria;

import java.util.List;

/**
 * Generic paginated response for criteria queries.
 *
 * @param items the list of items
 * @param total the total count of items matching the criteria
 * @param page the current page
 * @param pageSize the page size
 *
 * @author standofit
 * @version 1.0
 */
public record PagedResult<T>(
    List<T> items,
    long total,
    int page,
    int pageSize
) {
    public static <T> PagedResult<T> of(List<T> items, long total, int page, int pageSize) {
        return new PagedResult<>(items, total, page, pageSize);
    }

    public int totalPages() {
        return (int) Math.ceil((double) total / pageSize);
    }

    public boolean hasNext() {
        return page < totalPages() - 1;
    }

    public boolean hasPrevious() {
        return page > 0;
    }

    public boolean isFirst() {
        return page == 0;
    }

    public boolean isLast() {
        return page >= totalPages() - 1;
    }
}
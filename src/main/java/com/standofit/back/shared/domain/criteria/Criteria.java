package com.standofit.back.shared.domain.criteria;

import java.util.List;

/**
 * Criteria for building dynamic queries with filters, ordering, and pagination.
 *
 * Example usage:
 * <pre>
 * var criteria = Criteria.builder()
 *     .filter(Filter.like("name", "full body"))
 *     .filter(Filter.equal("userId", userId))
 *     .order("createdAt", Order.DESC)
 *     .page(0, 10)
 *     .build();
 *
 * repository.searchByCriteria(criteria);
 * </pre>
 *
 * @author standofit
 * @version 1.0
 */
public record Criteria(
    List<Filter> filters,
    String orderBy,
    Order order,
    PageInfo pageInfo
) {
    private static final Criteria EMPTY = new Criteria(List.of(), null, Order.DESC, PageInfo.first());

    public static CriteriaBuilder builder() {
        return new CriteriaBuilder();
    }

    public static Criteria empty() {
        return EMPTY;
    }

    public boolean hasFilters() {
        return filters != null && !filters.isEmpty();
    }

    public boolean hasOrder() {
        return orderBy != null && !orderBy.isBlank();
    }

    public boolean hasPage() {
        return pageInfo != null;
    }

    public int offset() {
        return pageInfo != null ? pageInfo.offset() : 0;
    }

    public int limit() {
        return pageInfo != null ? pageInfo.pageSize() : 10;
    }

    public static class CriteriaBuilder {
        private final java.util.ArrayList<Filter> filters = new java.util.ArrayList<>();
        private String orderBy;
        private Order order = Order.DESC;
        private PageInfo pageInfo = PageInfo.first();

        public CriteriaBuilder filter(Filter filter) {
            this.filters.add(filter);
            return this;
        }

        public CriteriaBuilder filters(List<Filter> filters) {
            this.filters.addAll(filters);
            return this;
        }

        public CriteriaBuilder order(String field, Order direction) {
            this.orderBy = field;
            this.order = direction;
            return this;
        }

        public CriteriaBuilder orderBy(String field) {
            return order(field, Order.DESC);
        }

        public CriteriaBuilder asc(String field) {
            return order(field, Order.ASC);
        }

        public CriteriaBuilder desc(String field) {
            return order(field, Order.DESC);
        }

        public CriteriaBuilder page(int page, int pageSize) {
            this.pageInfo = PageInfo.of(page, pageSize);
            return this;
        }

        public CriteriaBuilder page(PageInfo pageInfo) {
            this.pageInfo = pageInfo;
            return this;
        }

        public Criteria build() {
            return new Criteria(
                List.copyOf(filters),
                orderBy,
                order,
                pageInfo
            );
        }
    }
}
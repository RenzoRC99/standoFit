package com.standofit.back.shared.domain.criteria;

/**
 * Represents a single filter condition for criteria queries.
 *
 * @param field the field name to filter on
 * @param operator the filter operator
 * @param value the value to compare against
 *
 * @author standofit
 * @version 1.0
 */
public record Filter(
    String field,
    FilterOperator operator,
    Object value
) {
    public static Filter equal(String field, Object value) {
        return new Filter(field, FilterOperator.EQUAL, value);
    }

    public static Filter notEqual(String field, Object value) {
        return new Filter(field, FilterOperator.NOT_EQUAL, value);
    }

    public static Filter like(String field, String value) {
        return new Filter(field, FilterOperator.LIKE, value);
    }

    public static Filter gt(String field, Comparable value) {
        return new Filter(field, FilterOperator.GT, value);
    }

    public static Filter lt(String field, Comparable value) {
        return new Filter(field, FilterOperator.LT, value);
    }

    public static Filter gte(String field, Comparable value) {
        return new Filter(field, FilterOperator.GTE, value);
    }

    public static Filter lte(String field, Comparable value) {
        return new Filter(field, FilterOperator.LTE, value);
    }

    public static Filter in(String field, Object value) {
        return new Filter(field, FilterOperator.IN, value);
    }

    public static Filter isNull(String field) {
        return new Filter(field, FilterOperator.IS_NULL, null);
    }

    public static Filter isNotNull(String field) {
        return new Filter(field, FilterOperator.IS_NOT_NULL, null);
    }
}
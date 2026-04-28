package com.standofit.back.shared.domain.criteria;

/**
 * Pagination information for criteria queries.
 *
 * @param page the page number (0-based)
 * @param pageSize the number of items per page
 * @author standofit
 * @version 1.0
 */
public record PageInfo(int page, int pageSize) {
  public static PageInfo of(int page, int pageSize) {
    return new PageInfo(page, pageSize);
  }

  public static PageInfo first() {
    return new PageInfo(0, 10);
  }

  public int offset() {
    return page * pageSize;
  }
}

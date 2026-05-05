package com.standofit.back.api.planning.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.standofit.back.api.planning.dto.FilterRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * SearchWorkoutsRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-05-05T14:22:18.124695369+02:00[Europe/Madrid]", comments = "Generator version: 7.10.0")
public class SearchWorkoutsRequest {

  private String name;

  @Valid
  private List<@Valid FilterRequest> filters = new ArrayList<>();

  private String orderBy;

  /**
   * Gets or Sets order
   */
  public enum OrderEnum {
    ASC("ASC"),
    
    DESC("DESC");

    private String value;

    OrderEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static OrderEnum fromValue(String value) {
      for (OrderEnum b : OrderEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private OrderEnum order = OrderEnum.ASC;

  private Integer page = 0;

  private Integer pageSize = 10;

  public SearchWorkoutsRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  
  @Schema(name = "name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public SearchWorkoutsRequest filters(List<@Valid FilterRequest> filters) {
    this.filters = filters;
    return this;
  }

  public SearchWorkoutsRequest addFiltersItem(FilterRequest filtersItem) {
    if (this.filters == null) {
      this.filters = new ArrayList<>();
    }
    this.filters.add(filtersItem);
    return this;
  }

  /**
   * Get filters
   * @return filters
   */
  @Valid 
  @Schema(name = "filters", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("filters")
  public List<@Valid FilterRequest> getFilters() {
    return filters;
  }

  public void setFilters(List<@Valid FilterRequest> filters) {
    this.filters = filters;
  }

  public SearchWorkoutsRequest orderBy(String orderBy) {
    this.orderBy = orderBy;
    return this;
  }

  /**
   * Get orderBy
   * @return orderBy
   */
  
  @Schema(name = "orderBy", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("orderBy")
  public String getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(String orderBy) {
    this.orderBy = orderBy;
  }

  public SearchWorkoutsRequest order(OrderEnum order) {
    this.order = order;
    return this;
  }

  /**
   * Get order
   * @return order
   */
  
  @Schema(name = "order", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("order")
  public OrderEnum getOrder() {
    return order;
  }

  public void setOrder(OrderEnum order) {
    this.order = order;
  }

  public SearchWorkoutsRequest page(Integer page) {
    this.page = page;
    return this;
  }

  /**
   * Get page
   * @return page
   */
  
  @Schema(name = "page", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("page")
  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public SearchWorkoutsRequest pageSize(Integer pageSize) {
    this.pageSize = pageSize;
    return this;
  }

  /**
   * Get pageSize
   * @return pageSize
   */
  
  @Schema(name = "pageSize", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pageSize")
  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SearchWorkoutsRequest searchWorkoutsRequest = (SearchWorkoutsRequest) o;
    return Objects.equals(this.name, searchWorkoutsRequest.name) &&
        Objects.equals(this.filters, searchWorkoutsRequest.filters) &&
        Objects.equals(this.orderBy, searchWorkoutsRequest.orderBy) &&
        Objects.equals(this.order, searchWorkoutsRequest.order) &&
        Objects.equals(this.page, searchWorkoutsRequest.page) &&
        Objects.equals(this.pageSize, searchWorkoutsRequest.pageSize);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, filters, orderBy, order, page, pageSize);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SearchWorkoutsRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    filters: ").append(toIndentedString(filters)).append("\n");
    sb.append("    orderBy: ").append(toIndentedString(orderBy)).append("\n");
    sb.append("    order: ").append(toIndentedString(order)).append("\n");
    sb.append("    page: ").append(toIndentedString(page)).append("\n");
    sb.append("    pageSize: ").append(toIndentedString(pageSize)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}


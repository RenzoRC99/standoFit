package com.standofit.back.shared.utils;

import java.util.Collection;
import java.util.Map;

public final class CollectionUtils {

  private CollectionUtils() {}

  public static boolean isNullOrEmpty(Collection<?> collection) {
    return collection == null || collection.isEmpty();
  }

  public static boolean isNullOrEmptyForMap(Map<?, ?> map) {
    return map == null || map.isEmpty();
  }
}

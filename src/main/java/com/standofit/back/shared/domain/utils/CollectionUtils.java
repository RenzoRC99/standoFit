package com.standofit.back.shared.domain.utils;

import java.util.Collection;

public final class CollectionUtils {

    private CollectionUtils() {
    }

    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static void requireNonEmpty(Collection<?> collection, String message) {
        if (isNullOrEmpty(collection)) {
            throw new IllegalArgumentException(message);
        }
    }
}

package ru.otus.nyuriv.socialnet.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class ValidationUtil {
    public static void checkNotEmpty(Object o, String argName) {
        if (isEmpty(o)) {
            throw new IllegalArgumentException(String.format("'%s' is empty", argName));
        }
    }

    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        } else if (o instanceof String) {
            return ((String) o).trim().isEmpty();
        } else if (o instanceof Collection<?>) {
            return ((Collection<?>) o).isEmpty();
        } else if (o instanceof Map<?, ?>) {
            return ((Map<?, ?>) o).isEmpty();
        } else if (o.getClass().isArray()) {
            return Array.getLength(o) == 0;
        }
        return false;
    }
}

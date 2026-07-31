package com.netflix.nebula.dependencies.comparison;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class StringUtil {
    private StringUtil(){}
    /**
     * versions can be null if they are project dependencies, which should be considered as "not locked"
     */
    public static boolean isNullOrEmpty(@Nullable String versionString) {
        return versionString == null || versionString.isEmpty();
    }
}

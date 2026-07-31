package com.netflix.nebula.dependencies.comparison;

import org.jspecify.annotations.NullMarked;

import static com.netflix.nebula.dependencies.comparison.StringUtil.isNullOrEmpty;

@NullMarked
public class DependenciesComparisonUtil {
    private DependenciesComparisonUtil() {
    }

    public static boolean hasDiff(Dependencies oldDependencies, Dependencies newDependencies, String dependency) {
        String oldVersion = oldDependencies.usedVersion(dependency);
        String updatedVersion = newDependencies.usedVersion(dependency);
        boolean isPresentSomewhere = (!isNullOrEmpty(oldVersion) || !isNullOrEmpty(updatedVersion));
        if (isPresentSomewhere) {
            boolean isNullSomewhere = oldVersion == null || updatedVersion == null;
            if (isNullSomewhere) {
                return true;
            }
            return !oldVersion.equals(updatedVersion);
        } else {
            return false;
        }
    }
}

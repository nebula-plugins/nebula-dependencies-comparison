package com.netflix.nebula.dependencies.comparison;

import nebula.dependencies.comparison.Dependencies;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class DependenciesComparisonUtilTest {
    @Test
    void test_no_dep_both() {
        Dependencies oldDeps = new Dependencies(Collections.emptyMap());
        Dependencies newDeps = new Dependencies(Collections.emptyMap());
        assertThat(DependenciesComparisonUtil.hasDiff(oldDeps, newDeps, "group:artifact")).isFalse();
    }

    @Test
    void test_missing_in_new() {
        Dependencies oldDeps = new Dependencies(Collections.singletonMap("group:artifact", "1.0.0"));
        Dependencies newDeps = new Dependencies(Collections.emptyMap());
        assertThat(DependenciesComparisonUtil.hasDiff(oldDeps, newDeps, "group:artifact")).isTrue();
    }

    @Test
    void test_missing_in_old() {
        Dependencies oldDeps = new Dependencies(Collections.emptyMap());
        Dependencies newDeps = new Dependencies(Collections.singletonMap("group:artifact", "1.0.0"));
        assertThat(DependenciesComparisonUtil.hasDiff(oldDeps, newDeps, "group:artifact")).isTrue();
    }

    @Test
    void test_same_in_both() {
        Dependencies oldDeps = new Dependencies(Collections.singletonMap("group:artifact", "1.0.0"));
        Dependencies newDeps = new Dependencies(Collections.singletonMap("group:artifact", "1.0.0"));
        assertThat(DependenciesComparisonUtil.hasDiff(oldDeps, newDeps, "group:artifact")).isFalse();
    }

    @Test
    void test_missing_in_old_null_in_new() {
        Dependencies oldDeps = new Dependencies(Collections.emptyMap());
        Dependencies newDeps = new Dependencies(Collections.singletonMap("group:artifact", null));
        assertThat(DependenciesComparisonUtil.hasDiff(oldDeps, newDeps, "group:artifact")).isFalse();
    }

    @Test
    void test_exists_in_old_null_in_new() {
        Dependencies oldDeps = new Dependencies(Collections.singletonMap("group:artifact", "1.0.0"));
        Dependencies newDeps = new Dependencies(Collections.singletonMap("group:artifact", null));
        assertThat(DependenciesComparisonUtil.hasDiff(oldDeps, newDeps, "group:artifact")).isTrue();
    }
}

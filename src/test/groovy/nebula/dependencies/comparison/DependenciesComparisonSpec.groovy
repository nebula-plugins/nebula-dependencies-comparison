/*
 * Copyright 2015-2019 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nebula.dependencies.comparison

import spock.lang.Specification

class DependenciesComparisonSpec extends Specification {
    def 'should diff single project no skew between configurations'() {
        given:
        def old = new ConfigurationsSet([
                "compileClasspath": new Dependencies(["test.nebula:a": "1.0.0"]),
                "runtimeClasspath": new Dependencies(["test.nebula:a": "1.0.0"]),
        ])
        def updated = new ConfigurationsSet([
                "compileClasspath": new Dependencies(["test.nebula:a": "1.1.0"]),
                "runtimeClasspath": new Dependencies(["test.nebula:a": "1.1.0"]),
        ])

        when:
        def result = new DependenciesComparison().performDiff(old, updated)

        then:
        result.size() == 1
        def diff = result.first()
        diff.updated
        diff.updatedDiffString() == "  test.nebula:a: 1.0.0 -> 1.1.0"
    }

    def 'should handle new dependency'() {
        given:
        def old = new ConfigurationsSet([
                "compileClasspath": new Dependencies([:]),
                "runtimeClasspath": new Dependencies([:]),
        ])
        def updated = new ConfigurationsSet([
                "compileClasspath": new Dependencies(["test.nebula:a": "1.0.0"]),
                "runtimeClasspath": new Dependencies(["test.nebula:a": "1.0.0"]),
        ])

        when:
        def result = new DependenciesComparison().performDiff(old, updated)

        then:
        result.size() == 1
        def diff = result.first()
        diff.new
        diff.newDiffString() == "  test.nebula:a: 1.0.0"
    }

    def 'should handle removed dependency'() {
        given:
        def old = new ConfigurationsSet([
                "compileClasspath": new Dependencies(["test.nebula:a": "1.0.0"]),
                "runtimeClasspath": new Dependencies(["test.nebula:a": "1.0.0"]),
        ])
        def updated = new ConfigurationsSet([
                "compileClasspath": new Dependencies([:]),
                "runtimeClasspath": new Dependencies([:]),
        ])


        when:
        def result = new DependenciesComparison().performDiff(old, updated)

        then:
        result.size() == 1
        def diff = result.first()
        diff.removed
        diff.removedDiffString() == "  test.nebula:a"
    }

    def 'should handle multiple configurations'() {
        given:
        def old = new ConfigurationsSet([
                "compileClasspath": new Dependencies(["test.nebula:a": "1.0.0"]),
                "runtimeClasspath": new Dependencies(["test.nebula:a": "1.0.0"]),
                "testCompileClasspath": new Dependencies(["test.nebula:a": "1.0.0", "test.nebula:testlib": "2.0.0"]),
                "testRuntimeClasspath": new Dependencies(["test.nebula:a": "1.0.0", "test.nebula:testlib": "2.0.0"]),
        ])
        def updated = new ConfigurationsSet([
                "compileClasspath": new Dependencies(["test.nebula:a": "1.1.0"]),
                "runtimeClasspath": new Dependencies(["test.nebula:a": "1.1.0"]),
                "testCompileClasspath": new Dependencies(["test.nebula:a": "1.1.0", "test.nebula:testlib": "2.0.2"]),
                "testRuntimeClasspath": new Dependencies(["test.nebula:a": "1.1.0", "test.nebula:testlib": "2.0.2"]),
        ])

        when:
        def result = new DependenciesComparison().performDiff(old, updated)

        then:
        result.size() == 2
        result[0].updated
        result[0].updatedDiffString() == "  test.nebula:a: 1.0.0 -> 1.1.0"
        result[1].updated
        result[1].updatedDiffString() == "  test.nebula:testlib: 2.0.0 -> 2.0.2"
    }

    def 'should handle inconsistent configurations'() {
        given:
        def old = new ConfigurationsSet([
                "compileClasspath": new Dependencies(["test.nebula:a": "1.0.0"]),
                "runtimeClasspath": new Dependencies(["test.nebula:a": "1.0.0"]),
                "testCompileClasspath": new Dependencies(["test.nebula:a": "1.0.0"]),
                "testRuntimeClasspath": new Dependencies(["test.nebula:a": "1.0.0"]),
        ])
        def updated = new ConfigurationsSet([
                "compileClasspath": new Dependencies(["test.nebula:a": "1.1.0"]),
                "runtimeClasspath": new Dependencies(["test.nebula:a": "1.1.0"]),
                "testCompileClasspath": new Dependencies(["test.nebula:a": "1.1.1"]),
                "testRuntimeClasspath": new Dependencies(["test.nebula:a": "1.1.1"]),
        ])

        when:
        def result = new DependenciesComparison().performDiff(old, updated)

        then:
        result.size() == 1
        def diff = result.first()
        diff.inconsistent
        def inconsistent = diff.inconsistentDiffList()
        inconsistent[0] == "  test.nebula:a:"
        inconsistent[1] == "    1.0.0 -> 1.1.0 [compileClasspath,runtimeClasspath]"
        inconsistent[2] == "    1.0.0 -> 1.1.1 [testCompileClasspath,testRuntimeClasspath]"
    }
}

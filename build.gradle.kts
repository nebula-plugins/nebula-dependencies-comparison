plugins {
    id("com.netflix.nebula.root")
    id("com.netflix.nebula.library")
    id("groovy")
}

description = "Library for comparing dependencies in configurations"

group = "com.netflix.nebula"

contacts {
    addPerson("nebula-plugins-oss@netflix.com") {
        moniker = "Nebula Plugins Maintainers"
        github = "nebula-plugins"
    }
}

dependencies {
    api("org.jspecify:jspecify:1.0.0")
    // Use the latest Groovy version for building this library
    implementation("org.codehaus.groovy:groovy-all:3.0.25")

    // Use the awesome Spock testing and specification framework
    testImplementation("org.spockframework:spock-core:2.4-groovy-3.0")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(8)
    }
}

tasks.wrapper {
    distributionType = Wrapper.DistributionType.BIN
    gradleVersion = "9.6.1"
    distributionSha256Sum = "9c0f7faeeb306cb14e4279a3e084ca6b596894089a0638e68a07c945a32c9e14"
}
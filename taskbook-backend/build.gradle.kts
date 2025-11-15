import com.diffplug.spotless.LineEnding
import java.io.File

plugins {
    id("java")
    alias(libs.plugins.spotless)
    alias(libs.plugins.spring.boot)
}

group = "io.github.malczuuu.taskbook"

/**
 * In order to avoid hardcoding snapshot versions, we derive the version from the current Git commit hash. For CI/CD add
 * -Pversion={releaseVersion} parameter to match Git tag.
 */
version =
    if (version == "unspecified")
        getSnapshotVersion(File(rootProject.rootDir, ".."))
    else
        version

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(libs.spring.boot.dependencies))
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.web)

    implementation(libs.micrometer.registry.prometheus)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.security.test)
    testRuntimeOnly(libs.junit.platform.launcher)

    annotationProcessor(libs.spring.boot.configuration.processor)

    runtimeOnly(libs.mariadb.java.client)

    implementation(libs.java.jwt)
    implementation(libs.problem4j.spring.webmvc)

    testRuntimeOnly(libs.h2)
}
spotless {
    format("misc") {
        target("**/*.gradle.kts", "**/.gitattributes", "**/.gitignore")

        trimTrailingWhitespace()
        leadingTabsToSpaces(4)
        endWithNewline()
        lineEndings = LineEnding.UNIX
    }

    format ("yaml") {
        target ("**/*.yml", "**/*.yaml")

        trimTrailingWhitespace()
        leadingTabsToSpaces(2)
        endWithNewline()
    }

    java {
        target("src/**/*.java")
        forbidWildcardImports()

        googleJavaFormat("1.28.0")
        lineEndings = LineEnding.UNIX
    }
}

/**
 * Usage:
 *   ./gradlew printVersion
 */
tasks.register("printVersion") {
    description = "Prints the current project version to the console"
    group = "help"
    doLast {
        println("${project.name} version: ${project.version}")
    }
}

/**
 * There's no need for a plain JAR.
 */
tasks.named<Jar>("jar") {
    enabled = false
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

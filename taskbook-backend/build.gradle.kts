import com.diffplug.spotless.LineEnding
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("java")
    alias(libs.plugins.spotless)
    alias(libs.plugins.spring.boot)
}

group = "io.github.malczuuu.taskbook"

//
// Not assigning version as it is assigned in CI/CD using -Pversion=X.Y.Z parameter.
//

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
    implementation(libs.spring.boot.starter.webmvc)

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
    java {
        target("**/src/**/*.java")

        googleJavaFormat("1.28.0")
        forbidWildcardImports()
        endWithNewline()
        lineEndings = LineEnding.UNIX
    }

    sql {
        target("**/src/main/resources/**/*.sql")

        dbeaver()
        endWithNewline()
        lineEndings = LineEnding.UNIX
    }

    kotlin {
        target("**/src/**/*.kt")

        ktfmt("0.59").metaStyle()
        endWithNewline()
        lineEndings = LineEnding.UNIX
    }

    kotlinGradle {
        target("**/*.gradle.kts")

        ktlint("1.8.0").editorConfigOverride(mapOf("max_line_length" to "120"))
        endWithNewline()
        lineEndings = LineEnding.UNIX
    }

    format("misc") {
        target("**/.gitattributes", "**/.gitignore")

        trimTrailingWhitespace()
        leadingTabsToSpaces(4)
        endWithNewline()
        lineEndings = LineEnding.UNIX
    }

    format("yaml") {
        target("**/src/**/*.yml", "**/src/**/*.yaml")

        trimTrailingWhitespace()
        leadingTabsToSpaces(2)
        endWithNewline()
        lineEndings = LineEnding.UNIX
    }
}

// Utility to clean up old jars as they can clutter.
// Usage:
//   ./gradlew cleanLibs
tasks.register<Delete>("cleanLibs") {
    description = "Deletes build/libs directory."
    group = "build"

    delete(layout.buildDirectory.dir("libs"))
}

// Usage:
//   ./gradlew printVersion
tasks.register<DefaultTask>("printVersion") {
    description = "Prints the current project version to the console."
    group = "help"

    val projectName = project.name
    val projectVersion = project.version.toString()

    doLast {
        println("$projectName version: $projectVersion")
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-parameters")
}

tasks.withType<Jar>().configureEach {
    dependsOn("cleanLibs")

    if (name != "bootJar") {
        enabled = false
    }

    manifest {
        attributes["Implementation-Title"] = project.name
        attributes["Implementation-Version"] = project.version
        attributes["Build-Jdk-Spec"] = java.toolchain.languageVersion.get().toString()
        attributes["Created-By"] = "Gradle ${gradle.gradleVersion}"
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform {
        if (project.findProperty("containers.enabled")?.toString() == "false") {
            excludeTags("testcontainers")
        }
    }

    testLogging {
        events("passed", "skipped", "failed", "standardOut", "standardError")
        exceptionFormat = TestExceptionFormat.SHORT
        showStandardStreams = true
    }

    // For resolving warnings from mockito.
    jvmArgs("-XX:+EnableDynamicAgentLoading")

    systemProperty("user.language", "en")
    systemProperty("user.country", "US")
}

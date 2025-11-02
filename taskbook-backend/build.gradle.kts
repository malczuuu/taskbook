import com.diffplug.spotless.LineEnding
import java.io.File

plugins {
    id("java")
    id("org.springframework.boot").version("3.5.6")
    id("io.spring.dependency-management").version("1.1.7")
    id("com.diffplug.spotless").version("8.0.0")
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
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("io.micrometer:micrometer-registry-prometheus")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

    implementation("com.auth0:java-jwt:4.5.0")
    implementation("io.github.malczuuu.problem4j:problem4j-spring-webmvc:1.0.1")

    testRuntimeOnly("com.h2database:h2:2.3.232")
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

tasks.register("printVersion") {
    doLast {
        println("Project version: $version")
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

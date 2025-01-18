import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
    kotlin("plugin.jpa") version "1.7.20"

    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
}

allprojects {
    group = "com.giunne"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}


val nonDependenciesProjects = listOf("commons", "giunne-service")
configure(subprojects.filter { it.name !in nonDependenciesProjects }) {

    apply(plugin = "org.springframework.boot") // Spring Boot 플러그인 적용
    apply(plugin = "io.spring.dependency-management")  // Spring 의존성 관리 플러그인 적용
    apply(plugin = "kotlin")  // Kotlin 플러그인 적용
    apply(plugin = "kotlin-spring") // Kotlin-Spring 플러그인 적용(Spring 어노테이션을 Kotlin 클래스에 자동으로 열어주는 역할)

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect") // Kotlin 리플렉션 라이브러리
        implementation("org.springframework.boot:spring-boot-starter") // Spring Boot Starter

        // 테스트
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    kotlin {
        jvmToolchain(21)
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

plugins {
	java
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.fantasmaDux"
version = "0.0.1-SNAPSHOT"
description = "Shopping cart backend with spring boot, spring security, JWT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// === Spring Boot 3.x Starterы (правильные названия) ===
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")  // БЕЗ "mvc"!

	// Spring Security
	implementation("org.springframework.boot:spring-boot-starter-security")

	// === JWT для Spring Boot 3.x/Java 17 ===
	implementation("io.jsonwebtoken:jjwt-api:0.12.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")

	// Для конвертирования сущностей в DTO
	implementation("org.modelmapper:modelmapper:3.2.0")  // Обновите для Java 17

	// === Flyway ===
	implementation("org.flywaydb:flyway-core")

	// === PostgreSQL ===
	runtimeOnly("org.postgresql:postgresql")

	// === Lombok ===
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// === Тесты ===
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

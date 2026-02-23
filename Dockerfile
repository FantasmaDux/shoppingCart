FROM gradle:8.7-jdk17 AS build

LABEL authors="Rus"

WORKDIR /home/gradle/project

# Копируем только файлы конфигурации (settings.gradle, build.gradle и т.п.)
COPY --chown=gradle:gradle build.gradle.kts settings.gradle.kts ./
COPY --chown=gradle:gradle gradle ./gradle

# Кэшируем зависимости
RUN gradle dependencies --no-daemon || true

# Теперь копируем остальной исходный код
COPY --chown=gradle:gradle src ./src

# Сборка Spring Boot JAR
RUN gradle bootJar --no-daemon

# Финальный контейнер
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Копируем собранный .jar из стадии build
COPY --from=build /home/gradle/project/build/libs/*.jar shopping-cart-service.jar

ENTRYPOINT ["java", "-jar", "shopping-cart-service.jar"]
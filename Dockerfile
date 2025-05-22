# Базовый образ с JDK (для Spring Boot)
FROM eclipse-temurin:17-jdk-jammy as builder

# Рабочая директория
WORKDIR /books

# Копируем Gradle файлы для кэширования
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Собираем приложение
RUN ./gradlew clean build

# Финальный образ
FROM eclipse-temurin:17-jre-jammy

WORKDIR /books

# Копируем собранный JAR из builder-образа
COPY --from=builder /books/build/libs/*.jar app.jar

# Открываем порт, на котором работает приложение
EXPOSE 8080

# Команда запуска
ENTRYPOINT ["java", "-jar", "app.jar"]
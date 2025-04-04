# Используем базовый образ с Java (например, OpenJDK 17)
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем JAR-файл приложения в контейнер
COPY build/libs/Weather-0.0.1-SNAPSHOT.jar Weather-0.0.1-SNAPSHOT.jar

# Указываем порт, который будет использовать приложение
EXPOSE 8081

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "Weather-0.0.1-SNAPSHOT.jar"]
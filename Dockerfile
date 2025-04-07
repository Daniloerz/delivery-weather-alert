# Etapa 1: Compilación
FROM gradle:8.5-jdk17 AS build
WORKDIR /app

# Copiar archivos base primero para aprovechar el cache
COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle

# Copiar el resto del código
COPY . /app

# Asegurar permisos de ejecución del gradlew después de copiar todo
RUN chmod +x ./gradlew

# Compilar el proyecto
RUN ./gradlew clean bootJar --no-daemon

# Etapa 2: Imagen final
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

ENV SPRING_PROFILES_ACTIVE=default \
    JAVA_OPTS=""

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8090

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
# ---- Stage 1: Build ----
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# copy pom.xml first for dependency caching
COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline

# copy rest of source
COPY src ./src

# build the jar
RUN mvn clean package -DskipTests

# ---- Stage 2: Run ----
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

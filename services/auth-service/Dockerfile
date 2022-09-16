FROM eclipse-temurin:11-jdk-focal
WORKDIR /app
COPY src ./src
COPY mvnw pom.xml ./
CMD ["./mvnw", "spring-boot:run"]
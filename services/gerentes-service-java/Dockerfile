FROM eclipse-temurin:11-jdk-focal
WORKDIR /app
# COPY .mvn ./.mvn
COPY src ./src
COPY mvnw pom.xml ./
## Debug show all logs
CMD ["./mvnw", "spring-boot:run"]
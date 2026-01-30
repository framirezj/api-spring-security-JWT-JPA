FROM eclipse-temurin:25-jdk-jammy

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# Resolve dependencies primarily
RUN ./mvnw dependency:go-offline

COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose the port
EXPOSE 8080

# Run the jar
CMD ["java", "-jar", "target/security-jwt-jpa-0.0.1-SNAPSHOT.jar"]

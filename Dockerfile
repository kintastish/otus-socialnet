FROM maven:3.6.1-jdk-11-slim as builder

COPY . .

RUN mvn clean package

FROM eclipse-temurin:11-jdk-alpine
COPY --from=builder /target/social-net-1.0-SNAPSHOT.jar /opt/social-net-1.0-SNAPSHOT.jar

EXPOSE 8080
ENTRYPOINT [\
    "java",\
    "-XX:+UseContainerSupport",\
    "-XX:+UseG1GC",\
    "-Xms128m", "-Xmx256m", "-Xss1m",\
    "-jar", "/opt/social-net-1.0-SNAPSHOT.jar"]

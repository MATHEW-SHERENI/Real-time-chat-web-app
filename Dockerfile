# ============================================================
# STAGE 1: BUILD
# Uses a full JDK image to compile your Spring Boot app with Maven.
# This stage is thrown away after the JAR is produced — only the
# final (small) runtime image is kept.
# ============================================================
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

# Copy Maven wrapper + pom first (Docker caches this layer).
# If only your Java code changes, Docker skips re-downloading dependencies.
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline -q

COPY src src
RUN ./mvnw package -DskipTests -q

# ============================================================
# STAGE 2: RUN
# Uses a slim JRE-only image (~150 MB vs ~300+ MB with JDK).
# Only the compiled JAR is copied in — no source code, no Maven.
# ============================================================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Run as a non-root user (security best practice for containers)
RUN addgroup -S zimchat && adduser -S zimchat -G zimchat

COPY --from=build /app/target/zimchat-0.0.1-SNAPSHOT.jar app.jar

USER zimchat

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "app.jar"]

# ---- Base Image with JDK 17 ----
FROM eclipse-temurin:17-jdk AS build

# ---- Set Working Directory ----
WORKDIR /app

# ---- Copy Gradle Wrapper Files ----
COPY gradlew .
COPY gradle gradle

# ---- Copy Project Files ----
COPY build.gradle .
COPY settings.gradle .
COPY . .

# ---- Make Gradle Wrapper Executable ----
RUN chmod +x gradlew

# ---- Build the Application ----
RUN ./gradlew clean build -x test

# ---- Create Minimal Image for Running ----
FROM eclipse-temurin:17-jdk-jammy

# ---- Set Work Directory ----
WORKDIR /app

# ---- Copy the Built Jar ----
COPY --from=build /app/build/libs/*.jar app.jar

# ---- Set the Port (Render automatically detects it) ----
ENV PORT=8080
EXPOSE 8080

# ---- Run the App ----
ENTRYPOINT ["java", "-jar", "app.jar"]

FROM alpine:3.19.1 as build-step
RUN apk update
RUN apk add openjdk17
RUN apk add --no-cache tzdata
RUN apk add maven
ENV TZ=America/Bogota
WORKDIR /app
COPY ./ ./

# Build the application with Maven
RUN mvn clean install

# Run stage
FROM alpine:3.19.1
RUN apk update
RUN apk add openjdk17
RUN apk add --no-cache tzdata
ENV TZ=America/Bogota

# Set working directory for running the app
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build-step /app/target/manage_franchises.jar /app/manage_franchises.jar

EXPOSE 8080
CMD ["java", "-jar", "manage_franchises.jar"]

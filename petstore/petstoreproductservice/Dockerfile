FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

#FROM eclipse-temurin:17-jdk

# Install curl and download the AI agent
#RUN apt-get update && apt-get install -y curl \
 # && curl -Lo applicationinsights-agent.jar https://github.com/microsoft/ApplicationInsights-Java/releases/latest/download/applicationinsights-agent-3.4.13.jar

# Create app directory
#WORKDIR /app

# Copy jar
#COPY target/*.jar app.jar

# Copy the AI agent into the container
#COPY applicationinsights-agent-3.4.13.jar .

# Use APPINSIGHTS_CONNECTION_STRING from environment variable
#ENV APPLICATIONINSIGHTS_CONNECTION_STRING="InstrumentationKey=e526f19c-54fc-4a5d-ae6f-0036ff702711;IngestionEndpoint=https://eastus-8.in.applicationinsights.azure.com/;LiveEndpoint=https://eastus.livediagnostics.monitor.azure.com/;ApplicationId=5d57ddf6-cc5f-410a-b491-f8af806a305c"

# Run the app with AI agent
#ENTRYPOINT ["java", "-javaagent:applicationinsights-agent.jar", "-jar", "app.jar"]

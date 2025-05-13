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

FROM eclipse-temurin:17-jdk

# Install curl and download the AI agent
RUN apt-get update && apt-get install -y curl \
  && curl -Lo applicationinsights-agent.jar https://github.com/microsoft/ApplicationInsights-Java/releases/latest/download/applicationinsights-agent-3.4.13.jar

# Create app directory
WORKDIR /app

# Copy jar
COPY target/*.jar app.jar

# Copy Application Insights agent from local folder
COPY agent/applicationinsights-agent-3.7.2.jar /app/applicationinsights-agent-3.7.2.jar

# Use APPINSIGHTS_CONNECTION_STRING from environment variable
ENV APPLICATIONINSIGHTS_CONNECTION_STRING="InstrumentationKey=a29a3488-73bc-4aae-afeb-b791f5e3c076;IngestionEndpoint=https://eastus-8.in.applicationinsights.azure.com/;LiveEndpoint=https://eastus.livediagnostics.monitor.azure.com/;ApplicationId=7e1f2d43-10ea-49a4-a58e-830bedfa57bf"

# Run the app with AI agent
ENTRYPOINT ["java", "-javaagent:applicationinsights-agent-3.7.2.jar", "-jar", "app.jar"]

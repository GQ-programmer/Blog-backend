FROM java:8-alpine

# Copy local code to the container image.
WORKDIR /app
COPY ./Blog-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
# Run the web service on container startup.
CMD ["java","-jar","/app/app.jar","--spring.profiles.active=prod"]

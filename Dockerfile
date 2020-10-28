FROM tomcat:9.0
MAINTAINER creazy
COPY target/schedule-update-api-0.0.1-SNAPSHOT.jar /schedule-update-api.jar
CMD ["java", "-jar", "/schedule-update-api.jar"]

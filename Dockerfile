FROM openjdk:8-jdk-alpine
VOLUME /tmp
#ARG JAR_FILE
COPY Generator-all-1.0-SNAPSHOT.jar Generator-all-1.0-SNAPSHOT.jar
RUN mkdir storage && ls -al
CMD java -Djava.security.egd=file:/dev/./urandom -Dserver.port=$PORT -jar /app.jar

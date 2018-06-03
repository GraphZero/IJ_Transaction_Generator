FROM openjdk:8-jdk-alpine
VOLUME /tmp
#ARG JAR_FILE
COPY generator-0.1.0.jar generator-0.1.0.jar
RUN mkdir storage && ls -al
CMD java -Djava.security.egd=file:/dev/./urandom -Dserver.port=$PORT -jar /generator-0.1.0.jar

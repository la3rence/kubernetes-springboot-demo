FROM openjdk:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT java ${JAVA_OPTS} -Duser.timezone=GMT+08 -Djava.security.egd=file:/dev/./urandom -jar /app.jar
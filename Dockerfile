FROM openjdk:19-jdk-alpine
ARG JAR_FILE
ENV APP_ROOT=/app
WORKDIR ${APP_ROOT}
COPY ${JAR_FILE} ${APP_ROOT}/springboot.jar
ENTRYPOINT java ${JAVA_OPTS} -Duser.timezone=GMT+08 -Djava.security.egd=file:/dev/./urandom -jar ${APP_ROOT}/springboot.jar
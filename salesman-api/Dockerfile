FROM openjdk:11-jre
RUN mkdir app
ARG JAR_FILE
ADD /target/${JAR_FILE} /app/salesman-api.jar
WORKDIR /app
ENTRYPOINT java -jar salesman-api.jar
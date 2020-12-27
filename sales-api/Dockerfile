FROM openjdk:11-jre
RUN mkdir app
ARG JAR_FILE
ADD /target/${JAR_FILE} /app/sales-api.jar
WORKDIR /app
ENTRYPOINT java -jar sales-api.jar
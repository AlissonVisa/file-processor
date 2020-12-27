FROM openjdk:11-jre
RUN mkdir app
ARG JAR_FILE
ADD /target/${JAR_FILE} /app/batch-file-reader.jar
WORKDIR /app
ENTRYPOINT java -jar batch-file-reader.jar
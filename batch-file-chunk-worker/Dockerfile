FROM openjdk:11-jre
RUN mkdir app
ARG JAR_FILE
ADD /target/${JAR_FILE} /app/batch-file-chunk-worker.jar
WORKDIR /app
ENTRYPOINT java -jar batch-file-chunk-worker.jar
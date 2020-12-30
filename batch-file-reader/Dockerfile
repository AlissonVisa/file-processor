FROM openjdk:11-jre
RUN mkdir -p app/file-input/data
ARG JAR_FILE
ADD /target/${JAR_FILE} /app/batch-file-reader.jar
ARG SYSTEM_OWNER_USERNAME
RUN ln -sf /bin/bash /bin/sh
RUN useradd -ms /bin/bash ${SYSTEM_OWNER_USERNAME}
USER ${SYSTEM_OWNER_USERNAME}
WORKDIR /app
ENTRYPOINT java -jar batch-file-reader.jar
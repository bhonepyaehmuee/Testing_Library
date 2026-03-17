FROM eclipse-temurin:21-jdk
WORKDIR /app
LABEL maintainer="javaguides.net"
COPY target/library-0.0.1-SNAPSHOT.jar library.jar
ENTRYPOINT ["java", "-jar", "library.jar"]
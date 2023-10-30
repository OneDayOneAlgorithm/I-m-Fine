FROM openjdk:17-jre
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} algoarium-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
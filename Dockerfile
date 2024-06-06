FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ADD target/course1-0.0.2-SNAPSHOT.jar course1-0.0.2-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/course1-0.0.2-SNAPSHOT.jar"]
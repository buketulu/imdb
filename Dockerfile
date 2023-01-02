FROM openjdk:8
ADD target/imdb-1.0.0.jar imdb-1.0.0.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "imdb-1.0.0.jar"]
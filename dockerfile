FROM openjdk:17-oracle

WORKDIR /app

ADD https://github.com/Mari-Bastet/cp2-java/releases/download/v1/cpjava-0.0.1-SNAPSHOT.jar /app/cpjava-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app/cpjava-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080

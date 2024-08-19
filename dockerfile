# Use uma imagem base do OpenJDK
FROM openjdk:17-oracle


WORKDIR /app

ADD https://github.com/Mari-Bastet/cp2-java/releases/download/v1/cpjava-0.0.1-SNAPSHOT.jar /app/cpjava-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app/cpjava-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080



WORKDIR /app

# Baixar o arquivo JAR da URL do GitHub Release
ADD https://github.com/usuario/repositorio/releases/download/v1.0/cpjava-0.0.1-SNAPSHOT.jar /app/cpjava-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app/cpjava-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080

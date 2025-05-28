
 #fichier qui gère la lexecution du webapp dans un conteneur docker(render lexige pour le deployement)
 # Haran Mourno
 # Université de Sherbrooke
 # Version 1.0 - 13 mai 2025
 # IFT287 - Exploitation de BD relationnelles et OO
 #


# Étape 1 : Build avec Maven + JDK 20
FROM maven:3.9-eclipse-temurin-20 AS builder
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package --no-transfer-progress

# Étape 2 : Image Tomcat 10.1.41 avec JDK 21
FROM tomcat:10.1.41-jdk21-temurin

RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=builder /app/target/Final-1.0.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
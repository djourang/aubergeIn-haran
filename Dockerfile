
 #fichier qui gère la lexecution du webapp dans un conteneur docker(render lexige pour le deployement)
 # Haran Mourno
 # Université de Sherbrooke
 # Version 1.0 - 13 mai 2025
 # IFT287 - Exploitation de BD relationnelles et OO
 #


# Étape 1 : Compiler le projet avec Maven
FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Étape 2 : Déployer le fichier WAR dans Tomcat
FROM tomcat:9.0
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=builder /app/target/Final-1.0.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080



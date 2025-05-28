
 #fichier qui gère la lexecution du webapp dans un conteneur docker(render lexige pour le deployement)
 # Haran Mourno
 # Université de Sherbrooke
 # Version 1.0 - 13 mai 2025
 # IFT287 - Exploitation de BD relationnelles et OO
 #


# Étape 1 : Build avec Maven + JDK 20
FROM maven:3.9-eclipse-temurin-20 AS builder
WORKDIR /app

# Copier le projet
COPY pom.xml .
COPY src ./src

# Compiler le projet
RUN mvn clean package --no-transfer-progress

# Étape 2 : Image Tomcat 10.1.11 officielle
FROM tomcat:10.1.11-jdk20-temurin

# Supprimer les apps par défaut
RUN rm -rf /usr/local/tomcat/webapps/*

# Copier le WAR compilé dans ROOT
COPY --from=builder /app/target/Final-1.0.war /usr/local/tomcat/webapps/ROOT.war

# Exposer le port
EXPOSE 8080

CMD ["catalina.sh", "run"]




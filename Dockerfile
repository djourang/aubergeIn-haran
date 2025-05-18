
 #fichier qui gère la lexecution du webapp dans un conteneur docker(render lexige pour le deployement)
 # Haran Mourno
 # Université de Sherbrooke
 # Version 1.0 - 13 mai 2025
 # IFT287 - Exploitation de BD relationnelles et OO
 #


# Étape 1 : on part d'une image officielle Tomcat
FROM tomcat:9.0

# Étape 2 : supprimer les applications par défaut de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Étape 3 : copier ton fichier WAR dans le dossier ROOT
COPY target/Final-1.0.war /usrs/haran/tomcat/
# Étape 4 : exposer le port 8080 (par défaut pour Tomcat)
EXPOSE 8080

# Étape 5 : lancer Tomcat
CMD ["catalina.sh", "run"]


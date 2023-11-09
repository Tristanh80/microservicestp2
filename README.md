# microservicestp2

Tristan HERSENT
William HUANG

Pour deployer les microservices, il faut aller dans chacun d'eux et lancer la compilation maven (mvn clean install).
Une fois cela fait, on se replace a la racine du projet pour lancer :
docker compose up -d --build

Et le tour est joue !

Tous les endpoints disponibles sont au endpoint http://IP:PORT/swagger-ui.html

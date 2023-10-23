export EZYFOX_SERVER_HOME=D:/ezyfox-server
mvn -pl . clean install
mvn -pl test-server-common -Pexport clean install
mvn -pl test-server-app-api -Pexport clean install
mvn -pl test-server-app-entry -Pexport clean install
mvn -pl test-server-plugin -Pexport clean install
cp test-server-zone-settings.xml $EZYFOX_SERVER_HOME/settings/zones/

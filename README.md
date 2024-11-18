# userbase
1. run mvn clean install -DskipTests -f pom.xml
2. run UserBaseApplication (java -jar target/userbase-0.0.1-SNAPSHOT.jar   )
   oracle db takes quite a while to start
   this will also create some test data
4. verify output on endpoint: http://localhost:8080/users

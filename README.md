
# AccessLog File Parser

### To quickly execute with docker
docker pull mysql

docker run --name mysql_wallet -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql

### Once databse is up, just execute the following script
CREATE DATABASE IF NOT EXISTS dbFlyway;

### MYSQL DDL are at /src/main/resources/db/migration/mysql that will be used by flyway

### How to use this api?

java -jar -DstartDate=2017-01-01.00:00:00 -Dduration=daily -Dthreshold=500 target/access-log-parser-1.0.0-SNAPSHOT.jar

java -jar -DstartDate=2017-01-01.00:00:00 -Dduration=hourly -Dthreshold=200 -Daccesslog=file:/path/to/accesslog.file target/access-log-parser-1.0.0-SNAPSHOT.jar
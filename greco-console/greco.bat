rem LOCAL CONFIG
rem SET SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/greco?verifyServerCertificate=false&useSSL=false&requireSSL=false
rem SET SPRING_DATASOURCE_USERNAME=greco
rem SET SPRING_DATASOURCE_PASSWORD=greco

rem AWS CONFIG
SET SPRING_DATASOURCE_URL=jdbc:mysql://aa1s7htdcq7dm4v.cpqjtxuwrakw.us-east-2.rds.amazonaws.com:3306/ebdb
SET SPRING_DATASOURCE_USERNAME=greco
SET SPRING_DATASOURCE_PASSWORD=tubara10
SET SERVER_PORT=5000
cd target
java -jar greco-console.jar
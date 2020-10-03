# users-app
Manipulating users CRUD application.

Prerequisites:
1) Java 11;
2) Running postgresql server on port 5432, with database "mydb", and user with grant priviledges for this db with creds: username="myuser", pwd="123456".

Admin creds to manipulate with data: username=admin, pwd=adminPass.

If you use Maven, you can run the application by using ./mvnw spring-boot:run. 
Alternatively, you can build the JAR file with ./mvnw clean package and then run the JAR file, as follows:
java -jar target/donut-queue-0.0.1-SNAPSHOT.jar



When you run the service, you can use these REST calls from a console :

curl --user admin:adminPass -X DELETE http://localhost:8080/users/{iban} - delete user with a given IBAN if it exists; otherwise do nothing.

curl --user admin:adminPass -X POST -H "Content-Type: application/json" -d "{\"firstName\":\"Jim\", \"lastName\":\"Morrison\", \"iban\":\"DE123123\"}" http://localhost:8080/users - create a new user if there isn't such IBAN in db; else return 400 status.

curl --user admin:adminPass -X PUT -H "Content-Type: application/json" -d "{\"firstName\":\"Jim\", \"lastName\":\"Morrison\", \"iban\":\"DE123123\"}" http://localhost:8080/users - update an existing user if he exists; else return 400 status.

curl --user admin:adminPass http://localhost:8080/users - get list of all users.

curl --user admin:adminPass -X GET -d "iban={iban}" http://localhost:8080/users - get the user with a given iban; if not exists, return null.

## Library service
This is a RESTful API providing a library service implemented using Java and Spring Boot. 

### Prerequisites
You need to have Maven installed on your machine.

### Installation
1. Clone the repo.
   ```sh
   git clone https://github.com/zeinab-dashti/library-service.git
   ```
2. Build the project.
   ```sh
   mvn clean install
   ```
   This command compiles the project and runs all the tests (unit and end-to-end) against an embedded H2 database.
   **Note: you can also execute ```./src/test/scripts/run-tests.sh``` to run the tests.** 
3. Run the application.
   ```sh
   AUTH_USERNAME=[auth-username] AUTH_PASSWORD=[auth-password] mvn spring-boot:run
   ```
   **Note: you need to provide HTTP basic authentication credentials as environment variables ```AUTH_USERNAME``` and
   ```AUTH_PASSWORD```.**
   
   The command above starts Spring boot application on port 8080 connected to an embedded H2 database. 

## API Specification
Once the project is up and running, you can find Swagger UI at ```http://localhost:8080/swagger-ui/```.

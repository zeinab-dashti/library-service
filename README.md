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

## API usage examples
**Note: request samples below are in [HTTPie](https://httpie.io/) format. Let's assume "john" as username and "1234" as password.**
1. Create a book, ```http -a john:1234 POST http://localhost:8080/api/v1/books title="Title 1" author="Author 1" publicationDate="01-01-2020" bookFormat=KINDLE```
2. Get book by ID, ```http -a john:1234 http://localhost:8080/api/v1/books/1```
3. Get all books, ```http -a john:1234 http://localhost:8080/api/v1/books```
4. Delete a book, ```http -a john:1234 DELETE http://localhost:8080/api/v1/books/1```


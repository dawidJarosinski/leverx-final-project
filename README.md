# Leverx Final Project

## Requirements
Before running the project, make sure you have installed:
- **Docker** and **Docker Compose**
- **Maven** (Apache Maven 3.8+)
- **Java 17+**

## Configuration
Before running the application, fill in the `application.properties` file in `main/resources` with the appropriate values:

```properties
spring.application.name=leverxFinalProject

spring.data.redis.host=redis
spring.data.redis.port=6379

spring.datasource.url=jdbc:postgresql://postgres:5432/leverxdb
spring.datasource.username=postgres
spring.datasource.password=<TO_BE_FILLED>

spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

spring.mail.host=smtp.gmail.com
spring.mail.username=<TO_BE_FILLED>
spring.mail.password=<TO_BE_FILLED>
spring.mail.properties.mail.transport.protocol=smtp
spring.mail.properties.mail.smtp.port=587
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true

spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true
spring.sql.init.data-locations=classpath:/data/data.sql
```

To run tests, fill in the `application.properties` file in `test/resources` with the appropriate values:

```properties
spring.datasource.url=jdbc:postgresql://postgres:5432/testdb
spring.datasource.username=postgres
spring.datasource.password=password

spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

spring.mail.host=smtp.gmail.com
spring.mail.username=<TO_BE_FILLED>
spring.mail.password=<TO_BE_FILLED>
spring.mail.properties.mail.transport.protocol=smtp
spring.mail.properties.mail.smtp.port=587
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true

spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true
spring.sql.init.data-locations=classpath:/data-test/data.sql
```

## Building and Running the Application

### 1. Building the Docker Image
Run the following command to build the Docker image for the application:

```sh
mvn compile jib:dockerBuild
```

### 2. Running the Application
After building the image, start the Docker containers with:

```sh
docker compose up
```

The application should now be accessible at the appropriate address specified in the Docker configuration.

## Author
dawidJarosinski


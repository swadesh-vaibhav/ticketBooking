<!-- # Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.0/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.0/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.4.0/reference/web/servlet.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides. -->

# Running the Project
## Docker

### Starting containers
```
docker compose up -d 
```

To run applications locally from command line, comment out events and registration 
sections from the docker-compose file.

### Explore PostGresql
```
docker exec -it ticketbooking-postgres-1 psql -U pluralsight -d pluralsight
```

## Spring

### Seed events data
Run test profile by running:

```
cd events-service
./mvnw spring-boot:run "-Dspring-boot.run.profiles=testdata"
```

### Run events service 

If the events data is already data, subsequent runs on dev machine
can be done using:
```
cd events-service
.\mvnw spring-boot:run
```

### Run registration service 
```
cd registration-service
.\mvnw spring-boot:run
```

## Deployment

### Option 1 - Building docker images using maven 

These commands use specs from the Dockerfile of each service.
```
cd events-service
./mvnw package
docker build -t events-service:0.0.1-SNAPSHOT .
```
```
cd registration-service
./mvnw package
docker build -t registration-service:0.0.1-SNAPSHOT .
```

### Option 2 - Building docker images using Buildpacks through Spring Boot Maven Plugin

These commands identify specs automatically
```
cd events-service
.\mvnw spring-boot:build-image
```
```
cd registration-service
.\mvnw spring-boot:build-image
```

### Option 3 - Building native docker images to be deployed through GraalVM

<i>(Non-functional as of now)</i>
<br/>
Uncomment commented sections of both pom.xml files.

```
cd events-service
.\mvnw -Pnative spring-boot:build-image
```
```
cd registration-service
.\mvnw -Pnative spring-boot:build-image
```

### Hosting the image locally

```
docker compose up -d
```

# Hosting the Frontend

### Install Dependencies
```
cd ticketBookingFrontend
npm install
```

### Host the Dev Frontend locally
```
npm run dev
```
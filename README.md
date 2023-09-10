# Student Registration Service

This project contains a parent service called "Student Registration Service" that manages several Spring Boot applications: "Payment Service," "Reporting Service," and "Student Service." Additionally, it includes a Docker Compose configuration for running a Kafka Docker container, which is essential for inter-service communication.

## Prerequisites

Before you can run the services, ensure you have the following prerequisites installed on your system:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/) (for running Spring Boot applications)
    - **Note:** Apache Maven is not required if you are using IntelliJ IDEA to start the services.

## Getting Started

Follow the steps below to set up and run the services:

### 1. Start Kafka Docker Container

To start the Kafka container, navigate to the project's root directory containing the `docker-compose.yml` file and execute the following command:

`docker-compose up -d`

This command will launch a Kafka container in the background, which is necessary for communication between the services.

### 2. Start Spring Boot Services Using IntelliJ IDEA
If you don't have Apache Maven installed but have IntelliJ IDEA, you can start each Spring Boot service by following these steps:

Open IntelliJ IDEA.
* Navigate to the service's directory you want to start, e.g., payment_service.
* Locate the main class (usually annotated with @SpringBootApplication) in the project.
* Right-click on the main class and select "Run <YourMainClassName>."
* Repeat this step for each service you wish to run.

### 3. Import the Postman Collection
To simplify API testing, you can import the provided Postman collection:

* Locate the Student Management Skiply.postman_collection.json file in the project's root folder.
* Open Postman.
* Click on "Import" in the upper left corner.
* Choose the "Upload Files" option and select the Student Management Skiply.postman_collection.json file.
* The collection will be imported, and you can use it to test the APIs.
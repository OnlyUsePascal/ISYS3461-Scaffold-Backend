# ISYS3461 Microservices Backend

This project is a microservices-based backend architecture for the ISYS3461 course. It demonstrates a distributed system using Spring Boot, Spring Cloud, Kafka, and Docker.

## Architecture Overview

The system consists of the following components:

*   **Service Discovery**: Eureka Server
*   **API Gateway**: Spring Cloud Gateway
*   **Microservices**:
    *   **Alpha Service**: A core business service.
    *   **Beta Service**: Another core business service.
*   **Messaging**: Apache Kafka for asynchronous communication between services.
*   **Database**: PostgreSQL (dedicated instances for Alpha and Beta services).
*   **Monitoring**: Kafka UI for managing and monitoring Kafka clusters.

## Services

| Service | Description | Port |
| :--- | :--- | :--- |
| **Eureka Server** | Service registry for microservices to register and discover each other. | `8761` |
| **API Gateway** | Entry point for the system, routing requests to appropriate services. | `8080` |
| **Alpha Service** | Business logic service A. Uses its own PostgreSQL database. | `8081` |
| **Beta Service** | Business logic service B. Uses its own PostgreSQL database. | `8082` |
| **Kafka** | Message broker for event-driven communication. | `9092` |
| **Kafka UI** | Web UI for Kafka management. | `8888` |
| **Alpha DB** | PostgreSQL database for Alpha Service. | `5442` |
| **Beta DB** | PostgreSQL database for Beta Service. | `5443` |

## Prerequisites

*   **Java 21+** (for local development)
*   **Docker** and **Docker Compose** (for running the full stack)
*   **Maven** (optional, wrapper included)

## Getting Started

### Running with Docker Compose

The easiest way to run the entire system is using Docker Compose.

1.  **Build and Start Services**:
    ```bash
    docker compose -f compose.yml up --build
    ```
    This command will build the images for Alpha, Beta, Eureka, and API Gateway, and start them along with Kafka and PostgreSQL containers.

2.  **Access Services**:
    *   **Eureka Dashboard**: [http://localhost:8761](http://localhost:8761)
    *   **Kafka UI**: [http://localhost:8888](http://localhost:8888)
    *   **API Gateway**: [http://localhost:8080](http://localhost:8080)
    *   **Swagger Doc**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

3.  **Stop Services**:
    ```bash
    docker compose -f compose.yml down
    ```

### Configuration

*   **Environment Variables**: The services use an `.env` file for configuration (e.g., database credentials). Ensure this file exists or the variables are set in your environment.
*   **Kafka Configuration**:
    *   Alpha and Beta services are configured to use `ByteArraySerializer` and `ByteArrayDeserializer` for message values to handle complex objects efficiently via JSON serialization/deserialization manually or via `ObjectMapper`.

## Project Structure

```
.
├── alpha/              # Alpha Service (Source code & Dockerfile)
├── beta/               # Beta Service (Source code & Dockerfile)
├── api_gateway/        # API Gateway Service
├── eureka/             # Eureka Discovery Server
├── compose.yml         # Main Docker Compose file (Infrastructure)
├── compose-main.yml    # Application Docker Compose file (Services)
└── README.md           # Project Documentation
```

## Technologies Used

*   **Spring Boot**: Framework for building Java applications.
*   **Spring Cloud Netflix Eureka**: Service Discovery.
*   **Spring Cloud Gateway**: API Gateway.
*   **Apache Kafka**: Distributed streaming platform.
*   **PostgreSQL**: Relational database.
*   **Docker**: Containerization platform.

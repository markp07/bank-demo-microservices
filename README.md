# Bank Demo Microservices

A demonstration banking application built with Spring Boot microservices architecture, showcasing modern cloud-native patterns and practices.

## Overview

This project demonstrates a microservices-based banking system that includes account management, client management, and transaction processing. The application uses service discovery, API gateway pattern, and containerization to create a scalable and maintainable system.

## Architecture

The application consists of the following microservices:

- **Eureka Microservice**: Service discovery server that registers and locates microservices
- **API Gateway Microservice**: Entry point for all client requests, routes to appropriate services
- **Spring Boot Admin Microservice**: Monitoring and management dashboard for all microservices
- **Accounts Microservice**: Manages bank account operations
- **Clients Microservice**: Handles customer information and management
- **Transactions Microservice**: Processes financial transactions
- **Microservices Utils**: Shared utilities and common code across services

### Technology Stack

- **Spring Boot** 3.5.7 - Main framework for microservices
- **Spring Cloud Netflix Eureka** - Service discovery
- **Spring Cloud Gateway** - API gateway
- **Spring Boot Admin** - Monitoring and administration
- **Swagger/Springfox** 3.0.0 - API documentation
- **Kafka** - Event streaming platform
- **H2 Database** 2.4.240 - In-memory database
- **Docker** - Containerization
- **Maven** - Build and dependency management
- **Lombok** 1.18.42 - Reduces boilerplate code

## Prerequisites

- Java 10 or higher
- Maven 3.x
- Docker and Docker Compose
- Git

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/markp07/bank-demo-microservices.git
cd bank-demo-microservices
```

### Build the Project

```bash
mvn clean install
```

### Run with Docker Compose

```bash
docker-compose up
```

Alternatively, use the provided scripts:

```bash
# Start all services
./start.sh

# Stop all services
./stop.sh

# Restart all services
./restart.sh
```

## Access the Services

Once the application is running, you can access:

- **Eureka Dashboard**: http://localhost:8761
- **Spring Boot Admin**: http://localhost:8762
- **API Gateway**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html

## Development

### Project Structure

```
bank-demo-microservices/
├── AccountsMicroservice/       # Account management service
├── ClientsMicroservice/        # Client management service
├── TransactionsMicroservice/   # Transaction processing service
├── ApiGatewayMicroservice/     # API gateway
├── EurekaMicroservice/         # Service discovery
├── SpringBootAdminMicroservice/# Monitoring dashboard
├── MicroservicesUtils/         # Shared utilities
├── docker-compose.yml          # Docker composition
└── pom.xml                     # Parent POM
```

### Running Tests

```bash
mvn test
```

### Building Individual Services

```bash
cd AccountsMicroservice
mvn clean install
```

## Configuration

Default configuration can be found in `docker-compose.env_default`. You can override these settings by creating a custom environment file.

## CI/CD

The project includes a Bitbucket Pipelines configuration (`bitbucket-pipelines.yml`) for continuous integration and deployment.

## Changelog

See [CHANGELOG.md](CHANGELOG.md) for a detailed list of changes and version history.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the terms of the LICENSE file included in the repository.

## Author

Mark Post

## Acknowledgments

- Spring Boot and Spring Cloud teams for excellent frameworks
- The open-source community for various libraries and tools used in this project


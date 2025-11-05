# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [0.3.0] - 2025-11-02

### Added
- Dependabot configuration for automated dependency management
- Grouped dependency updates for Maven and Docker
- Updated multiple dependencies to latest versions:
  - Lombok upgraded from 1.18.8 to 1.18.42
  - JAXB Core upgraded from 2.3.0 to 4.0.6
  - SpringFox Swagger upgraded to 3.0.0
  - AssertJ Core upgraded from 3.11.1 to 3.27.6
  - Maven Compiler Plugin upgraded from 3.7.0 to 3.14.1
  - Spring Boot Starter Parent upgraded to 3.5.7
  - H2 Database upgraded from 1.4.194 to 2.4.240

## [0.2.0] - 2019-08-14

### Added
- JavaDoc documentation for accounts microservice
- Improved error response handling in clients and transactions microservices

### Changed
- Various Kafka integration improvements

## [0.1.0] - 2019-08-03

### Added
- Spring Boot Admin for monitoring and management
- Swagger UI documentation for API endpoints
- Log file viewing capability in Spring Boot Admin
- Unit test framework and initial tests

### Changed
- Updated Java to version 10

## [0.0.1] - 2019-07-30

### Added
- Initial microservices architecture setup
- Eureka Service Discovery microservice
- API Gateway microservice
- Accounts microservice
- Clients microservice
- Transactions microservice
- Shared utilities module (MicroservicesUtils)
- Docker containerization for all microservices
- Docker Compose configuration with environment defaults
- H2 database integration
- Database models and business logic
- Kafka integration for event streaming
- Bitbucket Pipelines CI/CD configuration
- Initial unit tests

### Changed
- Removed Swagger UI from individual microservices (now available via gateway)
- Code cleanup and import optimization
- Refactored Docker files for better performance
- Improved database container setup
- Connected all services through service discovery

[Unreleased]: https://github.com/markp07/bank-demo-microservices/compare/v0.3.0...HEAD
[0.3.0]: https://github.com/markp07/bank-demo-microservices/compare/v0.2.0...v0.3.0
[0.2.0]: https://github.com/markp07/bank-demo-microservices/compare/v0.1.0...v0.2.0
[0.1.0]: https://github.com/markp07/bank-demo-microservices/compare/v0.0.1...v0.1.0
[0.0.1]: https://github.com/markp07/bank-demo-microservices/releases/tag/v0.0.1

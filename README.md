# Dish Reservation System

Izvorni kod aplikacije napravljene na vežbama iz predmeta Programski jezici na 2. godini Računarskih nauka na Univerzitetu Singidunum.

### About the Project

This application fetches and displays meal recipes from an external API (TheMealDB) and allows users to reserve and order these recipes at manually registered local restaurants. 

The project originally started with a standard Three-Tier architecture, but was later upgraded to a Microservices architecture. It integrates external API data with a local database through a Many-to-Many relationship (handled via the Reservation entity) and uses an API Gateway to handle incoming requests and serve the frontend efficiently.

### Key features include:
* RESTful API design with standardized HTTP methods and status codes
* Mapping external API recipe search results into custom DTO models
* State transition from unconfirmed orders to paid status
* Dynamic QR code generation for paid orders
* Strict input validation and read/write DTO mappings
* Centralized custom exceptions for standardized HTTP error responses
* Asynchronous communication between microservices using RabbitMQ
* System monitoring and real-time metrics using Prometheus and Grafana
* Distributed request tracing across microservices using Zipkin

### Technologies

* **Backend:** Java, Spring Boot (Microservices, API Gateway, Eureka)
* **Database:** MySQL
* **Message Broker:** RabbitMQ
* **Monitoring & Tracing:** Prometheus, Grafana, Zipkin
* **Frontend:** HTML5 / Vanilla JavaScript, Bootstrap 5.3
* **Containerization:** Docker & Docker Compose (Multi-stage builds, Volumes for persistent database storage)
* **Utilities:** SweetAlert2

### How to Run

```bash
docker-compose up -d --build

### Accessing the Services

* **Main Application (API Gateway):** `http://localhost:8080`
* **Eureka Service Registry:** `http://localhost:8761`
* **Grafana (Dashboards):** `http://localhost:3000 (Login: admin / admin) `
* **Zipkin (Distributed Tracing):** `http://localhost:9411`
* **Prometheus (Metrics):** `http://localhost:9090`
* **RabbitMQ Management UI:** `http://localhost:15672 (Login: guest / guest)`
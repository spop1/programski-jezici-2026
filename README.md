# Dish Reservation System

Izvorni kod aplikacije napravljene na vežbama iz predmeta Programski jezici na 2. godini Računarskih nauka na Univerzitetu Singidunum.

# About the Project
This application fetches and displays meal recipes from an external API (TheMealDB) and allows users to reserve and order these recipes at manually registered local restaurants. It features a Three-Tier architecture, integrating external API data with a local database through a Many-to-Many relationship (handled via the Reservation entity).

Key features include:
- RESTful API design with standardized HTTP methods and status codes
- Mapping external API recipe search results into custom DTO models
- State transition from unconfirmed orders to paid status
- Dynamic QR code generation for paid orders
- Strict input validation and read/write DTO mappings
- Centralized custom exceptions for standardized HTTP error responses

# Technologies
- Backend: Java, Spring Boot
- Database: MySQL 
- Frontend: HTML5 / Vanilla JavaScript, Bootstrap 5.3
- Containerization: Docker & Docker Compose (with Volumes for persistent database storage)
- Utilities: SweetAlert2

# How to Run
```bash
docker-compose up -d --build
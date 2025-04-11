# Ride Sharing Microservices Project

This project is a Ride Sharing Application built using Spring Boot Microservices architecture. It provides REST APIs for managing Users, Rides, and Payments.

---

## Technologies Used
- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- MongoDB
- OpenFeign (Service Communication)
- Eureka Server (Service Discovery)
- Spring Cloud Config Server
- Resilience4j (Fault Tolerance)
- Docker & Docker Compose

---

## Microservices Structure

### 1. User Service
Handles User Management:
- Create User
- Get User by Id
- Get All Users

#### Endpoints
| Method | Endpoint | Description |
|--------|-----------|-------------|
| POST   | `/users` | Create a new User |
| GET    | `/users` | Get all Users |
| GET    | `/users/{userId}` | Get User by UserId |
| GET    | `/users/role/driver` | Get User by role |

---

### 2. Ride Service
Handles Ride Management:
- Create Ride
- Get Ride by Id
- Get All Rides
- Update Ride
- Update Ride Status

#### Endpoints
| Method | Endpoint | Description |
|--------|-----------|-------------|
| POST   | `/rides` | Create a new Ride and Payment |
| GET    | `/rides` | Get all Rides |
| GET    | `/rides/{rideId}` | Get Ride by RideId |
| PUT    | `/rides/{rideId}` | Update Ride details |
| PUT    | `/rides/{rideId}/status?status=value` | Update Ride Status (Ex: COMPLETED, CANCELLED, IN_PROGRESS, PENDING_DRIVER_ASSIGNMENT) |

---

### 3. Payment Service
Handles Payment Management:
- Create Payment
- Get Payments (All/User/Ride wise)
- Update Payment Status

#### Endpoints
| Method | Endpoint | Description |
|--------|-----------|-------------|
| POST   | `/payments` | Create a new Payment |
| GET    | `/payments` | Get all Payments |
| GET    | `/payments/users/{userId}` | Get Payments by UserId |
| GET    | `/payments/rides/{rideId}` | Get Payments by RideId |
| PUT    | `/payments/{paymentId}/status?status=value` | Update Payment Status (Ex: COMPLETED, FAILED, PENDING) |

---

## How To Run

### Prerequisites
- Java 17
- Maven
- MySQL (For User & Ride Service)
- MongoDB (For Payment Service)
- Docker (optional for containers)

---

### Steps to Run the Project

1. Clone the Repository  
```bash
git clone https://github.com/kaustubmule/RideSharing
```

2. Setup Databases
MySQL:
Create 2 Databases:

ma_project → for User Service

ride_db → for Ride Service

MongoDB:
Create Database:

microservices → for Payment Service

3. Start Eureka Server

4. Start Config Server

5. Start All Microservices

6. Test APIs
Use Postman / Swagger UI to test all available endpoints.


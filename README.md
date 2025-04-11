# Ride Sharing Microservices Project

This project is a Ride Sharing Application built using Spring Boot Microservices architecture. It provides REST APIs for managing Rides and Payments.

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

### 1. Ride Service
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
| PUT    | `/rides/{rideId}/status?status=value` | Update Ride Status (Ex: COMPLETED, CANCELLED) |

---

### 2. Payment Service
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
| PUT    | `/payments/{paymentId}/status?status=value` | Update Payment Status (Ex: SUCCESS, FAILED) |

---

## How To Run

### Prerequisites
- Java 17
- Maven
- MySQL & MongoDB running
- Docker (optional for containerization)

### Steps
1. Clone the repo  
```bash
git clone [<repo-url>
](https://github.com/kaustubmule/RideSharing)

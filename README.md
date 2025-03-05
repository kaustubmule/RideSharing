# RideSharing
microservices-based ride-sharing application. It comprises four main services:

1. **User Service**
    - Manages users (registration, authentication, roles)
    - CRUD operations for `Users`
    - Stores user details in MySQL/PostgreSQL
    - API endpoints for user registration, login, and role management
2. **Ride Service**
    - Handles ride creation, status tracking
    - CRUD operations for `Ride`
    - Assigns drivers to rides
    - Calculates ride distance
3. **Driver Service**
    - Manages driver details (vehicle, rating, payment method)
    - CRUD operations for `Driver`
    - API to fetch available drivers
4. **Payment Service**
    - Handles transactions and payments
    - CRUD operations for `Payment`
    - Processes payments for rides

User Service
| HTTP Method | Endpoint                  | Description                                     | Controller        |
|------------|--------------------------|-----------------------------------------------|------------------|
| POST       | /auth/login              | Authenticates a user and returns a token.     | LoginController  |
| GET        | /api/users               | Retrieves a list of all users.                | UserController   |
| POST       | /api/users               | Creates a new user.                           | UserController   |
| GET        | /api/users/{id}          | Retrieves details of a specific user.         | UserController   |
| DELETE     | /api/users/{id}          | Deletes a user by ID.                         | UserController   |
| POST       | /api/users/{id}/book-ride| Allows a user to book a ride.                 | UserController   |
| GET        | /api/users/{id}/driver   | Fetches assigned driver details for a user.   | UserController   |
| GET        | /api/users/{id}/role     | Retrieves the role of a specific user.        | UserController   |



Payment Service
| HTTP Method | Endpoint                                      | Description                                                         | Controller         |
|------------|----------------------------------------------|---------------------------------------------------------------------|-------------------|
| POST       | /api/payments                               | Creates a new payment record.                                       | PaymentController |
| GET        | /api/payments/{id}                         | Retrieves details of a specific payment by its ID.                  | PaymentController |
| GET        | /api/payments/{paymentId}/passenger-payments | Fetches passenger payment details associated with the payment.      | PaymentController |


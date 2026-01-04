

# Customer Management System

## Project Overview

The **Customer Management System** is a full-stack web application developed using **Spring Boot** for the backend and **React** for the frontend.
It enables users to manage customer records efficiently and supports bulk customer uploads through Excel files.

This project was implemented as part of an academic assignment with the objective of practicing **full-stack development**, **RESTful APIs**, and **database integration**.

---

## Technologies Used

### Backend

* Java 8
* Spring Boot
* Spring Data JPA
* Hibernate
* Apache POI (Excel file processing)
* MariaDB
* Maven

### Frontend

* React
* Axios
* HTML
* CSS

---

## Features Implemented

* Create new customer records
* View customers with pagination and sorting
* Edit existing customer details
* Upload customers using an Excel file
* Backend validations:

  * NIC format validation
  * Duplicate NIC detection and handling

---

## Project Setup

### Prerequisites

Ensure the following are installed on your system:

* Java 8
* Node.js and npm
* MariaDB
* Maven

---

## Backend Setup

1. Navigate to the backend directory:

   ```bash
   cd backend
   ```

2. Configure database settings in `application.properties`:

   ```properties
   spring.datasource.url=jdbc:mariadb://localhost:3306/cms
   spring.datasource.username=root
   spring.datasource.password=root
   ```

3. Run the backend application:

   ```bash
   mvn spring-boot:run
   ```

4. Backend will be available at:

   ```
   http://localhost:8080
   ```

---

## Frontend Setup

1. Navigate to the frontend directory:

   ```bash
   cd frontend
   ```

2. Install dependencies:

   ```bash
   npm install
   ```

3. Start the frontend application:

   ```bash
   npm start
   ```

4. Frontend will be available at:

   ```
   http://localhost:3000
   ```

---

## REST API Endpoints

| Method | Endpoint                | Description                              |
| ------ | ----------------------- | ---------------------------------------- |
| POST   | `/api/customers`        | Create a new customer                    |
| GET    | `/api/customers`        | Retrieve customers (pagination, sorting) |
| PUT    | `/api/customers/{id}`   | Update an existing customer              |
| POST   | `/api/customers/upload` | Upload customers via Excel file          |

---

## Excel Upload Details

### Required Columns

| Column      | Description                       |
| ----------- | --------------------------------- |
| name        | Customer name                     |
| nic         | Unique NIC                        |
| dateOfBirth | Date of birth (yyyy-mm-dd format) |

### Upload Behavior

* If a NIC already exists, the record is skipped and reported
* Valid records are saved to the database
* Validation errors are returned in the API response

---

## Database Structure

### Tables Used

* `customer`
* `customer_mobile`
* `address`
* `country`
* `city`

---

## Design Notes

* NIC is enforced as a unique identifier
* Most validations are handled on the backend
* Pagination and sorting are implemented using Spring Data
* Frontend communicates with backend using REST APIs

---

## Limitations

* City and country are not fully integrated into the frontend UI
* No authentication or authorization implemented
* Basic UI styling
* Some entities are implemented but not fully utilized in the UI

---

## Conclusion

This project demonstrates a foundational full-stack application using **Java 8**, **Spring Boot**, and **React**.
The primary focus was to satisfy academic requirements by implementing CRUD operations, REST APIs, database integration, and Excel-based bulk data uploads.

---

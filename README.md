# Hospital Patient Record System

## Project Overview
The Hospital Patient Record System is a web-based application developed using Java Spring Boot. It helps manage patient information securely and efficiently.

The system allows authorized users to add, view, search, update, and delete patient records with role-based access control.

## Technologies Used
- Java 21
- Spring Boot 4.1.2
- Spring MVC
- Spring Data JPA
- Spring Security
- MySQL
- Thymeleaf
- HTML
- CSS
- Bootstrap 5
- Maven

## Features

### Patient Management
- Add new patient records
- View all patient records
- Search patients by name
- Update patient information
- Delete patient records

### Security Features
- Admin and Staff login
- Role-based access control
- Password encryption using BCrypt
- CSRF protection
- Staff cannot delete patient records
- Medical History is accessible only to Admin
- Staff cannot modify Medical History

### Form Validation
- Full Name is required
- Age must be between 1 and 120
- Valid 10-digit Indian mobile number
- Gender and Address validation

## User Roles

**Admin**
- Add, view, search, update and delete patient records
- View and update Medical History

**Staff**
- Add, view, search and update patient records
- Cannot delete patient records
- Cannot view or modify Medical History

## Database
Database: `hospital_db`

Table: `patients`

The application uses Spring Data JPA and Hibernate for database operations.

## Installation and Setup

1. Clone the repository.
2. Import the project into Spring Tools for Eclipse as an Existing Maven Project.
3. Create a MySQL database named `hospital_db`.
4. Configure your MySQL username and password in `application.properties`.
5. Update Maven dependencies.
6. Run the Spring Boot application.

Open the application in your browser:

`http://localhost:8080/login`

## Testing
The following features have been tested:
- Admin and Staff authentication
- Patient CRUD operations
- Patient search
- Role-based access restrictions
- Medical History security
- Form validation

## Author
**Sonali Hiwale**

MCA Graduate | Java Developer

## License
This project is developed for educational and learning purposes.

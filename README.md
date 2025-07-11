# Job Application Tracker

The Job Application Tracker is a backend web application built using Spring Boot. It provides role-based access for customers (job seekers), employers, and administrators. Users can register, log in, and perform role-specific actions such as job posting, job searching, and application tracking.

This project is designed with a modular architecture, includes secure JWT-based authentication, and uses Spring Data JPA for database interactions.

---

## Technology Stack

- Java 17
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA
- MySQL
- ModelMapper
- Lombok

---

## Roles and Capabilities

**Customer**
- Register and log in
- View job listings
- Apply for jobs
- View applied jobs and track application status

**Employer**
- Register and log in
- Create and manage job postings
- View applicants for posted jobs
- Update application status (e.g., shortlisted, rejected, offered)

**Admin**
- View statistics such as total jobs posted and users registered
- Access platform-wide data including all users and job postings

---

## Key Features

- User registration and authentication using JSON Web Tokens (JWT)
- Role-based access control using Spring Security
- Secure and structured RESTful API design
- Filtering, searching, pagination, and sorting of job listings
- Centralized exception handling and validation
- Clear separation of concerns with layered architecture (Controller, Service, Repository, DTO)

---

## Author

- [Jeevitha Shree T](https://github.com/JeevithaShreeT)

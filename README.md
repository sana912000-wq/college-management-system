swagger url : http://localhost:8080/swagger-ui/index.html

🎓 College Management System (LMS Backend)

🚀 Project Overview

This is a full-fledged College Management System Backend developed using Spring Boot.
The system supports Student, Faculty, and Admin roles with complete functionality including authentication, course management, attendance tracking, fee management, and more.

🔗 Deployed Backend URL:

📁 Postman Collection:
College Management System.postman_collection.json

swagger url : http://localhost:8080/swagger-ui/index.html

🔐 Authentication & Security
•	JWT-based Authentication
•	Role-Based Access Control (RBAC)
•	Roles:
•	STUDENT
•	FACULTY
•	ADMIN
•	Secure Password Hashing (BCrypt)
•	Forgot Password (Email Token)
•	Reset Password
•	Change Password

🧩 Modules Implemented
👨‍🎓 Student Module
•	Student Registration & Login
•	Profile Management
•	Course Enrollment
•	View Attendance
•	View Marks
•	Fee Payment

👩‍🏫 Faculty Module
•	Faculty Registration & Login
•	Manage Courses
•	Mark Attendance
•	Upload Marks

🛠️ Admin Module
•	Manage Students, Faculty
•	Create Subjects & Courses
•	Announcements Management
•	Fee Structure Management
•	System Control

📚 Subject & Course Module
•	Create Subjects
•	Assign Faculty
•	Manage Courses

📝 Attendance Module
•	Mark Attendance
•	Track Student Attendance

📊 Marks Module
•	Add Marks
•	Calculate Grades

💰 Fee Management
•	Fee Structure Creation
•	Fee Payment Tracking

📢 Announcement Module
•	Create Announcements
•	Target Audience (Student / Faculty / All)

🏗️ Project Structure
•	Controller Layer (REST APIs)
•	Service Layer (Business Logic)
•	Repository Layer (JPA)
•	DTO Layer (Data Transfer Objects)
•	Entity Layer (Database Models)

🛠️ Tech Stack
•	Java 17+
•	Spring Boot 3+
•	Spring Security
•	Spring Data JPA (Hibernate)
•	PostgreSQL
•	JWT Authentication
•	Swagger (OpenAPI)
•	Maven

Complete Authentication System
•	Multi-role Support (Student, Faculty, Admin)
•	Secure APIs with JWT
•	Full CRUD Operations for all modules
•	Clean layered architecture

🔮 Future Improvements
•	Unit Testing (JUnit)
•	Frontend Integration continuation (Angular/React)
•	Docker Deployment
•	CI/CD Pipeline
•	Extending Email Notification Enhancements
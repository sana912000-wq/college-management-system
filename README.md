🎓 **College Management System (LMS Backend)**

🚀 **Project Overview**
This is a full-fledged College Management System Backend developed using Spring Boot.
The system supports Student, Faculty, and Admin roles with complete functionality including authentication, course management, attendance tracking, fee management, and more.

🔗 **Deployed Backend URL**
API Base URL:  https://college-management-system-1-14of.onrender.com

This is a Spring Boot REST API deployed on Render. Use Postman to test the endpoints.
for testing or example :
student register API :POST /api/auth/student/register
FULL URL : https://college-management-system-1-14of.onrender.com/api/auth/student/register
Request Body:
{
"name": "sana",
"email": "sana101@gmail.com",
"phone": "7981455400",
"password": "Zananas@1209",
"branch": "ECE",
"enrollmentYear": 2019
}

Student Login API : POST /api/auth/student/login
FULL URL : https://college-management-system-1-14of.onrender.com/api/auth/student/login
Request Body:
{
"email": "sana1011@gmail.com",
"password": "Zananas@1209"
}

After login you will get JWT token in response, use that token to access protected APIs by adding it in Authorization header as Bearer token.

📁 **Postman Collection:**
College Management System.postman_collection.json

**swagger url**
https://college-management-system-1-14of.onrender.com/swagger-ui/index.html

**🔐 Authentication & Security**
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

**🧩 Modules Implemented**
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

**🏗️ Project Structure**
•	Controller Layer (REST APIs)
•	Service Layer (Business Logic)
•	Repository Layer (JPA)
•	DTO Layer (Data Transfer Objects)
•	Entity Layer (Database Models)

**🛠️ Tech Stack**
•	Java 17+
•	Spring Boot 3+
•	Spring Security
•	Spring Data JPA (Hibernate)
•	PostgreSQL
•	JWT Authentication
•	Swagger (OpenAPI)
•	Maven

**Complete Authentication System**
•	Multi-role Support (Student, Faculty, Admin)
•	Secure APIs with JWT
•	Full CRUD Operations for all modules
•	Clean layered architecture

**🔮 Future Improvements**
•	Unit Testing (JUnit)
•	Frontend Integration continuation (Angular/React)
•	Docker Deployment
•	CI/CD Pipeline
•	Extending Email Notification Enhancements
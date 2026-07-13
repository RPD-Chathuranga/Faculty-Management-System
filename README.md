Faculty Management System (FMS)

A JavaFX-based Faculty Management System developed to manage academic information within a university environment. The system provides role-based access for Administrators, Lecturers, and Students, allowing efficient management of faculties, departments, lecturers, students, courses, timetables, attendance, and grades.

---

Features

Authentication
- Secure login system
- Role-based access control
- Session management
- Logout functionality

Dashboard
- Modern JavaFX dashboard
- Navigation to all modules
- Role-based module visibility

Faculty Management
- Add Faculty
- Update Faculty
- Delete Faculty
- Search Faculty

Department Management
- Add Department
- Update Department
- Delete Department
- Search Department

Lecturer Management
- Add Lecturer
- Update Lecturer
- Delete Lecturer
- Search Lecturer
- Automatically create user login account

Student Management
- Add Student
- Update Student
- Delete Student
- Search Student
- Automatically create user login account

Course Management
- Add Course
- Update Course
- Delete Course
- Search Course

Timetable Management
- Add Timetable
- Update Timetable
- Delete Timetable
- Search Timetable
- Conflict validation

### Attendance Management
- Add Attendance
- Update Attendance
- Delete Attendance
- Search Attendance
- Prevent duplicate attendance records

Grade Management
- Add Grade
- Update Grade
- Delete Grade
- Search Grades
- Automatic letter grade calculation
- Duplicate grade prevention

---

User Roles

Administrator
- Full system access
- Manage all modules
- CRUD operations on every module

Lecturer
- Access Course Management
- Access Academic Management
- Manage Attendance
- Manage Grades

Student
- View Academic Management
- View Attendance
- View Grades
- No editing permissions

---

Technologies Used

- Java 26
- JavaFX
- MySQL
- JDBC
- IntelliJ IDEA
- Git & GitHub

---

Architecture

The project follows the MVC (Model-View-Controller) architecture combined with the Repository Pattern.


User
   │
   ▼
View
   │
   ▼
Controller
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
MySQL Database


---

Project Structure

src
│
├── authentication
├── common
├── dashboard
├── faculty
├── department
├── lecturer
├── student
├── course
├── timetable
├── attendance
├── grade
└── app


Each module contains:

Model
Repository
Service
View
Controller

---

Database

The system uses MySQL with the following tables:

- USERS
- FACULTY
- DEPARTMENT
- LECTURER
- STUDENT
- COURSE
- TIMETABLE
- ATTENDANCE
- GRADE

All tables are connected using foreign key relationships to maintain data integrity.

---

How to Run

1. Clone the repository

git clone https://github.com/your-username/Faculty-Management-System.git


2. Open the project

Open the project using IntelliJ IDEA.

3. Configure JavaFX

Download the JavaFX SDK and configure the VM options.

Example:

--module-path "path/to/javafx/lib" --add-modules javafx.controls,javafx.fxml

4. Import Database

Import the provided SQL file into MySQL.

sql
fms.sql

5. Configure Database Connection

Update the database credentials in:

common/DatabaseConnection.java

Example:

java
url = "jdbc:mysql://localhost:3306/faculty_management_system";

username = "root";

password = "RPD48677";


6. Run

Run

Main.java

---

Validation

The system validates:

- Required fields
- Duplicate records
- Marks between 0–100
- Automatic letter grade calculation
- Timetable conflicts
- Duplicate attendance
- Duplicate grades

---

Role-Based Access

The dashboard automatically adjusts according to the logged-in user's role.

| Role & Permissions |

Administrator - Full Access 
Lecturer - Course, Timetable, Attendance, Grade 
Student - Academic View Only 

---

Future Improvements

- Password encryption
- Export reports to PDF
- Email notifications
- Student self-service portal
- Lecturer-specific course filtering
- Dashboard analytics
- Attendance reports
- Grade reports

---

Authors

Faculty Management System

Developed as a university group project using JavaFX and MySQL.

---

License

This project is developed for educational purposes.

USE faculty_management_system;


SHOW TABLES;

INSERT INTO USERS (
    username,
    password,
    full_name,
    email,
    phone,
    role
)
VALUES (
    'admin',
    'admin123',
    'System Administrator',
    'admin@fms.com',
    '0771234567',
    'ADMIN'
);

 SELECT * FROM USERS;

 CREATE TABLE FACULTY (
    faculty_id INT AUTO_INCREMENT PRIMARY KEY,
    faculty_name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
 
INSERT INTO FACULTY (faculty_name, description) VALUES
('Faculty of Computing', 'Offers Computing and IT related programs'),
('Faculty of Engineering', 'Offers Engineering programs'),
('Faculty of Business', 'Offers Business and Management programs');
 
 SELECT * FROM FACULTY;
 
 
CREATE TABLE DEPARTMENT (
    department_id INT AUTO_INCREMENT PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL,
    faculty_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_department_faculty
        FOREIGN KEY (faculty_id)
        REFERENCES FACULTY(faculty_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);
 
 INSERT INTO DEPARTMENT (department_name, faculty_id) VALUES
('Computer Science', 1),
('Software Engineering', 1),
('Information Technology', 1),
('Civil Engineering', 2),
('Mechanical Engineering', 2),
('Business Administration', 3);

 
CREATE TABLE STUDENT (
    student_id INT AUTO_INCREMENT PRIMARY KEY,

    user_id INT NOT NULL,
    registration_number VARCHAR(50) NOT NULL UNIQUE,
    semester INT NOT NULL,

    department_id INT NOT NULL,

    CONSTRAINT fk_student_user
        FOREIGN KEY (user_id)
        REFERENCES USERS(user_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_student_department
        FOREIGN KEY (department_id)
        REFERENCES DEPARTMENT(department_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE LECTURER (
    lecturer_id INT AUTO_INCREMENT PRIMARY KEY,

    user_id INT NOT NULL,
    employee_number VARCHAR(50) NOT NULL UNIQUE,
    specialization VARCHAR(100),

    department_id INT NOT NULL,

    CONSTRAINT fk_lecturer_user
        FOREIGN KEY (user_id)
        REFERENCES USERS(user_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_lecturer_department
        FOREIGN KEY (department_id)
        REFERENCES DEPARTMENT(department_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE COURSE (
    course_id INT AUTO_INCREMENT PRIMARY KEY,

    course_code VARCHAR(20) NOT NULL UNIQUE,
    course_name VARCHAR(100) NOT NULL,
    credits INT NOT NULL,

    department_id INT NOT NULL,
    lecturer_id INT,

    CONSTRAINT fk_course_department
        FOREIGN KEY (department_id)
        REFERENCES DEPARTMENT(department_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_course_lecturer
        FOREIGN KEY (lecturer_id)
        REFERENCES LECTURER(lecturer_id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

CREATE TABLE ENROLLMENT (
    enrollment_id INT AUTO_INCREMENT PRIMARY KEY,

    student_id INT NOT NULL,
    course_id INT NOT NULL,

    enrollment_date DATE NOT NULL,

    CONSTRAINT fk_enrollment_student
        FOREIGN KEY (student_id)
        REFERENCES STUDENT(student_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_enrollment_course
        FOREIGN KEY (course_id)
        REFERENCES COURSE(course_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);


CREATE TABLE ATTENDANCE (
    attendance_id INT AUTO_INCREMENT PRIMARY KEY,

    student_id INT NOT NULL,
    course_id INT NOT NULL,

    date DATE NOT NULL,
    status ENUM('PRESENT', 'ABSENT', 'LATE') NOT NULL,

    CONSTRAINT fk_attendance_student
        FOREIGN KEY (student_id)
        REFERENCES STUDENT(student_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_attendance_course
        FOREIGN KEY (course_id)
        REFERENCES COURSE(course_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE GRADE (
    grade_id INT AUTO_INCREMENT PRIMARY KEY,

    student_id INT NOT NULL,
    course_id INT NOT NULL,

    marks DECIMAL(5,2) NOT NULL,
    letter_grade VARCHAR(5),

    CONSTRAINT fk_grade_student
        FOREIGN KEY (student_id)
        REFERENCES STUDENT(student_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_grade_course
        FOREIGN KEY (course_id)
        REFERENCES COURSE(course_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
CREATE TABLE TIMETABLE (
    timetable_id INT AUTO_INCREMENT PRIMARY KEY,

    course_id INT NOT NULL,
    lecturer_id INT NOT NULL,

    day_of_week VARCHAR(20) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    room VARCHAR(50),
    semester INT NOT NULL,

    CONSTRAINT fk_timetable_course
        FOREIGN KEY (course_id)
        REFERENCES COURSE(course_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_timetable_lecturer
        FOREIGN KEY (lecturer_id)
        REFERENCES LECTURER(lecturer_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);





































 
 
 
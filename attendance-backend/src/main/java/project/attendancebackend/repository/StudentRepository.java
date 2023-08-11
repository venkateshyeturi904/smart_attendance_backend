package project.attendancebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import project.attendancebackend.model.Student;

public interface StudentRepository extends JpaRepository<Student, String> {
    
}

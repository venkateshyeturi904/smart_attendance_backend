package project.attendancebackend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.attendancebackend.model.Student;
import project.attendancebackend.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student getStudentById(String student_id){
        Optional<Student> studentOptional = studentRepository.findById(student_id);
        return studentOptional.orElse(null);
    }

}

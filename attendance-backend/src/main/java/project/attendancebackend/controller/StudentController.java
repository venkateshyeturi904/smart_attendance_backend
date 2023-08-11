package project.attendancebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import project.attendancebackend.model.Student;
import project.attendancebackend.service.StudentService;

@RestController
@RequestMapping("/api")
public class StudentController {
    
    @Autowired
    private StudentService studentService;

    @GetMapping("/studentNameById")
    public ResponseEntity<String> getStudentById(@RequestParam("student_id") String student_id){
        Student student = studentService.getStudentById(student_id);
        return ResponseEntity.ok(student.getStudent_name());
    }
}

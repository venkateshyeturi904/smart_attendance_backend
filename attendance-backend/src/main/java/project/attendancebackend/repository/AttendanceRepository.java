package project.attendancebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import project.attendancebackend.model.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance,Long>{
    
}

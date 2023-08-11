package project.attendancebackend.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import project.attendancebackend.model.Attendance;
import project.attendancebackend.repository.AttendanceRepository;

@Service
public class AttendanceService {

    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private AttendanceRepository attendanceRepository;

    public ResponseEntity<String> saveAttendanceData(HttpEntity<MultiValueMap<String, Object>> requestEntity) throws JsonMappingException, JsonProcessingException{
        ResponseEntity<String> response = restTemplate.exchange(
            "http://localhost:5000/predict_roll_numbers", 
            HttpMethod.POST,
            requestEntity,
            String.class
        );
        String incomingClassId = requestEntity.getBody().get("classId").get(0).toString();

        // save the attendance in the database

        String datePattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        String incomingRawDate = String.valueOf(requestEntity.getBody().get("date").get(0));
        LocalDate incomingDate = LocalDate.parse(incomingRawDate, formatter);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        JsonNode rollNumbersNode = jsonNode.get("roll_numbers");

        if (rollNumbersNode.isArray()) {
            for (JsonNode rollNumberArray : rollNumbersNode) {
                if (rollNumberArray.isArray() && rollNumberArray.size() > 0) {
                    String rollNumber = rollNumberArray.get(0).asText();
                    
                    String studentId = rollNumber;
                    Attendance attendance = new Attendance();

                    attendance.setStudentId(studentId);
                    attendance.setClassId(incomingClassId);
                    attendance.setDate(incomingDate);
                    attendance.setPresent(true);

                    attendanceRepository.save(attendance);

                }
            }
        }



        return response;
    }
}


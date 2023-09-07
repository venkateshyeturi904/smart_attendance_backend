package project.attendancebackend.controller;



import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import project.attendancebackend.service.AttendanceService;

@RestController
@RequestMapping(path = "/api")
public class ImageController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping(path = "/uploadImages")
    @CrossOrigin(origins = "http://localhost:3000")
    public Object uploadImages(
        @RequestParam("images") List<MultipartFile> files,
        @RequestParam("classId") String classId,
        @RequestParam("date") String date
    ) throws IOException {
    
        if (files.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonList("No images found in the request."));
        }
    
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    
        List<String> response = new ArrayList<>();
    
        for (MultipartFile file : files) {
            byte[] fileBytes = file.getBytes();
            ByteArrayResource resource = new ByteArrayResource(fileBytes) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };
    
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("images", resource);
            body.add("classId", classId);
            body.add("date", date);
    
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            List<String> temp = attendanceService.saveAttendanceData(requestEntity) ;
            System.out.println(temp);
            response.addAll(temp);

        }
        Set<String>temp = new HashSet<>(response) ;
        response = new ArrayList<>(temp) ;
    
        return response;
    }
    



}
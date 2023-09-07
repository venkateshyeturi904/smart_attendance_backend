package project.attendancebackend.model;

import java.util.List;
import java.util.ArrayList;
public class AttendanceReq {

    private List<String> rollNos = new ArrayList<String>();
    private String classId;
    private String date ;

    public String getDate(){
        return this.date;
    }
    public List<String> getRollNos(){
        return this.rollNos;
    }
    public String getClassId(){
        return this.classId;
    }


    
}

package com.abbayllc.dao;

import com.abbayllc.dto.Student;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StudentDaoImpl  implements StudentDao{

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate  template){
        this.namedParameterJdbcTemplate=template;
    }

    @Override
    public List<Student> displayAll() {
        List<Student> studentList=null;
        studentList=namedParameterJdbcTemplate.query("select * from student",(resultSet,index)->{
            Student std=new Student();
            std.setSid(resultSet.getString("sid"));
            std.setSname(resultSet.getString("sname"));
            std.setSaddr(resultSet.getString("saddr"));
            return std;
        });
        return studentList;
    }

    @Override

    public String add(Student std) {
        String status="";
        String query="";
        try {
             query="select * from student where sid=:sid";
            Map<String, Object> map=new HashMap<>();
            map.put("sid",std.getSid());
            List<Student> students=namedParameterJdbcTemplate.query(query,map,(rs,index)->{
                Student st=new Student();
                st.setSname(rs.getString("sname"));
                st.setSid(rs.getString("sid"));
                st.setSaddr(rs.getString("saddr"));

                return st;
            });
            if(students.isEmpty()){
                query="insert into student values(:sid,:sname,:saddr)";
                Map<String,Object> insertMap=new HashMap<>();
                insertMap.put("sid",std.getSid());
                insertMap.put("sname",std.getSname());
                insertMap.put("saddr",std.getSaddr());
                int rowCount=namedParameterJdbcTemplate.update(query,insertMap);

                if(rowCount==1){
                    status="Student Registered successfully";
                }else {
                    status="Student already Failure";
                }
            }else {
                status="Student already Existed";
            }

        }catch (Exception e){
            status="Student already Failure";
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public Student search(String sid) {
        Student std=null;
       try{
           String query="select * from student where sid=:sid";
           Map<String,Object> params=new HashMap<>();
           params.put("sid",sid);

           List<Student> studentList=namedParameterJdbcTemplate.query(query,params,(rs,index)->{
               Student student=new Student();
               student.setSid(rs.getString("sid"));
               student.setSaddr(rs.getString("saddr"));
               student.setSname(rs.getString("sname"));
               return  student;

           });
           if(studentList.isEmpty()){
               std=null;
           }else {
               std=studentList.get(0);
           }

       }catch (Exception e){
           std=null;
           e.printStackTrace();
       }
        return std;
    }

    @Override
    public String update(Student std) {
        String status="";
        try {
            Student student=search(std.getSid());
            if(student==null){
                status="We can't find a student to update please try again";
            }else {
                String query="update student set sname=:sname, saddr=:saddr where sid=:sid";
                Map<String,Object> params=new HashMap<>();
                params.put("sid",std.getSid());
                params.put("sname",std.getSname());
                params.put("saddr",std.getSaddr());
                int rowsCount=namedParameterJdbcTemplate.update(query,params);
                if(rowsCount>0){
                    status="Student Updated successfully";
                }else {
                    status="Student updation Failure";
                }

            }
        }catch (Exception e){
            status="Student updation Failure";
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public String delete(String sid) {
        String status="";
        try {

            Student std=search(sid);
            if(std!=null){
                String query="Delete from student where sid=:sid";
                Map<String,Object> params=new HashMap<>();
                params.put("sid",sid);
                int rowcomunt=namedParameterJdbcTemplate.update(query,params);
                if(rowcomunt>0){
                    status="Student deleted from the db";
                }else {
                    status="Deletion Failure";
                }

            }else {
                status="Student not existed";
            }
        }catch (Exception e){
            status="Deletion Failure";
            e.printStackTrace();
        }
        return status;
    }
}

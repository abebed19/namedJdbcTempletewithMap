package com.abbayllc.test;

import com.abbayllc.dao.StudentDao;
import com.abbayllc.dto.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext("/com/abbayllc/resources/applicationContext.xml");
        StudentDao studentDao=(StudentDao) context.getBean("studentDao");
        Student student=null;
        BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
        while (true){
            System.out.println("Please select from the following options");
            System.out.println("----------------------------------------");
            System.out.println("1------------->Add Student");
            System.out.println("2------------->Search  Student");
            System.out.println("3------------->Update  Student");
            System.out.println("4------------->Delete  Student");
            System.out.println("5------------->Display All Student");
            System.out.println("6------------->Exit system");
            try {
                int choice=Integer.parseInt(reader.readLine());
                switch (choice){
                    case 1:
                        student=new Student();
                        System.out.print("Please enter student id");
                        student.setSid(reader.readLine());
                        System.out.print("Please enter student name");
                        student.setSname(reader.readLine());
                        System.out.print("Please enter student address");
                        student.setSaddr(reader.readLine());
                        String status=studentDao.add(student);
                        System.out.println(status);
                        break;
                    case 2:
                        System.out.println("Please enter Student id to search student detail");
                        String sid=reader.readLine();
                        student=studentDao.search(sid);
                        if(student==null){
                            System.out.println("We can't find the student based on the given information");
                        }else {
                            System.out.println("Student Detail");
                            System.out.println("--------------");
                            System.out.println("Student id: "+student.getSid());
                            System.out.println("Student sname: "+student.getSname());
                            System.out.println("Student  Address: "+student.getSaddr());
                        }
                        break;
                    case 3:
                        System.out.println("Please enter Student id");
                        String studentID=reader.readLine();
                        student=studentDao.search(studentID);
                        if(student==null){
                            System.out.println("we can't find the student based on the id provided");
                        }else {
                            student.setSid(studentID);;
                            System.out.println("Enter Student new Student name old one:"+student.getSname());
                            student.setSname(reader.readLine());
                            System.out.println("Enter Student new student address old one:"+student.getSaddr());
                            student.setSaddr(reader.readLine());
                            String stat=studentDao.update(student);
                            System.out.println(stat);

                        }
                        break;
                    case 4:
                        System.out.println("Please enter Student id");
                        String studentid=reader.readLine();
                        student=studentDao.search(studentid);
                        if(student==null){
                            System.out.println("we can't find the student based on the id provided");
                        }else {
                            String stat=studentDao.delete(studentid);
                            System.out.println(stat);

                        }
                        break;
                    case 5:
                        List<Student> studentList=studentDao.displayAll();
                        if(studentList.isEmpty()){
                            System.out.println("No record exit try again latter");
                        }else {
                            System.out.println("List of all Students");
                            for (Student std:studentList
                                 ) {
                                System.out.println("---------------");
                                System.out.println("Student id: "+std.getSid());
                                System.out.println("Student name: "+std.getSname());
                                System.out.println("Student address"+std.getSaddr());


                            }
                        }


                    case 6:
                        System.exit(0);
                }

            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }
}

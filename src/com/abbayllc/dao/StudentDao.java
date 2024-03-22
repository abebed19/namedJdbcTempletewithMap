package com.abbayllc.dao;

import com.abbayllc.dto.Student;

import java.util.List;

public interface StudentDao {
    public String add(Student std);
    public Student search(String sid);
    public String update(Student std);
    public String delete(String sid);
    public List<Student> displayAll();
}

package com.hjl.testmodule.base;

import java.util.List;

/**
 * Description 测试基类
 * Author long
 * Date 2020/2/12 17:06
 */
public class Student {

    String name;
    String id;
    List<Course> courseList;

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }




    public Student(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

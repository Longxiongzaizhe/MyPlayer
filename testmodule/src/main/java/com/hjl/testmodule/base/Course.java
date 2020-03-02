package com.hjl.testmodule.base;

/**
 * Description 测试基类 学生的课程
 * Author long
 * Date 2020/2/12 17:07
 */
public class Course {
    String name;
    String id;


    public Course(String name, String id) {
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

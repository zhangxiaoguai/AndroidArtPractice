package com.ericzhang.androidartpractice.chapter2.binder.entity;

import com.ericzhang.androidartpractice.chapter2.binder.entity.Teacher;

interface ITeacherManager {

    List<Teacher> getTeacherList();
    void addTeacher(in Teacher teacher);

}

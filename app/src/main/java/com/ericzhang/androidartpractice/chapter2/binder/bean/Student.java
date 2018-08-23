package com.ericzhang.androidartpractice.chapter2.binder.bean;

import java.io.Serializable;

public class Student implements Serializable {

    private static final long serialVersionUID = -4959874913017878685L;

    private String name;
    private String className;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}

package com.example.demofunc.model;

public class ModelTwo extends ModelOne{
    private String message;

    public ModelTwo(String name, int age, String message) {
        super(name, age);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.takusemba.multisnaprecyclerviewsample.temp;

public class VerticalAbstractModel {

    private String title;

    private String message;


    public VerticalAbstractModel(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public VerticalAbstractModel() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

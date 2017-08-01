package com.takusemba.multisnaprecyclerviewsample;

import java.util.ArrayList;

public class HorizontalAbstractModel {

    private String title;

    private String message;

    private ArrayList<HorizontalAbstractModel> singleItemModelArrayList;

    public HorizontalAbstractModel(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public HorizontalAbstractModel(String title, String message, ArrayList<HorizontalAbstractModel> singleItemModelArrayList) {
        this.title = title;
        this.message = message;
        this.singleItemModelArrayList = singleItemModelArrayList;
    }


    public HorizontalAbstractModel() {

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

    public ArrayList<HorizontalAbstractModel> getSingleItemArrayList() {
        return singleItemModelArrayList;
    }

    public void setSingleItemArrayList(ArrayList<HorizontalAbstractModel> singleItemModelArrayList) {
        this.singleItemModelArrayList = singleItemModelArrayList;
    }
}

package com.example.bhatt.selfhelpher;

/**
 * Created by bhatt on 21-07-2017.
 */

public class CourselibData {

    private String coursename;

    private int colorcode;

    public CourselibData(String coursename, int randomAndroidColor) {

        this.coursename = coursename;
        this.colorcode = randomAndroidColor;
    }

    public String getcoursename(){ return coursename; }

    public int getcolor(){ return colorcode; }

}

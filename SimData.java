package com.example.firstapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SimData {
    private String title;
    private String viewItemURL;
    private String postalCode;
    private String prd;

    public String getPrd() {
        return prd;
    }

    public void setPrd(String prd) {
        this.prd = prd;
    }

    public String getDad() {
        return dad;
    }

    public void setDad(String dad) {
        this.dad = dad;
    }

    private String dad;



    private String id;
    private String shipcost,cost,days,img;

    public SimData(String title, String viewItemURL, String cost,String shipcost, String days,String img) {
        this.title = title;
        this.viewItemURL = viewItemURL;
        this.postalCode = postalCode;
        this.cost = cost;

        this.shipcost = shipcost;
        this.days=days;
        this.id=id;
        this.img=img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String vurl) {
        this.img = vurl;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getShipcost() {
        return shipcost;
    }

    public void setShipcost(String shipcost) {
        this.shipcost = shipcost;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getViewItemURL() {
        return viewItemURL;
    }

    public void setViewItemURL(String viewItemURL) {
        this.viewItemURL = viewItemURL;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }






}

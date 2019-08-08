package com.example.firstapp;

public class ResultData {
    private String title;
    private String viewItemURL;
    private String postalCode;
    private String condition;



    private String id;
    private String shipcost,cost;
    private String vurl;

    public ResultData(String title, String viewItemURL, String postalCode,String id, String cost, String condition,String shipcost,String vurl) {
        this.title = title;
        this.viewItemURL = viewItemURL;
        this.postalCode = postalCode;
        this.cost = cost;
        this.condition = condition;
        this.shipcost = shipcost;
        this.vurl=vurl;
        this.id=id;
    }

    public String getVurl() {
        return vurl;
    }

    public void setVurl(String vurl) {
        this.vurl = vurl;
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }





}

package com.datseacorporation.maptrace;



public class ModelClass {
    String drugname, currentDateandTime, logimage;

    public ModelClass(String drugname, String currentDateandTime, String logimage) {
        this.drugname = drugname;
        this.currentDateandTime = currentDateandTime;
        this.logimage = logimage;

    }

    public ModelClass() {
    }

    // to return Drugname
    public String getDrugname() {return drugname;}
    public void setDrugname(String drugname) {
        this.drugname = drugname;
    }
    // to return timestrap
    public String getCurrentDateandTime() {return currentDateandTime;}
    public void setCurrentDateandTime(String currentDateandTime) {this.currentDateandTime = currentDateandTime;}
    // to return logimage
    public String getLogimage() {
        return logimage;
    }
    public void setLogimage(String logimage) {
        this.logimage = logimage;
    }
}

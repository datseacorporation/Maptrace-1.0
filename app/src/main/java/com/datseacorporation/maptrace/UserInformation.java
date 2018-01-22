package com.datseacorporation.maptrace;

/**
 * Created by Admin on 08-09-2017.
 */

public class UserInformation {
  //  public String logimage;
    public String drugname;
    public double finallatitude;
    public double finallongitude;
  //  public String currentDateandTime;


    public UserInformation(){

    }
    public UserInformation(String drugname,double finallatitude,double finallongitude){

        this.drugname = drugname;
        this.finallatitude = finallatitude;
        this.finallongitude = finallongitude;
      //  this.currentDateandTime = currentDateandTime;
      //  this.logimage = logimag;
    }
}

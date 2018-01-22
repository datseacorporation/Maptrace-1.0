package com.datseacorporation.maptrace;

/**
 * Created by HP on 18-01-2018.
 */

public class UserDataUpload {
public  String drugName;
public  String drugQuantity;
public  String suspectName;
public  Double uLatitude;
public  Double uLongitude;
public  String currentDateandTime;

public UserDataUpload(){

}
public UserDataUpload(String drugName, String drugQuantity, String suspectName, Double uLatitude, Double uLongitude, String currentDateandTime){
    this.drugName = drugName;
    this.drugQuantity = drugQuantity;
    this.suspectName = suspectName;
    this.uLatitude=  uLatitude;
    this.uLongitude = uLongitude;
    this.currentDateandTime = currentDateandTime;
}
}

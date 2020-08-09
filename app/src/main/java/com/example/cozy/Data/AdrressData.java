package com.example.cozy.Data;

public class AdrressData {
    private String adrressName;
    private String visitDate, latitude, longitude, adrress;

    public AdrressData(String adrressName){
        this.adrressName = adrressName;
    }

    public AdrressData(String visitDate, String latitude, String longitude,String adrress){
        this.visitDate = visitDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.adrress = adrress;
    }

    public String getAdrressName(){
        return this.adrressName;
    }

    public String getAdrress() {return this.adrress;};

    public String getVisitDate(){
        return  this.visitDate;
    }

    public String getLatitude(){
        return  this.latitude;
    }

    public String getLongitude(){
        return this.longitude;
    }
}

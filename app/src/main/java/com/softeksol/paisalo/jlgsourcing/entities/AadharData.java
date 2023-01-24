package com.softeksol.paisalo.jlgsourcing.entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by sachindra on 12/3/2016.
 */
public class AadharData implements Serializable {
    public String AadharId;
    public String Name;
    public String GurName;
    public Date DOB;
    public int Age;
    public String State;
    public int Pin;
    public String Gender;
    public String Address1;
    public String Address2;
    public String Address3;
    public String City;
    public byte[] Picture;
    public String isAadharVerified;
    public String UUID;

    @Override
    public String toString() {
        return "AadharData{" +
                "AadharId='" + AadharId + '\'' +
                ", Name='" + Name + '\'' +
                ", GurName='" + GurName + '\'' +
                ", DOB=" + DOB +
                ", Age=" + Age +
                ", State='" + State + '\'' +
                ", Pin=" + Pin +
                ", Gender='" + Gender + '\'' +
                ", Address1='" + Address1 + '\'' +
                ", Address2='" + Address2 + '\'' +
                ", Address3='" + Address3 + '\'' +
                ", City='" + City + '\'' +
                ", Picture=" + Arrays.toString(Picture) +
                ", isAadharVerified='" + isAadharVerified + '\'' +
                ", UUID='" + UUID + '\'' +
                '}';
    }
}

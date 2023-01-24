package com.softeksol.paisalo.jlgsourcing.entities;

import com.google.gson.annotations.Expose;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.softeksol.paisalo.jlgsourcing.DbIGL;

import java.io.Serializable;


/**
 * Created by sachindra on 2015-10-01.
 */
@ModelContainer
@Table(database = DbIGL.class)
public class BorrowerFamilyExpenses extends BaseModel implements Serializable {

    @Expose
    @Column
    private long Code;
    @Expose
    @Column
    private String Tag;
    @Expose
    @Column
    private String Creator;
    @Expose
    @Column
    private int Rent;
    @Expose
    @Column
    private int Fooding;
    @Expose
    @Column
    private int Education;
    @Expose
    @Column
    private int Health;
    @Expose
    @Column
    private int Travelling;

    @Expose
    @Column
    private int Entertainment;
    @Expose
    @Column
    private int Others;

    @Expose
    @Column
    private String HomeType;

    @Expose
    @Column
    private String HomeRoofType;

    @Expose
    @Column
    private String ToiletType;

    @Expose
    @Column
    private String LivingWSpouse;

    @Column
    @PrimaryKey(autoincrement = true)
    private long id;
    @Column
    private long FiID;

    public BorrowerFamilyExpenses() {
    }

    public BorrowerFamilyExpenses(long code, String tag, String creator, int rent, int fooding, int education, int health, int travelling, int entertainment, int others, String homeType, String homeRoofType, String toiletType, String livingWSpouse, long id, long fiID) {
        Code = code;
        Tag = tag;
        Creator = creator;
        Rent = rent;
        Fooding = fooding;
        Education = education;
        Health = health;
        Travelling = travelling;
        Entertainment = entertainment;
        Others = others;
        HomeType = homeType;
        HomeRoofType = homeRoofType;
        ToiletType = toiletType;
        LivingWSpouse = livingWSpouse;
        this.id = id;
        FiID = fiID;
    }

    public long getCode() {
        return Code;
    }

    public void setCode(long code) {
        Code = code;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public int getRent() {
        return Rent;
    }

    public void setRent(int rent) {
        Rent = rent;
    }

    public int getFooding() {
        return Fooding;
    }

    public void setFooding(int fooding) {
        Fooding = fooding;
    }

    public int getEducation() {
        return Education;
    }

    public void setEducation(int education) {
        Education = education;
    }

    public int getHealth() {
        return Health;
    }

    public void setHealth(int health) {
        Health = health;
    }

    public int getTravelling() {
        return Travelling;
    }

    public void setTravelling(int travelling) {
        Travelling = travelling;
    }

    public int getEntertainment() {
        return Entertainment;
    }

    public void setEntertainment(int entertainment) {
        Entertainment = entertainment;
    }

    public int getOthers() {
        return Others;
    }

    public void setOthers(int others) {
        Others = others;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFiID() {
        return FiID;
    }

    public void setFiID(long fiID) {
        FiID = fiID;
    }

    public String getHomeType() {
        return HomeType;
    }

    public void setHomeType(String homeType) {
        HomeType = homeType;
    }

    public String getHomeRoofType() {
        return HomeRoofType;
    }

    public void setHomeRoofType(String homeRoofType) {
        HomeRoofType = homeRoofType;
    }

    public String getToiletType() {
        return ToiletType;
    }

    public void setToiletType(String toiletType) {
        ToiletType = toiletType;
    }

    public String getLivingWSpouse() {
        return LivingWSpouse;
    }

    public void setLivingWSpouse(String livingWSpouse) {
        LivingWSpouse = livingWSpouse;
    }

    @Override
    public String toString() {
        return "BorrowerFamilyExpenses{" +
                "Code=" + Code +
                ", Tag='" + Tag + '\'' +
                ", Creator='" + Creator + '\'' +
                ", Rent=" + Rent +
                ", Fooding=" + Fooding +
                ", Education=" + Education +
                ", Health=" + Health +
                ", Travelling=" + Travelling +
                ", Entertainment=" + Entertainment +
                ", Others=" + Others +
                ", HomeType='" + HomeType + '\'' +
                ", HomeRoofType='" + HomeRoofType + '\'' +
                ", ToiletType='" + ToiletType + '\'' +
                ", LivingWSpouse='" + LivingWSpouse + '\'' +
                ", id=" + id +
                ", FiID=" + FiID +
                '}';
    }
}

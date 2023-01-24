package com.softeksol.paisalo.jlgsourcing.entities;

import com.google.gson.annotations.Expose;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.softeksol.paisalo.jlgsourcing.DbIGL;

import java.util.Date;

/**
 * Created by sachindra on 2016-10-04.
 */

@ModelContainer
@Table(database = DbIGL.class)
public class Locations extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    long id;

    @Expose
    @Column
    float Longitude;

    @Expose
    @Column
    float Latitude;

    @Expose
    @Column
    Date UtcDateTime;

    @Expose
    @Column
    Date GpsDateTime;

    @Expose
    @Column
    String Provider;

    @Expose
    @Column
    String Accuracy;

    @Expose
    @Column
    String IPAddr;

    @Expose
    @Column
    Date UpdtDateTime;
}

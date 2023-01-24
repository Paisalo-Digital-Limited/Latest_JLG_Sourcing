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
 * Created by sachindra on 2016-10-04.
 */

@ModelContainer
@Table(database = DbIGL.class)
public class Manager extends BaseModel implements Serializable {
    @Expose
    @Column
    @PrimaryKey(autoincrement = true)
    public long id;

    @Expose
    @Column
    public String Creator;

    @Expose
    @Column
    public String FOCode;

    @Expose
    @Column
    public String FOName;

    @Expose
    @Column
    public String DataBase;

    @Expose
    @Column
    public String TAG;

    @Expose
    @Column
    public String AreaCd;

    @Expose
    @Column
    public String AreaName;

    @Expose
    public String GroupClosed = "N";
}

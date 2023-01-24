package com.softeksol.paisalo.jlgsourcing.entities;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.softeksol.paisalo.jlgsourcing.DbIGL;

/**
 * Created by sachindra on 2016-10-04.
 */


@ModelContainer
@Table(database = DbIGL.class)
public class DocumentCheckList extends BaseModel {
    @Column
    @PrimaryKey
    public short DocID;

    @Column
    public String DocName;

    @Column
    public short SortOrder;

    @Column
    public String CreatorType;
}

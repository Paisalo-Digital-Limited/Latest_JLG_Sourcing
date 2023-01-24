package com.softeksol.paisalo.jlgsourcing;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

/**
 * Created by sachindra on 2016-10-08.
 */
public class DbFlowExclusionStrategy implements ExclusionStrategy {

    // Otherwise, Gson will go through base classes of DBFlow models
    // and hang forever.
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getDeclaredClass().equals(ModelAdapter.class);
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}

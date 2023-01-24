package com.softeksol.paisalo.jlgsourcing.enums;

/**
 * Created by sachindra on 12/14/2016.
 */
public enum EnumFieldName {
    Borrower("borrowerpic"), Guarantor("guarantorpic"), Documents("documentpic");
    private final String fieldName;

    public String getFieldName() {
        return fieldName;
    }

    EnumFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}

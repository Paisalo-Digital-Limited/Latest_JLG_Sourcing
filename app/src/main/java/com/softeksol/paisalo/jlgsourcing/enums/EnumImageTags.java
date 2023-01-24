package com.softeksol.paisalo.jlgsourcing.enums;

/**
 * Created by sachindra on 12/14/2016.
 */
public enum EnumImageTags {
    Borrower("CUSTIMG"), Guarantor("GUARPIC"), Documents("DOCIMG");
    private final String imageTag;

    public String getImageTag() {
        return imageTag;
    }

    ;

    EnumImageTags(String imageTag) {
        this.imageTag = imageTag;
    }
}

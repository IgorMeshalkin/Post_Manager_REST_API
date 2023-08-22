package com.test_task.skyeng_test_task.entities.entity_types;

public enum PostalItemType {
    LETTER("LT"),
    PACKAGE("PK"),
    PARCEL_POST("PP"),
    POSTCARD("PC");

    private final String typeCode;

    PostalItemType(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeCode() {
        return typeCode;
    }
}

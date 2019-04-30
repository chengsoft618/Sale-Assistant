package com.shoniz.saledistributemobility.message;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 921235 on 3/7/2018.
 */

public enum MessageTypeEnum {
    @SerializedName("0")
    Personal(0),
    @SerializedName("1")
    Issued(1),
    @SerializedName("2")
    updateData(2),
    @SerializedName("3")
    trackingOn(3),
    @SerializedName("4")
    trackingOff(4),
     ;

    private final int value;

    MessageTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

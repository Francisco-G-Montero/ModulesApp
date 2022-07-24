package com.frommetoyou.modulesapp2.data.util;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse {
    @SerializedName("detail")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

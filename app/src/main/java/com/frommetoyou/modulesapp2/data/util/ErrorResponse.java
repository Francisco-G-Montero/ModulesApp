package com.intermedia.daiana.scan.data.model;

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

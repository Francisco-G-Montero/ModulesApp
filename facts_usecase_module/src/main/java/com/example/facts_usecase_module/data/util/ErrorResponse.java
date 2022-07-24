package com.example.facts_usecase_module.data.util;

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

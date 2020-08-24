package com.azstudio.storeapp.Models;

public class responseModel {
    boolean status;
    String message;

    public responseModel() {

    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }


}

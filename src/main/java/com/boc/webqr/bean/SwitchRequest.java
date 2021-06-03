package com.boc.webqr.bean;

public class SwitchRequest {
    private String statusCode;
    private String statusMessage;
    private SwitchBean data;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public SwitchBean getData() {
        return data;
    }

    public void setData(SwitchBean data) {
        this.data = data;
    }
}

package com.boc.webqr.bean;

public class UserResponse {
    String content;
    String message;
    String code;
    SwitchBean switchBean;
    RequestBean requestBean;

    public UserResponse() {
    }

    public UserResponse(String content) {
        this.content = content;
    }

    public UserResponse(String message, String code, RequestBean requestBean) {
        this.message = message;
        this.code = code;
        this.requestBean = requestBean;
    }

    public UserResponse(String message, String code, SwitchBean switchBean) {
        this.message = message;
        this.code = code;
        this.switchBean = switchBean;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SwitchBean getSwitchBean() {
        return switchBean;
    }

    public void setSwitchBean(SwitchBean switchBean) {
        this.switchBean = switchBean;
    }

    public RequestBean getRequestBean() {
        return requestBean;
    }

    public void setRequestBean(RequestBean requestBean) {
        this.requestBean = requestBean;
    }
}

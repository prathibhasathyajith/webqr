package com.boc.webqr.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestBean {
    private String mid;
    private String tid;
    private String amount;
    private String currency;
    private String successUrl;
    private String errorUrl;
    private String returnUrl;
    private String referenceNo;
    private String hash;
    private String secret;

    public RequestBean() {
    }

    public RequestBean(String mid, String tid, String amount, String successUrl, String errorUrl, String referenceNo) {
        this.mid = mid;
        this.tid = tid;
        this.amount = amount;
        this.successUrl = successUrl;
        this.errorUrl = errorUrl;
        this.referenceNo = referenceNo;
    }

    public RequestBean(String mid, String tid, String amount, String currency, String successUrl, String errorUrl, String returnUrl, String referenceNo, String hash, String secret) {
        this.mid = mid;
        this.tid = tid;
        this.amount = amount;
        this.currency = currency;
        this.successUrl = successUrl;
        this.errorUrl = errorUrl;
        this.returnUrl = returnUrl;
        this.referenceNo = referenceNo;
        this.hash = hash;
        this.secret = secret;
    }

    public RequestBean(String mid, String tid, String amount) {
        this.mid = mid;
        this.tid = tid;
        this.amount = amount;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}

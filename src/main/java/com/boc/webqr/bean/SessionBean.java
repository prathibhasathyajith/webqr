package com.boc.webqr.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

public class SessionBean {
    private MerchantBean merchantBean;

    public MerchantBean getMerchantBean() {
        return merchantBean;
    }

    public void setMerchantBean(MerchantBean merchantBean) {
        this.merchantBean = merchantBean;
    }
}

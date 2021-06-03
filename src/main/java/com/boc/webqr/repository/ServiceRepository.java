package com.boc.webqr.repository;

import com.boc.webqr.bean.KeyValueBean;
import com.boc.webqr.bean.MerchantBean;
import com.boc.webqr.bean.RequestBean;

import java.util.List;

public interface ServiceRepository {

    public MerchantBean getMerchantByMidTid(String mid, String tid);

    public List<KeyValueBean> getQRUserParamValues();

    public KeyValueBean getTimeOut();

    public String insertWebQRRequest(RequestBean requestBean, String qrString) throws Exception;
}

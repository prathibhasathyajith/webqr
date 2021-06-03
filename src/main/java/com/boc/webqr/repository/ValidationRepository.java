package com.boc.webqr.repository;

import com.boc.webqr.bean.KeyValueBean;

import java.util.List;

public interface ValidationRepository {
    public boolean checkMerchantByMidTid(String mid,String tid);


}

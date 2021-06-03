package com.boc.webqr.repository.impl;

import com.boc.webqr.bean.MerchantBean;
import com.boc.webqr.bean.MerchantDataMapper;
import com.boc.webqr.bean.RequestBean;
import com.boc.webqr.bean.SessionBean;
import com.boc.webqr.repository.ValidationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class ValidationRepositoryImpl implements ValidationRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SessionBean sessionBean;

    public boolean checkMerchantByMidTid(String mid, String tid) {
        boolean isValid = false;
        List<MerchantBean> merchantBeans = new ArrayList<MerchantBean>();

        try {
            String SQL_GET_MERCHANT = "SELECT " +
                    " M.MID AS MID," +
                    " M.LEGAL_NAME AS LEGAL_NAME, " +
                    " M.MCC AS MCC, " +
                    " M.STATUS AS STATUS, " +
                    " M.CITY AS CITY, " +
                    " M.REFERENCE_LABEL AS REFERENCE_LABEL, " +
                    " T.TID AS TID " +
                    " FROM LVMT_MERCHANT M " +
                    " LEFT OUTER JOIN LVMT_TERMINAL T ON T.MID = M.MID " +
                    " LEFT OUTER JOIN LVMT_SWT_MT_STATUS S ON S.CODE = M.STATUS " +
                    " WHERE M.MID =? AND T.TID=?";

            merchantBeans = jdbcTemplate.query(SQL_GET_MERCHANT, new Object[]{mid, tid}, new MerchantDataMapper());

            if (merchantBeans.size()>0){
                if (merchantBeans.get(0).getStatus()==1){
                    isValid = true;
                    if(sessionBean!=null){
                        sessionBean.setMerchantBean(merchantBeans.get(0));
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;

    }

}

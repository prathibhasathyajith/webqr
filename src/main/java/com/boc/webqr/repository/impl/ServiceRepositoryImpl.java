package com.boc.webqr.repository.impl;

import com.boc.webqr.bean.*;
import com.boc.webqr.repository.ServiceRepository;
import com.boc.webqr.util.varlist.MessageVarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Scope("prototype")
public class ServiceRepositoryImpl implements ServiceRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public MerchantBean getMerchantByMidTid(String mid, String tid) {
        List<MerchantBean> merchantBeans = new ArrayList<MerchantBean>();
        MerchantBean merchantBean = new MerchantBean();
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return merchantBean;
    }

    public List<KeyValueBean> getQRUserParamValues() {
        List<KeyValueBean> paramValues = new ArrayList<KeyValueBean>();
        try {
            String SQL_GET_MERCHANT = "SELECT " +
                    "S.PARAM_CODE AS KEY, " +
                    "S.PARAM_VALUE AS VALUE " +
                    "FROM LVMT_USER_PARAM S " +
                    "WHERE " +
                    "S.PARAM_CODE IN (?,?,?,?,?) " +
                    "ORDER BY S.PARAM_CODE ";

            paramValues = jdbcTemplate.query(SQL_GET_MERCHANT, new Object[]{
                    "UNION_PAY_ACQUIRER_IIN",
                    "UNION_PAY_FORWARDING_IIN",
                    "LQR_NETWORK_TYPE",
                    "LQR_AQBANK_CODE",
                    "LQR_SAQBANK_CODE"
            }, new KeyValueDataMapper());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return paramValues;
    }

    public KeyValueBean getTimeOut() {
        List<KeyValueBean> paramValues = new ArrayList<KeyValueBean>();
        try {
            String SQL_GET_MERCHANT = "SELECT " +
                    "S.PARAM_CODE AS KEY, " +
                    "S.PARAM_VALUE AS VALUE " +
                    "FROM LVMT_USER_PARAM S " +
                    "WHERE " +
                    "S.PARAM_CODE IN (?) " +
                    "ORDER BY S.PARAM_CODE ";

            paramValues = jdbcTemplate.query(SQL_GET_MERCHANT, new Object[]{
                    "WEB_QR_TIMEOUT"
            }, new KeyValueDataMapper());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return paramValues.get(0);
    }

    public Date getCurrentDate() throws Exception {
        DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        Date convertedDate;

        Map<String, Object> cdate;
        try {
            String query = "SELECT TO_CHAR (SYSDATE, 'MM-DD-YYYY HH24:MI:SS') NOW FROM DUAL";
            cdate = jdbcTemplate.queryForMap(query);

            convertedDate = (Date) formatter.parse(cdate.get("NOW").toString());
        } catch (Exception e) {
            throw e;
        }
        return convertedDate;

    }

    @Transactional(rollbackFor = Exception.class)
    public String insertWebQRRequest(RequestBean requestBean, String qrString) throws Exception {
        String message = "";
        int value = 0;
        Date sysDate = this.getCurrentDate();

        if (requestBean.getReferenceNo() != null) {
            String SQL_INSERT_LVMT_WEBQR_TXN_REQUEST = ""
                    + "insert into LVMT_WEBQR_TXN_REQUEST ("
                    + "REFERENCE_ID,"
                    + "MID,"
                    + "TID,"
                    + "LQR_STRING, "
                    + "AMOUNT, "
                    + "CURRENCY, "
                    + "ERROR_URL, "
                    + "SUCCESS_URL, "
                    + "RETURN_URL, "
                    + "STATUS, "
                    + "API_CALL_STATUS, "
                    + "UPDATED_TIME, "
                    + "CREATED_TIME) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

            value = jdbcTemplate.update(SQL_INSERT_LVMT_WEBQR_TXN_REQUEST,
                    requestBean.getReferenceNo(),
                    requestBean.getMid(),
                    requestBean.getTid(),
                    qrString,
                    requestBean.getAmount(),
                    requestBean.getCurrency(),
                    requestBean.getErrorUrl(),
                    requestBean.getSuccessUrl(),
                    requestBean.getReturnUrl(),
                    0,
                    0,
                    sysDate,
                    sysDate);


        } else {
            message = MessageVarList.WEBQR_REQUEST_INSERTION_FAILED;
        }

        if (value == 1) {
            message = "";
        }

        return message;
    }
}

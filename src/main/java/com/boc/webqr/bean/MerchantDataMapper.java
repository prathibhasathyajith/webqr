package com.boc.webqr.bean;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MerchantDataMapper implements RowMapper<MerchantBean> {

    @Override
    public MerchantBean mapRow(ResultSet resultSet, int i) throws SQLException {

        MerchantBean bean = new MerchantBean();

        try{
            bean.setMid(resultSet.getString("MID").toString());
        }catch(Exception e){
            bean.setMid("--");
        }

        try{
            bean.setTid(resultSet.getString("TID").toString());
        }catch(Exception e){
            bean.setTid("--");
        }

        try{
            bean.setLegalName(resultSet.getString("LEGAL_NAME").toString());
        }catch(Exception e){
            bean.setLegalName("--");
        }

        try{
            bean.setCity(resultSet.getString("CITY").toString());
        }catch(Exception e){
            bean.setCity("--");
        }

        try{
            bean.setMcc(resultSet.getString("MCC").toString());
        }catch(Exception e){
            bean.setMcc("--");
        }

        try{
            bean.setReferenceLabel(resultSet.getString("REFERENCE_LABEL").toString());
        }catch(Exception e){
            bean.setReferenceLabel("--");
        }

        try{
            bean.setStatus(resultSet.getInt("STATUS"));
        }catch(Exception e){
            bean.setStatus(0);
        }

        return bean;
    }
}

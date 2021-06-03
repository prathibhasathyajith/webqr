package com.boc.webqr.bean;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KeyValueDataMapper implements RowMapper<KeyValueBean> {
    @Override
    public KeyValueBean mapRow(ResultSet resultSet, int i) throws SQLException {
        KeyValueBean bean = new KeyValueBean();

        try{
            bean.setKey(resultSet.getString("KEY").toString());
        }catch(Exception e){
            bean.setKey("--");
        }
        try{
            bean.setValue(resultSet.getString("VALUE").toString());
        }catch(Exception e){
            bean.setValue("--");
        }

        return bean;

    }
}

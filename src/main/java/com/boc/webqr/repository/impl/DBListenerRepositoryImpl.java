package com.boc.webqr.repository.impl;

import com.boc.webqr.bean.MerchantBean;
import com.boc.webqr.bean.MerchantDataMapper;
import com.boc.webqr.repository.DBListenerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class DBListenerRepositoryImpl implements DBListenerRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;



}

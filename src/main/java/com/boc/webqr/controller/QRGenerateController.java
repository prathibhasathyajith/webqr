package com.boc.webqr.controller;

import com.boc.webqr.bean.RequestBean;
import com.boc.webqr.bean.SessionBean;
import com.boc.webqr.repository.DBListenerRepository;
import com.boc.webqr.repository.ServiceRepository;
import com.boc.webqr.repository.ValidationRepository;
import com.boc.webqr.service.MD5Service;
import com.boc.webqr.util.validation.BeanValidation;
import com.boc.webqr.validators.RequestBeanValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/api/v1/qr")
public class QRGenerateController implements BeanValidation<Object> {
    private static final Logger logger = LogManager.getLogger(QRGenerateController.class);

    @Autowired
    DBListenerRepository dbListenerRepository;

    @Autowired
    ValidationRepository validationRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    MD5Service md5;

    @Autowired
    SessionBean sessionBean;

    @Autowired
    RequestBeanValidator requestBeanValidator;

    @RequestMapping("/")
    public String index(Map<String, Object> model) {
        logger.info("Class: "+getClass()+",Method: index , Mapping: /api/v1/qr/");
        model.put("message", "running!!!");
        return "server";
    }

    @RequestMapping("/server")
    public String server(Map<String, Object> model) {
        logger.info("Class: "+getClass()+",Method: server , Mapping: /api/v1/qr/server");
        model.put("message", "running!!!");
        return "server";
    }

    @PostMapping(value = "/request", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String getClientRequest(Map<String, Object> model, RequestBean requestBean, HttpServletRequest request, Locale locale) throws Exception {

        logger.info("Class: "+getClass()+",Method: getClientRequest ,Mapping: /api/v1/qr/request");
        logger.info(">> CLIENT REQUEST <<");
        logger.info(">> CLIENT REQUEST BEAN VALUES <<");
        logger.info("-- MID : " + requestBean.getMid() + " | TID : " + requestBean.getTid() + " | AMOUNT : " + requestBean.getAmount() + " --");

        try {
            BindingResult bindingResult = validateRequestBean(requestBean);

            if (bindingResult.hasErrors()) {
                String errorMsg = messageSource.getMessage(bindingResult.getAllErrors().get(0).getCode(), new Object[]{bindingResult.getAllErrors().get(0).getDefaultMessage()}, Locale.US);
                model.put("message", errorMsg);
                logger.error("Error : " + errorMsg);
                return "error-common";
            } else {
                model.put("data", requestBean);
                model.put("merchantData", sessionBean.getMerchantBean());
                return "genqr";
            }
        } catch (Exception ex) {
            logger.error("Exception : ", ex);
            return "error-common";
        }
    }

    @Override
    public BindingResult validateRequestBean(Object o) {
        DataBinder dataBinder = new DataBinder(o);
        dataBinder.setValidator(requestBeanValidator);
        dataBinder.validate();
        return dataBinder.getBindingResult();
    }

}

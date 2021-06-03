package com.boc.webqr.controller;

import com.boc.webqr.bean.SwitchRequest;
import com.boc.webqr.bean.SwitchResponse;
import com.boc.webqr.service.impl.SocketService;
import com.boc.webqr.util.validation.BeanValidation;
import com.boc.webqr.util.varlist.MessageVarList;
import com.boc.webqr.validators.SwitchRequestValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Locale;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/pay")
public class PaymentNotifyController implements BeanValidation<Object> {
    private static final Logger logger = LogManager.getLogger(PaymentNotifyController.class);
    private static final String AUTHENTICATION_TOKEN_SWT_USERNAME = "admin";
    private static final String AUTHENTICATION_TOKEN_SWT_PASSWORD = "admin";

    @Autowired
    SocketService socketService;

    @Autowired
    SwitchRequestValidator switchRequestValidator;

    @Autowired
    MessageSource messageSource;

    @PostMapping("/paymentNotify")
    public ResponseEntity paymentReceived(@RequestBody SwitchRequest requestBean,
                                          @RequestHeader(value = "authorization") String authString) {

        logger.info("Class: " + getClass() + ",Method: paymentReceived ,Mapping: /api/v1/pay/paymentNotify");
        logger.info(">> SWITCH REQUEST <<");
        logger.info(">> SWITCH REQUEST BEAN VALUES <<");
        logger.info("-- Status Code : " + requestBean.getStatusCode());
        logger.info("-- Status Message : " + requestBean.getStatusMessage());
        logger.info("-- Data : " + requestBean.getData().getMid() + "|"
                + requestBean.getData().getTid() + "|"
                + requestBean.getData().getAmount() + "|"
                + requestBean.getData().getTxnId() + "|"
                + requestBean.getData().getReferenceId() + "|");
        SwitchResponse response = new SwitchResponse();
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            String decodedAuthString = new String(decoder.decode(authString.substring(6).getBytes()));
            String username = decodedAuthString.split(":")[0];
            String password = decodedAuthString.split(":")[1];
            if (AUTHENTICATION_TOKEN_SWT_USERNAME.equals(username) && AUTHENTICATION_TOKEN_SWT_PASSWORD.equals(password)) {

                BindingResult bindingResult = validateRequestBean(requestBean);

                if (!bindingResult.hasErrors()) {
                    this.socketService.notifyPayment(requestBean.getStatusCode(),requestBean.getStatusMessage(),requestBean.getData());
                    response.setStatusCode(MessageVarList.SUCCESS);
                    response.setMessage(MessageVarList.SUCCESS_MESSAGE);
                    return new ResponseEntity(HttpStatus.OK);
                } else {
                    String errorMsg = messageSource.getMessage(bindingResult.getAllErrors().get(0).getCode(), new Object[]{bindingResult.getAllErrors().get(0).getDefaultMessage()}, Locale.US);
                    response.setStatusCode(MessageVarList.ERROR);
                    response.setMessage(errorMsg);
                    return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
                }
            } else {
                response.setStatusCode(MessageVarList.ERROR);
                response.setMessage(MessageVarList.SWITCH_REQUEST_ERROR_AUTHENTICATION_INVALID_USERNAME_PASS);
                logger.error("Error : " + MessageVarList.SWITCH_REQUEST_ERROR_AUTHENTICATION_INVALID_TOKEN);
                return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            response.setStatusCode(MessageVarList.EXCEPTION);
            response.setMessage(ex.getMessage());
            logger.error("Exception : " + ex);
            return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/listen")
    public ResponseEntity test(@RequestBody SwitchRequest requestBean, @RequestHeader(value = "authorization") String authString) throws InterruptedException {
        logger.info("Class: " + getClass() + ",Method: test ,Mapping: /api/v1/pay/listen");
        SwitchResponse response = new SwitchResponse();
        try {

            Base64.Decoder decoder = Base64.getDecoder();
            String decodedAuthString = new String(decoder.decode(authString.substring(6).getBytes()));
            String username = decodedAuthString.split(":")[0];
            String password = decodedAuthString.split(":")[1];

            if (AUTHENTICATION_TOKEN_SWT_USERNAME.equals(username) && AUTHENTICATION_TOKEN_SWT_PASSWORD.equals(password)) {

                BindingResult bindingResult = validateRequestBean(requestBean);

                if (bindingResult.hasErrors()) {
                    String errorMsg = messageSource.getMessage(bindingResult.getAllErrors().get(0).getCode(), new Object[]{bindingResult.getAllErrors().get(0).getDefaultMessage()}, Locale.US);
                    response.setStatusCode(MessageVarList.ERROR);
                    response.setMessage(errorMsg);
                    return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
                } else {
                    this.socketService.notifyPayment(requestBean.getStatusCode(),requestBean.getStatusMessage(),requestBean.getData());
                    response.setStatusCode(MessageVarList.SUCCESS);
                    response.setMessage("success");
                    return new ResponseEntity(response, HttpStatus.OK);
                }
            } else {
                response.setStatusCode(MessageVarList.ERROR);
                response.setMessage("error");
                return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception x) {
            logger.error("Error : " + x.getMessage());
            response.setStatusCode(MessageVarList.EXCEPTION);
            response.setMessage(x.getMessage());
            return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public BindingResult validateRequestBean(Object o) {
        DataBinder dataBinder = new DataBinder(o);
        dataBinder.setValidator(switchRequestValidator);
        dataBinder.validate();
        return dataBinder.getBindingResult();
    }

}

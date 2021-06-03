package com.boc.webqr.controller;

import com.boc.webqr.bean.*;
import com.boc.webqr.repository.ServiceRepository;
import com.boc.webqr.service.GenerateQRString;
import com.boc.webqr.service.impl.SocketService;
import com.boc.webqr.util.varlist.MessageVarList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/api/v1/swt")
public class ServiceController {

    private static final Logger logger = LogManager.getLogger(ServiceController.class);
    private static final String AUTHENTICATION_TOKEN_WEB_USERNAME = "admin";
    private static final String AUTHENTICATION_TOKEN_WEB_PASSWORD = "admin";

    @Autowired
    SessionBean sessionBean;

    @Autowired
    GenerateQRString generateQR;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    SocketService socketService;


    @PostMapping("/listen")
    public String listen(@RequestParam("msg") String s, @RequestParam("refid") String refId) throws InterruptedException {
        logger.info("Class: " + getClass() + ",Method: listen , Mapping: /api/v1/swt/listen");
        System.out.println("session bean listen " + sessionBean.getMerchantBean().getMid());
        Thread.sleep(5000);
        return s;
    }

    @PostMapping("/getqrstring")
    public ResponseEntity getQRString(@RequestHeader(value = "authorization") String authString,
                                      @RequestBody RequestBean requestBean) {
        logger.info("Class: " + getClass() + ",Method: getQRString , Mapping: /api/v1/swt/getqrstring");
        ResponseBean responseBean = new ResponseBean();
        QRGenerateData qrData = new QRGenerateData();
        String message = "";
        try {

            Base64.Decoder decoder = Base64.getDecoder();
            String decodedAuthString = new String(decoder.decode(authString.substring(6).getBytes()));
            String username = decodedAuthString.split(":")[0];
            String password = decodedAuthString.split(":")[1];

            if (AUTHENTICATION_TOKEN_WEB_USERNAME.equals(username) && AUTHENTICATION_TOKEN_WEB_PASSWORD.equals(password)) {

                // call to get qr string
                sessionBean.getMerchantBean().setReferenceLabel(requestBean.getReferenceNo());
                sessionBean.getMerchantBean().setAmount(requestBean.getAmount());
                String qrString = generateQR.generateQRString(sessionBean.getMerchantBean());

                // Insert QR request
                message = serviceRepository.insertWebQRRequest(requestBean, qrString);

                if (message.isEmpty()) {
                    qrData.setAmount(sessionBean.getMerchantBean().getAmount());
                    qrData.setMerchantName(sessionBean.getMerchantBean().getLegalName());
                    qrData.setMid(requestBean.getMid());
                    qrData.setTid(requestBean.getTid());
                    qrData.setReferenceId(requestBean.getReferenceNo());
                    qrData.setQrString(qrString);

                    logger.info("Generated LQR String : " + qrData.getQrString());

                    // For timeout
                    KeyValueBean time = serviceRepository.getTimeOut();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                 Thread.sleep(Long.parseLong(time.getValue())*60*1000);
//                                Thread.sleep(Long.parseLong(time.getValue()) * 10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            // send message to the socket
                            socketService.notifyPaymentTimeOut(MessageVarList.TIMEOUT, MessageVarList.TIMEOUT_MESSAGE,requestBean);
                        }
                    }, requestBean.getReferenceNo()).start();

                    responseBean.setContent(qrData);
                    responseBean.setResponseCode(MessageVarList.SUCCESS);
                    responseBean.setResponseMsg(MessageVarList.SUCCESS_MESSAGE);
                    return new ResponseEntity(responseBean, HttpStatus.OK);
                } else {
                    logger.error("Error : " + message);
                    responseBean.setContent(requestBean);
                    responseBean.setResponseCode(MessageVarList.ERROR);
                    responseBean.setResponseMsg(message);
                    return new ResponseEntity(responseBean,HttpStatus.UNAUTHORIZED);
                }
            } else {
                logger.error("Error : " + MessageVarList.SWITCH_REQUEST_ERROR_AUTHENTICATION_EMPTY_USERNAME_PASS);
                responseBean.setContent(requestBean);
                responseBean.setResponseCode(MessageVarList.ERROR);
                responseBean.setResponseMsg(MessageVarList.SWITCH_REQUEST_ERROR_AUTHENTICATION_EMPTY_USERNAME_PASS);
                return new ResponseEntity(responseBean,HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            logger.error("Exception : " + ex);
            responseBean.setContent(requestBean);
            responseBean.setResponseCode(MessageVarList.ERROR);
            responseBean.setResponseMsg(ex.getMessage());
            return new ResponseEntity(responseBean,HttpStatus.UNAUTHORIZED);
        }
    }

}

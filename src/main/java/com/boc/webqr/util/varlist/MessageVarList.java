package com.boc.webqr.util.varlist;

import org.springframework.stereotype.Component;

@Component
public class MessageVarList {

    //-------------------------- start common messages----------------------------------------------------------------//
    public static final String COMMON_ERROR_PROCESS = "common.error.process";
    public static final String COMMON_ERROR_RECORD_DOESNOT_EXISTS = "common.error.record.doesnot.exists";
    public static final String COMMON_ERROR_NO_VALUE_CHANGE = "common.error.no.value.change";
    public static final String COMMON_ERROR_ALREADY_USE = "common.error.alreadyuse";
    public static final String COMMON_ERROR_INVALID_BEAN = "common.error.invalid.bean";
    //-------------------------- end common messages------------------------------------------------------------------//
    //-------------------------- Request validate messages -----------------------------------------------------------//
    public static final String REQUEST_ERROR_EMPTY_MID = "request.error.empty.mid";
    public static final String REQUEST_ERROR_INVALID_MID = "request.error.invalid.mid";
    public static final String REQUEST_ERROR_EMPTY_TID = "request.error.empty.tid";
    public static final String REQUEST_ERROR_EMPTY_AMOUNT = "request.error.empty.amount";
    public static final String REQUEST_ERROR_INVALID_AMOUNT = "request.error.invalid.amount";
    public static final String REQUEST_ERROR_EMPTY_HASH = "request.error.empty.hash";
    public static final String REQUEST_ERROR_INVALID_HASH = "request.error.invalid.hash";
    public static final String REQUEST_ERROR_EMPTY_SECRET = "request.error.empty.secret";
    public static final String REQUEST_ERROR_EMPTY_CURRENCY = "request.error.empty.currency";
    public static final String REQUEST_ERROR_EMPTY_RETURNURL = "request.error.empty.returnurl";

    //-------------------------- Switch request validate messages -----------------------------------------------------------//
    public static final String SWITCH_REQUEST_ERROR_AUTHENTICATION_EMPTY_USERNAME_PASS = "Empty username or password";
    public static final String SWITCH_REQUEST_ERROR_AUTHENTICATION_INVALID_USERNAME_PASS = "Username or password invalid";
    public static final String SWITCH_REQUEST_ERROR_AUTHENTICATION_INVALID_TOKEN = "Authorization failed";
    public static final String SWITCH_REQUEST_ERROR_EMPTY_STATUSCODE = "switch.request.error.empty.statuscode";
    public static final String SWITCH_REQUEST_ERROR_EMPTY_STATUSMESSAGE = "switch.request.error.empty.statusmessage";
    public static final String SWITCH_REQUEST_ERROR_EMPTY_MID = "switch.request.error.empty.mid";
    public static final String SWITCH_REQUEST_ERROR_EMPTY_TID = "switch.request.error.empty.tid";
    public static final String SWITCH_REQUEST_ERROR_EMPTY_AMOUNT = "switch.request.error.empty.amount";
    public static final String SWITCH_REQUEST_ERROR_EMPTY_REFERENCEID = "switch.request.error.empty.referenceid";
    public static final String SWITCH_REQUEST_ERROR_EMPTY_TXNID = "switch.request.error.empty.txnid";

    //-------------------------- Response codes -----------------------------------------------------------//
    public static final String SUCCESS = "00";
    public static final String ERROR = "01";
    public static final String EXCEPTION = "02";
    public static final String TIMEOUT = "99";
    public static final String SUCCESS_MESSAGE = "success";
    public static final String ERROR_MESSAGE = "success";
    public static final String TIMEOUT_MESSAGE = "Payment Request Timeout";

    // -------------------------- Web qr request ------------------------------------------------------------
    public static final String WEBQR_REQUEST_INSERTION_FAILED = "Failed to insert";
}

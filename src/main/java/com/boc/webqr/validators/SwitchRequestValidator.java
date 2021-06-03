package com.boc.webqr.validators;

import com.boc.webqr.bean.RequestBean;
import com.boc.webqr.bean.SwitchRequest;
import com.boc.webqr.repository.ValidationRepository;
import com.boc.webqr.service.MD5Service;
import com.boc.webqr.util.validation.Validation;
import com.boc.webqr.util.varlist.MessageVarList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SwitchRequestValidator implements Validator {
    private final Logger logger = LogManager.getLogger(getClass());

    @Autowired
    MessageVarList messageVarList;

    @Autowired
    MD5Service md5;

    @Autowired
    Validation validation;

    @Override
    public boolean supports(Class<?> aClass) {
        return SwitchRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        try {
            if (o.getClass().equals(SwitchRequest.class)) {

                ValidationUtils.rejectIfEmpty(errors, "statusCode", messageVarList.SWITCH_REQUEST_ERROR_EMPTY_STATUSCODE, messageVarList.SWITCH_REQUEST_ERROR_EMPTY_STATUSCODE);
                ValidationUtils.rejectIfEmpty(errors, "statusMessage", messageVarList.SWITCH_REQUEST_ERROR_EMPTY_STATUSMESSAGE, messageVarList.SWITCH_REQUEST_ERROR_EMPTY_STATUSMESSAGE);
                ValidationUtils.rejectIfEmpty(errors, "data.mid", messageVarList.SWITCH_REQUEST_ERROR_EMPTY_MID, messageVarList.SWITCH_REQUEST_ERROR_EMPTY_MID);
                ValidationUtils.rejectIfEmpty(errors, "data.tid", messageVarList.SWITCH_REQUEST_ERROR_EMPTY_TID, messageVarList.SWITCH_REQUEST_ERROR_EMPTY_TID);
                ValidationUtils.rejectIfEmpty(errors, "data.amount", messageVarList.SWITCH_REQUEST_ERROR_EMPTY_AMOUNT, messageVarList.SWITCH_REQUEST_ERROR_EMPTY_AMOUNT);
                ValidationUtils.rejectIfEmpty(errors, "data.referenceId", messageVarList.SWITCH_REQUEST_ERROR_EMPTY_REFERENCEID, messageVarList.SWITCH_REQUEST_ERROR_EMPTY_REFERENCEID);
                ValidationUtils.rejectIfEmpty(errors, "data.txnId", messageVarList.SWITCH_REQUEST_ERROR_EMPTY_TXNID, messageVarList.SWITCH_REQUEST_ERROR_EMPTY_TXNID);

            } else {
                logger.error("Error : ", messageVarList.COMMON_ERROR_INVALID_BEAN);
                errors.reject(messageVarList.COMMON_ERROR_INVALID_BEAN);
            }
        } catch (Exception ex) {
            logger.error("Exception : ", ex);
            errors.reject(messageVarList.COMMON_ERROR_PROCESS);
        }
    }

}

package com.boc.webqr.validators;

import com.boc.webqr.bean.RequestBean;
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
public class RequestBeanValidator implements Validator {
    private final Logger logger = LogManager.getLogger(getClass());

    @Autowired
    MessageVarList messageVarList;

    @Autowired
    MD5Service md5;

    @Autowired
    Validation validation;

    @Autowired
    ValidationRepository validationRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return RequestBean.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        try {
            if (o.getClass().equals(RequestBean.class)) {

                ValidationUtils.rejectIfEmpty(errors, "mid", messageVarList.REQUEST_ERROR_EMPTY_MID, "Request bean mid can not be empty.");
                ValidationUtils.rejectIfEmpty(errors, "tid", messageVarList.REQUEST_ERROR_EMPTY_TID, "Request bean tid can not be empty.");
                ValidationUtils.rejectIfEmpty(errors, "amount", messageVarList.REQUEST_ERROR_EMPTY_AMOUNT, "Request bean amount can not be empty.");
                ValidationUtils.rejectIfEmpty(errors, "hash", messageVarList.REQUEST_ERROR_EMPTY_HASH, "Request bean hash can not be empty.");
                ValidationUtils.rejectIfEmpty(errors, "secret", messageVarList.REQUEST_ERROR_EMPTY_SECRET, "Request bean secret can not be empty.");
                ValidationUtils.rejectIfEmpty(errors, "currency", messageVarList.REQUEST_ERROR_EMPTY_CURRENCY, "Request bean currency can not be empty.");
                ValidationUtils.rejectIfEmpty(errors, "returnUrl", messageVarList.REQUEST_ERROR_EMPTY_RETURNURL, "Request bean returnUrl can not be empty.");

                if (!errors.hasErrors()) {
                    String amount = ((RequestBean) o).getAmount();
                    if (!validation.isValidAmount(amount)) {
                        errors.rejectValue("amount", messageVarList.REQUEST_ERROR_INVALID_AMOUNT, messageVarList.REQUEST_ERROR_INVALID_AMOUNT);
                    }
                }

                if (!errors.hasErrors()) {
                    RequestBean requestBean = ((RequestBean) o);
                    String genVal = md5.getMd5(requestBean.getMid() + requestBean.getTid() + requestBean.getAmount() + requestBean.getCurrency() + md5.getMd5(requestBean.getMid() + requestBean.getSecret()).toUpperCase()).toUpperCase();
                    if (!genVal.equals(requestBean.getHash())) {
                        errors.rejectValue("hash", messageVarList.REQUEST_ERROR_INVALID_HASH, messageVarList.REQUEST_ERROR_INVALID_HASH);
                    }
                }

                if (!errors.hasErrors()) {
                    RequestBean requestBean = ((RequestBean) o);
                    boolean merchantValidate = validationRepository.checkMerchantByMidTid(requestBean.getMid(), requestBean.getTid());
                    if (!merchantValidate) {
                        errors.rejectValue("mid", messageVarList.REQUEST_ERROR_INVALID_MID, messageVarList.REQUEST_ERROR_INVALID_MID);
                    }
                }

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

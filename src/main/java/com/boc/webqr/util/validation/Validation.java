package com.boc.webqr.util.validation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Scope("prototype")
public class Validation {
    private final Log logger = LogFactory.getLog(getClass());

    public boolean isEmptyFieldValue(String value) {
        boolean isErrorField = true;
        try {
            if (value != null && !value.isEmpty()) {
                isErrorField = false;
            }
        } catch (Exception e) {
            logger.error("Exception : ", e);
        }
        return isErrorField;
    }

    public boolean isValidAmount(String value) {
        boolean isValidAmount = false;
        String AMOUNT_REGEX_PATTERN = "\\d+(\\.\\d{1,2})?";
        try {
            if (value.equals("0") || value.equals("0.00") || value.equals("0.0")) {
                return isValidAmount;
            } else {
                Pattern pattern = Pattern.compile(AMOUNT_REGEX_PATTERN);
                Matcher matcher = pattern.matcher(value);
                isValidAmount = matcher.matches();
            }
        } catch (Exception e) {
            return isValidAmount;
        }
        return isValidAmount;
    }


}

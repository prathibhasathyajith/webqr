package com.boc.webqr.util.validation;

import org.springframework.validation.BindingResult;

public interface BeanValidation<T> {

    BindingResult validateRequestBean(T t);
}

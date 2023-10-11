package com.javahk.project.finnhub.exception;

import com.javahk.project.finnhub.infra.BusinessException;
import com.javahk.project.finnhub.infra.Code;

public class FinnhubException extends BusinessException {

    public FinnhubException(Code code) {
        super(code);
    }

    // BusinessException suppose can work within the whole organization
    // which the range may be too wide
    // so seperate one more layer FinnhubException only use in this project 
}

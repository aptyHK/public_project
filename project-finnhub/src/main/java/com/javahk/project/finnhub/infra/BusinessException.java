package com.javahk.project.finnhub.infra;

public class BusinessException extends Exception {
    
    private int code;

    public int getCode() {
        return this.code;
    }

    public BusinessException(Code code) {
        super(code.getDesc());
        this.code = code.getCode();
    }

    // Work with Code.java, tackle all the expected exception may happen and give them proper description
}

package com.duzzi.sdk.core.exception;

public class CommonException extends Throwable {
    private int errorCode;
    private String errorMessage;

    public CommonException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
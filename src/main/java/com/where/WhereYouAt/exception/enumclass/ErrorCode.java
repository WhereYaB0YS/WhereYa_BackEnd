package com.where.WhereYouAt.exception.enumclass;

public enum ErrorCode {
    EXPIRED_TOKEN(1001,"토큰이 만료되었습니다"),
    INVALID_TOKEN(1002,"유효하지 않은 토큰입니다"),
    NOTEXSITED_TOKEN(1003,"토큰이 없습니다");

    private int errorCode;
    private String errorMessage;

    ErrorCode(int errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }
}

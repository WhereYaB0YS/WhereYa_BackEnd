package com.where.WhereYouAt.exception;

public class NotExistedTokenException extends RuntimeException {

    private static final String MESSAGE ="토큰이 없습니다";
    public NotExistedTokenException(){
        super(MESSAGE);
    }
}

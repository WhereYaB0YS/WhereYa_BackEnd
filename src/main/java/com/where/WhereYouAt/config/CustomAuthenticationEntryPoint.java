package com.where.WhereYouAt.config;

import com.where.WhereYouAt.exception.dto.ErrorResponse;
import com.where.WhereYouAt.exception.enumclass.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        String exception = (String)request.getAttribute("exception");

        if(exception==null){
            ErrorResponse.of(HttpStatus.BAD_REQUEST,ErrorCode.NOTEXSITED_TOKEN.getErrorMessage());
            System.out.println("토큰 없다");
            return;
        }

        if(exception.equals(ErrorCode.EXPIRED_TOKEN.getErrorCode())){
            ErrorResponse.of(HttpStatus.BAD_REQUEST,ErrorCode.EXPIRED_TOKEN.getErrorMessage());
            return;
        }

        if(exception.equals(ErrorCode.INVALID_TOKEN.getErrorCode())){
            ErrorResponse.of(HttpStatus.BAD_REQUEST,ErrorCode.INVALID_TOKEN.getErrorMessage());
            return;
        }
    }
}

package com.where.WhereYouAt.message;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Data
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage {

    private String code;
    private String message;
    private Object data;

    public ResponseMessage(HttpStatus httpStatus, String message){
        this.code = String.valueOf(httpStatus.value());
        this.message = message;
    }

    public ResponseMessage(Object object) {
        this.data = object;
    }
}

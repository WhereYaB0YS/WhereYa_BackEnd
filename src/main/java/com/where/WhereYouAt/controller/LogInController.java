package com.where.WhereYouAt.controller;

import com.where.WhereYouAt.annotation.Timer;
import com.where.WhereYouAt.controller.dto.user.LoginRequestDto;
import com.where.WhereYouAt.controller.dto.user.LoginResponseDto;
import com.where.WhereYouAt.domain.User;
import com.where.WhereYouAt.domain.utils.JwtUtil;
import com.where.WhereYouAt.message.ResponseMessage;
import com.where.WhereYouAt.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@Api(tags={"Login 관련 Controller"})
@RequestMapping(value = "/user/login")
@RestController
@RequiredArgsConstructor
public class LogInController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    // get 으로 보냈을 때 error 내주기 하는 방법

    @ApiResponse(code = 400, message = "아이디가 틀린 경우")
    @ApiOperation(value = "로그인")
    @Timer // 직접 만든 annotation
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody @Valid LoginRequestDto dto){
        return ResponseEntity.ok(userService.authentication(dto));
    }
}

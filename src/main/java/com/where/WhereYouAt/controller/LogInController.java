package com.where.WhereYouAt.controller;

import com.where.WhereYouAt.annotation.Timer;
import com.where.WhereYouAt.controller.dto.user.LoginRequestDto;
import com.where.WhereYouAt.controller.dto.user.LoginResponseDto;
import com.where.WhereYouAt.domain.User;
import com.where.WhereYouAt.domain.utils.JwtUtil;
import com.where.WhereYouAt.message.ResponseMessage;
import com.where.WhereYouAt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RequestMapping(value = "/user/login")
@RestController
public class LogInController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // get으로 보냈을 때 error 내주기 하는 방법

//    @Timer // 직접 만든 annotation
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto dto) throws URISyntaxException {
        String userId = dto.getUserId();
        String password = dto.getPassword();

        User user = userService.authentication(userId,password);

        String jwt = jwtUtil.createToken(user.getId(),user.getNickname());

        LoginResponseDto responseDto = LoginResponseDto.builder().jwt(jwt).nickname(user.getNickname()).build();

        return ResponseEntity.ok(responseDto);
    }
}

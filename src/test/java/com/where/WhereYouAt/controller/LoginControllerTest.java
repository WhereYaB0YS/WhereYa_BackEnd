package com.where.WhereYouAt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.where.WhereYouAt.SecurityJavaConfig;
import com.where.WhereYouAt.controller.dto.user.LoginRequestDto;
import com.where.WhereYouAt.controller.dto.user.LoginResponseDto;
import com.where.WhereYouAt.domain.User;
import com.where.WhereYouAt.domain.dto.Birthday;
import com.where.WhereYouAt.domain.utils.JwtUtil;
import com.where.WhereYouAt.enumclass.GenderStatus;
import com.where.WhereYouAt.repository.UserRepository;
import com.where.WhereYouAt.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@WebMvcTest(LogInController.class)
@AutoConfigureWebMvc
public class LoginControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private MockMvc mockMvc;

//    @BeforeEach
//    public void init(){
//
//        String jwt =  "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MzQyODc5OTYsInVzZXJJZCI6MSwibmlja25hbWUiOiJoYW1pbmfsp4TshJ0ifQ.uiSJYlJOdH0rqonJA-A3VZRdSwg1BZjvBhLivZrLnME";
//        String nickname=  "haming진석";
//
//        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
//                .jwt(jwt)
//                .nickname(nickname)
//                .build();
//
////        new ResponseEntity.ok();
////
////        Mockito.when(userService.authentication(loginRequestDto)).thenReturn(loginResponseDto);
//    }

    @Test
    public void loginTest() throws Exception {
        String userId = "wya111";
        String password = "1234";

        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .userId(userId)
                .password(password)
                .build();

        String json = new ObjectMapper().writeValueAsString(loginRequestDto);

        User user = User.builder()
                .id(1L)
                .userId("wya111")
                .password("$2a$10$WITI3wjWXu9MWfvppMOafuosC870rPP7dbgoBJg7vaYx/k7piayxS")
                .nickname("haming진석")
                .birthday(Birthday.of(LocalDate.of(1997,06,27)))
                .gender(GenderStatus.valueOf("MALE"))
                .build();

        String jwt =  "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MzQzNjkwNjIsInVzZXJJZCI6MSwibmlja25hbWUiOiJoYW1pbmfsp4TshJ0ifQ.08pcY_GDQY3FgyYjskAmZsqhQA5lfrFVtyqYJi_KOCQ";

        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .jwt(jwt)
                .nickname(user.getNickname())
                .build();

        Mockito.when(userService.authentication(loginRequestDto)).thenReturn(loginResponseDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("http://localhost:8080/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.nickname").value(user.getNickname())
        ).andDo(MockMvcResultHandlers.print());
    }
}


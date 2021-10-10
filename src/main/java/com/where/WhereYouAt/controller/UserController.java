package com.where.WhereYouAt.controller;

import com.where.WhereYouAt.controller.dto.user.*;
import com.where.WhereYouAt.domain.User;
import com.where.WhereYouAt.message.ResponseMessage;
import com.where.WhereYouAt.repository.UserRepository;
import com.where.WhereYouAt.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RequestMapping(value = "/user")
@RestController
@Slf4j
public class UserController{
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    //회원 정보 조회
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    //프로필 이미지 조회
    @GetMapping
    public ResponseEntity<ProfileImgDto>getMyInfo(Authentication authentication){
        Claims claims = (Claims)authentication.getPrincipal();
        Long id = claims.get("userId",Long.class);

        User user = userService.getMyinfo(id);

        ProfileImgDto responseDto = ProfileImgDto.builder()
                .profileImg(user.getProfileImg())
                .build();

       return ResponseEntity.ok(responseDto);
    }

    //아이디 중복 확인
    @PostMapping("/check/userId")
    public ResponseEntity<ResponseMessage> checkUserId(@RequestBody @Valid CheckUserIdDto dto){
        userService.checkUserId(dto.getUserId());

        return ResponseEntity.ok(new ResponseMessage(HttpStatus.OK,"ok"));
    }

    //닉네임 중복 확인
    @PostMapping("/check/nickname")
    public ResponseEntity<ResponseMessage> checkNickname(@RequestBody @Valid CheckNicknameDto dto){
        userService.checkNickname(dto.getNickname());

        return ResponseEntity.ok(new ResponseMessage(HttpStatus.OK,"ok"));
    }


    //회원가입
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();

            bindingResult.getAllErrors().forEach(objectError -> {
                FieldError field = (FieldError) objectError;
                String msg = objectError.getDefaultMessage();

//                System.out.println("field: "+field.getField());
//                System.out.println(msg);

//                sb.append("field: "+field.getField());
//                sb.append("\n");
                sb.append("message: "+msg+" ");
            });
            return ResponseEntity.ok(new ResponseMessage(HttpStatus.BAD_REQUEST, sb.toString()));
        }

        userService.createUser(userDto);

        return ResponseEntity.ok(new ResponseMessage(HttpStatus.CREATED,"ok"));
    }

    //프로필 수정
    @PatchMapping
    public ResponseEntity<ResponseMessage> modifyUser(Authentication authentication, @RequestBody ModUserDto userDto){
        Claims claims = (Claims) authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);

        userService.modifyUser(userId,userDto.getNickname());

        return ResponseEntity.ok(new ResponseMessage(HttpStatus.OK,"ok"));
    }

    //프로필 이미지 업로드
    @PutMapping("/upload/img")
    public ResponseEntity<ProfileImgDto> uploadImg(Authentication authentication, @RequestParam("data") MultipartFile file) throws IOException {
        Claims claims = (Claims) authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);
        String img = userService.uploadImg(userId,file);
        return ResponseEntity.ok(ProfileImgDto.builder().profileImg(img).build());
    }

    //회원탈퇴
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseMessage> deleteUser(Authentication authentication){
        Claims claims = (Claims) authentication.getPrincipal();
        Long userId = claims.get("userId",Long.class);

        userService.deleteUser(userId);

        return ResponseEntity.ok(new ResponseMessage(HttpStatus.OK,"ok"));
    }

}


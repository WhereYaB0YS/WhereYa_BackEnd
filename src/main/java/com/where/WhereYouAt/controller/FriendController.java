package com.where.WhereYouAt.controller;


import com.where.WhereYouAt.controller.dto.friend.FriendsListResponseDto;
import com.where.WhereYouAt.domain.utils.JwtUtil;
import com.where.WhereYouAt.message.ResponseMessage;
import com.where.WhereYouAt.service.FriendService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(value = "/friend")
@RestController
public class FriendController {

    private final FriendService friendService;

    private final JwtUtil jwtUtil;


    //친구추가
    @PostMapping("/{friendNickname}")
    public ResponseEntity<ResponseMessage> addFriend(Authentication authentication, @PathVariable String friendNickname){
        Claims claims = jwtUtil.checkToken(authentication);
        Long userId = claims.get("userId",Long.class);

        friendService.addFriend(userId,friendNickname);

        return ResponseEntity.ok(new ResponseMessage(HttpStatus.CREATED,"ok"));

    }

    //친구목록 보기
    @GetMapping
    public ResponseEntity<FriendsListResponseDto> getFriends(Authentication authentication){
        Claims claims = jwtUtil.checkToken(authentication);
        Long userId = claims.get("userId",Long.class);

        FriendsListResponseDto dto = FriendsListResponseDto.builder()
                .friends(friendService.getFriends(userId))
                .build();

        return ResponseEntity.ok(dto);
    }

    //친구삭제
    @DeleteMapping("/{friendNickname}")
    public ResponseEntity<ResponseMessage> deleteFriend(Authentication authentication,@PathVariable String friendNickname){
        Claims claims = jwtUtil.checkToken(authentication);
        Long userId = claims.get("userId",Long.class);

        friendService.deleteFriend(userId,friendNickname);

        return ResponseEntity.ok(new ResponseMessage(HttpStatus.OK,"ok"));
    }

    //즐겨찾기
    @PostMapping("/bookmark/{friendNickname}")
    public ResponseEntity<ResponseMessage> addStar(Authentication authentication, @PathVariable String friendNickname){
        Claims claims = jwtUtil.checkToken(authentication);
        Long userId = claims.get("userId",Long.class);

        String message = friendService.bookmark(userId,friendNickname);

        return ResponseEntity.ok(new ResponseMessage(HttpStatus.OK,message));
    }
}

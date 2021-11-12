package com.where.WhereYouAt.controller;

import com.where.WhereYouAt.controller.dto.appointment.*;
import com.where.WhereYouAt.domain.utils.JwtUtil;
import com.where.WhereYouAt.message.ResponseMessage;
import com.where.WhereYouAt.service.AppointmentService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDate;

@RequiredArgsConstructor
@RequestMapping(value = "/promise")
@RestController
public class AppointmentController {

    private final AppointmentService appointmentService;

    private final JwtUtil jwtUtil;

    //약속 추가
    @PostMapping
    public ResponseEntity<ResponseMessage> addAppointment(Authentication authentication, @RequestBody @Valid AppointmentRequestDto dto){
        Claims claims = jwtUtil.checkToken(authentication);
        Long userId = claims.get("userId",Long.class);

        appointmentService.addAppointment(userId,dto);

        return ResponseEntity.ok(new ResponseMessage(HttpStatus.CREATED,"ok"));
    }

    //날짜별 약속유무 조회
    @GetMapping("/checkDate")
    public ResponseEntity<AppointmentcheckDateDto> getCheckedAppointment(Authentication authentication){
        Claims claims = jwtUtil.checkToken(authentication);
        Long userId = claims.get("userId",Long.class);

        return ResponseEntity.ok(AppointmentcheckDateDto.builder()
                .datesWithEvent(appointmentService.getCheckedAppointment(userId))
                .build());
    }

    //날짜별로 약속목록 조회
    @GetMapping("/date/{date}")
    public ResponseEntity<AppointmentListResponseDto2> getAppointmentsByDate(Authentication authentication, @PathVariable String date){
        Claims claims = jwtUtil.checkToken(authentication);
        Long userId = claims.get("userId",Long.class);

        AppointmentListResponseDto2 list =  AppointmentListResponseDto2.builder()
                .promiseList(appointmentService.getAppointmentByDate(userId,LocalDate.parse(date)))
                .build();

        return ResponseEntity.ok(list);
    }

    //다가올 약속 조회
    @GetMapping("/proximate")
    public ResponseEntity<AppointmentProximateDto> getApproachedAppointment(Authentication authentication){
        Claims claims = jwtUtil.checkToken(authentication);
        Long userId = claims.get("userId",Long.class);

        return ResponseEntity.ok(appointmentService.getApproachedAppointment(userId));
    }

    //약속목록 조회
    @GetMapping
    public ResponseEntity<AppointmentListResponseDto> getAppointments(Authentication authentication){
        Claims claims = jwtUtil.checkToken(authentication);
        Long userId = claims.get("userId",Long.class);

        AppointmentListResponseDto list =  AppointmentListResponseDto.builder()
                .promiseList(appointmentService.getAppointments(userId))
                .build();

        return ResponseEntity.ok(list);
    }

    //지난 약속 전체 조회
    @GetMapping("/passed")
    public ResponseEntity<AppointmentListResponseDto> getLastedAppointments(Authentication authentication){
        Claims claims = jwtUtil.checkToken(authentication);
        Long userId = claims.get("userId",Long.class);

        return ResponseEntity.ok(AppointmentListResponseDto.builder().promiseList(appointmentService.getLastedAppointments(userId)).build());
    }

    //약속수정
    @PatchMapping("/{promiseId}")
    public ResponseEntity<ResponseMessage> editAppointment(Authentication authentication, @PathVariable Long promiseId, @RequestBody @Valid AppointmentRequestDto dto){
        Claims claims = jwtUtil.checkToken(authentication);
        Long userId = claims.get("userId",Long.class);

        appointmentService.editAppointment(userId,promiseId,dto);

        return ResponseEntity.ok(new ResponseMessage(HttpStatus.CREATED,"ok"));
    }

    //약속삭제
    @DeleteMapping("/{promiseId}")
    public ResponseEntity<ResponseMessage>deleteAppointment(Authentication authentication, @PathVariable Long promiseId){
        Claims claims = jwtUtil.checkToken(authentication);
        Long userId = claims.get("userId",Long.class);

        appointmentService.deleteAppointment(userId,promiseId);

        return ResponseEntity.ok(new ResponseMessage(HttpStatus.OK,"ok"));
    }

}

package com.where.WhereYouAt.controller.dto.user;

import com.where.WhereYouAt.annotation.Gender;
import com.where.WhereYouAt.annotation.YearMonthDay;
import com.where.WhereYouAt.enumclass.GenderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto{
    @NotBlank(message = "Id는 필수 값 입니다.")
    private String userId;

    @NotBlank(message = "비밀번호는 필수 값 입니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수 값 입니다.")
    private String nickname;

    @NotBlank(message = "성별을 선택해 주세요.")
    @Gender(enumClass = GenderStatus.class, ignoreCase = true)
    private String gender;

    @NotBlank(message = "생년월일을 입력해 주세요.")
    @YearMonthDay
    private String birthday;
}
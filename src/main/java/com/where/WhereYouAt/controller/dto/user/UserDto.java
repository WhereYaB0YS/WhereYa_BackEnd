package com.where.WhereYouAt.controller.dto.user;

import com.where.WhereYouAt.annotation.YearMonthDay;
import com.where.WhereYouAt.enumclass.GenderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    @NotBlank(message = "Id는 필수 값 입니다.")
    private String userId;

    @NotBlank(message = "비밀번호는 필수 값 입니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수 값 입니다.")
    private String nickname;

    //TODO: gender validation 처리
    @NotNull(message = "성별을 선택해 주세요.")
    private GenderStatus gender;

    @YearMonthDay
    private String birthday;
}
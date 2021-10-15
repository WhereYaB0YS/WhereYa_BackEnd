package com.where.WhereYouAt.controller.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDto {

    @ApiModelProperty(value = "사용자의 아이디",example = "wya111",required = true)
    @NotEmpty
    private String userId;

    @ApiModelProperty(value = "사용자의 비밀번호",example = "1234",required = true)
    @NotEmpty
    private String password;
}

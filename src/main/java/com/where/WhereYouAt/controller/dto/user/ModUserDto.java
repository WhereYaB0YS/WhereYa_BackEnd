package com.where.WhereYouAt.controller.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModUserDto {

    @NotBlank(message = "변경할 닉네임은 필수 값 입니다")
    private String nickname;
}

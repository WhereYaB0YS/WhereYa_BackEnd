package com.where.WhereYouAt.controller.dto.appointment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentRequestDto {

    private Long id;

    @NotBlank(message = "Id는 필수 값 입니다.")
    private String name;

    private String memo;

    @NotNull(message = "날짜는 필수 값 입니다")
    private LocalDate date;

    @NotNull(message = "시간은 필수 값 입니다")
    private LocalTime time;

    @Valid
    private DestinationDto destination;

    private List<String> friends;
}

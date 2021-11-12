package com.where.WhereYouAt.controller.dto.appointment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.where.WhereYouAt.domain.dto.Destination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DestinationDto {

    @NotBlank(message = "약속 장소는 필수 값 입니다")
    @JsonProperty("place_name")
    private String placeName;

    @NotBlank(message = "약속 장소는 필수 값 입니다")
    @JsonProperty("address_name")
    private String addressName;

    @NotBlank(message = "좌표는 필수 값 입니다")
    private String x;

    @NotBlank(message = "좌표는 필수 값 입니다")
    private String y;

    public DestinationDto (Destination destination){
        this.placeName = destination.getPlaceName();
        this.addressName = destination.getAddressName();
        this.x = destination.getX();
        this.y = destination.getY();
    }

    public static DestinationDto of(Destination destination){
        return new DestinationDto(destination);
    }
}

package com.where.WhereYouAt.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Embeddable
@Data
@NoArgsConstructor
public class Birthday {

    @NotNull
    private Integer yearOfBirthday;

    @NotNull
    private Integer monthOfBirthday;

    @NotNull
    private Integer dayOfBirthday;

    private Birthday(LocalDate birthday){
        this.yearOfBirthday = birthday.getYear();
        this.monthOfBirthday = birthday.getMonthValue();
        this.dayOfBirthday = birthday.getDayOfMonth();
    }

    public static Birthday of(LocalDate birthday){
        return new Birthday(birthday);
    }
}

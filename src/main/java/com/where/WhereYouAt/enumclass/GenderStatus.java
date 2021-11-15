package com.where.WhereYouAt.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public enum GenderStatus {

    MALE(0,"male"),
    FEMALE(1,"female");

    private Integer id;

    private String title;
}
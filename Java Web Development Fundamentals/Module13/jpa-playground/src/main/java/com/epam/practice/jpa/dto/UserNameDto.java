package com.epam.practice.jpa.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserNameDto {
    private Long id;
    private String name;
}

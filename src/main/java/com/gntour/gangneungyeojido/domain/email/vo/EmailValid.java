package com.gntour.gangneungyeojido.domain.email.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailValid {
    private String email;
    private String validCode;
    private String validYn;
    private Timestamp expired;
}

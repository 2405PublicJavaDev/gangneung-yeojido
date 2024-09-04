package com.gntour.gangneungyeojido.app.member.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class JoinRequest {
    private String memberId;
    private String password;
    private String confirmPassword;
    private String name;
    private Timestamp birthDate;
    private String email;
    private String phone;
    private String role;
}

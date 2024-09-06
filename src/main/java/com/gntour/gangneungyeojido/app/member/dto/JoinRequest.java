package com.gntour.gangneungyeojido.app.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.sql.Timestamp;

import static com.gntour.gangneungyeojido.common.Validation.*;

@Getter
@Setter
@ToString
public class JoinRequest {
    @Pattern(regexp = ID_VALIDATION)
    private String memberId;
    @Pattern(regexp = PW_VALIDATION)
    private String password;
    @Pattern(regexp = PW_VALIDATION)
    private String confirmPassword;
    @NotBlank
    private String name;
    @Pattern(regexp = BIRTHDATE_VALIDATION)
    private String birthDate;
    @Pattern(regexp = EMAIL_VALIDATION)
    private String email;
    @Pattern(regexp = PHONE_VALIDATION)
    private String phone;
    private String auth;
}

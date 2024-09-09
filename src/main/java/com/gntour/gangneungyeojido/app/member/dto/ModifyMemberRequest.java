package com.gntour.gangneungyeojido.app.member.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.gntour.gangneungyeojido.common.Validation.*;

@Getter
@Setter
@ToString
public class ModifyMemberRequest {
    @Pattern(regexp = PW_VALIDATION)
    private String password;
    @Pattern(regexp = PW_VALIDATION)
    private String confirmPassword;
    @Pattern(regexp = EMAIL_VALIDATION)
    private String email;
    @Pattern(regexp = PHONE_VALIDATION)
    private String phone;
    private String auth;
}

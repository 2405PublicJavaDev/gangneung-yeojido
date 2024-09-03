package com.gntour.gangneungyeojido.app.admin.dto;

import jakarta.validation.Validation;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.gntour.gangneungyeojido.common.Validation.ID_VALIDATION;
import static com.gntour.gangneungyeojido.common.Validation.PW_VALIDATION;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @Pattern(regexp = ID_VALIDATION)
    private String memberId;
    @Pattern(regexp = PW_VALIDATION)
    private String password;
}

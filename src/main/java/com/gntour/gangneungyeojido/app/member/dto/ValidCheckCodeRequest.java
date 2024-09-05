package com.gntour.gangneungyeojido.app.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.gntour.gangneungyeojido.common.Validation.EMAIL_VALIDATION;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidCheckCodeRequest {
    @Pattern(regexp = EMAIL_VALIDATION)
    private String email;
    @NotBlank
    private String auth;
}

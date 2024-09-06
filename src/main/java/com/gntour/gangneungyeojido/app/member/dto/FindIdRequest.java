package com.gntour.gangneungyeojido.app.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.gntour.gangneungyeojido.common.Validation.*;

@Getter
@Setter
@ToString
public class FindIdRequest {
    @NotBlank
    private String name;
    @Pattern(regexp = BIRTHDATE_VALIDATION)
    private String birthDate;
}

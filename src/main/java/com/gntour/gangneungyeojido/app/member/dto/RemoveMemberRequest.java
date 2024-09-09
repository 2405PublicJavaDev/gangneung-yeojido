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
public class RemoveMemberRequest {
    @Pattern(regexp = ID_VALIDATION)
    private String memberId;
    @Pattern(regexp = PW_VALIDATION)
    private String password;
}

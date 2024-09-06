package com.gntour.gangneungyeojido.app.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FindIdResponse {
    private String memberId;
}

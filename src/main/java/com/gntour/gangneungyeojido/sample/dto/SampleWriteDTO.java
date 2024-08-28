package com.gntour.gangneungyeojido.sample.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SampleWriteDTO {
    @NotBlank
    private String content;
}

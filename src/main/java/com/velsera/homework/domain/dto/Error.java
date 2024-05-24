package com.velsera.homework.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Error(
        @NotBlank @Size(max = 255) String description,
        @NotNull @Positive Integer httpCode,
        @NotNull @Positive Integer errorCode,
        @NotBlank @Size(max = 255) String message) {
}



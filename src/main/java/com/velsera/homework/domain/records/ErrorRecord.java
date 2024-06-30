package com.velsera.homework.domain.records;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorRecord(
        @NotNull @Positive Integer httpCode,
        @NotNull @Positive Integer errorCode,
        @NotBlank @Size(max = 255) String message) {
}



package com.velsera.homework.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PostTweetRequest(
        @NotBlank @Size(max = 255) String description,
        @NotBlank @Size(max = 320) String tweetBody,
        @Nullable @Size(max = 5) @Pattern(regexp = "^#[a-zA-Z]{2,16}$]") List<String> hashTags
) {

}



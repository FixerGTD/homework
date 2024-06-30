package com.velsera.homework.domain.records;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public record PostTweetRequestRecord(
        @NotBlank @Size(max = 320) String tweetBody,
        @Nullable @Size(max = 5) @Pattern(regexp = "^#[a-zA-Z]{2,16}$]") List<String> hashTags
) {

}



package com.velsera.homework.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TweetResponse(
        @NotNull Long tweetId,
        String tweetBody,
        List<String> hashTags,
        String createdBy,
        String createdAt) {
}

package com.velsera.homework.domain.records;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.ZonedDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TweetResponseRecord(
        Long tweetId,
        String tweetBody,
        List<String> hashTag,
        String createdBy,
        ZonedDateTime createdAt) {
}

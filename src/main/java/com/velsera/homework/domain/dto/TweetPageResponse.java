package com.velsera.homework.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.lang.Nullable;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TweetPageResponse(
        List<TweetResponse> tweetResponses,
        @Nullable String nextPage
) {
}

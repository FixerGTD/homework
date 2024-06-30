package com.velsera.homework.domain.records;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.lang.Nullable;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TweetPageResponseRecord(
        List<TweetResponseRecord> tweetResponseRecords,
        @Nullable String nextPage
) {
}

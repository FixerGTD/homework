package com.velsera.homework.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Map;

@Getter
@Setter
public class TweetDTO {

    private Long tweetId;
    public static final String TWEET_ID = "tweet_id";

    private String tweetBody;
    public static final String TWEET_BODY = "tweet_body";

    private String hashTags;
    public static final String HASH_TAGS = "hash_tags";

    private String createdBy;
    public static final String CREATED_BY = "created_by";

    private ZonedDateTime createdAt;
    public static final String CREATED_AT = "created_at";

    public TweetDTO(Object[] tuples, Map<String, Integer> aliasToIndexMap) {
        this.tweetId = (Long) tuples[aliasToIndexMap.get(TWEET_ID)];
        this.tweetBody = (String) tuples[aliasToIndexMap.get(TWEET_BODY)];
        this.hashTags = (String) tuples[aliasToIndexMap.get(HASH_TAGS)];
        this.createdBy = (String) tuples[aliasToIndexMap.get(CREATED_BY)];
        this.createdAt = (ZonedDateTime) tuples[aliasToIndexMap.get(CREATED_AT)];
    }
}

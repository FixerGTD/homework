package com.velsera.homework.controller.helper;

import com.velsera.homework.domain.records.TweetResponseRecord;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TweetResponseRecordGenerator {
    private static final TweetResponseRecordGenerator INSTANCE = new TweetResponseRecordGenerator();

    private TweetResponseRecordGenerator() {
    }

    public static TweetResponseRecordGenerator getInstance() {
        return INSTANCE;
    }

    private static final Random RANDOM = new Random();

    public List<TweetResponseRecord> generateRandomTweetResponseRecords(int numberOfRecords) {
        List<TweetResponseRecord> records = new ArrayList<>();
        for (int i = 0; i < numberOfRecords; i++) {
            records.add(createRandomTweetResponseRecord());
        }
        return records;
    }

    private TweetResponseRecord createRandomTweetResponseRecord() {
        Long tweetId = generateRandomId();
        String tweetBody = generateRandomTweetBody();
        List<String> hashTags = generateRandomHashTags();
        String createdBy = generateRandomUsername();
        ZonedDateTime createdAt = generateRandomCreatedAt();

        return new TweetResponseRecord(tweetId, tweetBody, hashTags, createdBy, createdAt);
    }

    private Long generateRandomId() {
        return Math.abs(RANDOM.nextLong());
    }

    private String generateRandomTweetBody() {
        int length = RANDOM.nextInt(20) + 10; // Generate tweet body with length between 10 and 30 characters
        return UUID.randomUUID().toString().replace("-", "").substring(0, length);
    }

    private List<String> generateRandomHashTags() {
        int numTags = RANDOM.nextInt(5); // Generate up to 5 random hashtags
        List<String> hashTags = new ArrayList<>();
        for (int i = 0; i < numTags; i++) {
            hashTags.add("#" + UUID.randomUUID().toString().substring(0, 5));
        }
        return hashTags;
    }

    private String generateRandomUsername() {
        return "user_" + UUID.randomUUID().toString().substring(0, 8);
    }

    private ZonedDateTime generateRandomCreatedAt() {
        return ZonedDateTime.now().minusDays(RANDOM.nextInt(365)); // Random date within the last year
    }

}

package com.velsera.homework.controller.valiadations;

import com.velsera.homework.domain.model.Tweet;
import com.velsera.homework.domain.records.PostTweetRequestRecord;
import com.velsera.homework.exceptions.TweetExceptionBadRequest;
import com.velsera.homework.exceptions.TweetExceptionUnauthorized;
import com.velsera.homework.util.UtilHelper;

import java.time.ZonedDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationsUtil {


    private static final ValidationsUtil INSTANCE = new ValidationsUtil();

    private ValidationsUtil() {
    }

    public static ValidationsUtil getInstance() {
        return INSTANCE;
    }

    public void validateHeaderUsername(String xUsername) {
        if (xUsername == null) {
            throw new TweetExceptionUnauthorized("Username header is missing!");
        }
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]{4,32}$");
        Matcher matcher = pattern.matcher(xUsername);
        if (!matcher.matches()) {
            throw new TweetExceptionUnauthorized("Username header is in incorrect format!");
        }
    }

    public Tweet validateTweetBody(String xUsername, PostTweetRequestRecord postTweetRequestRecord) {
        if (postTweetRequestRecord == null) {
            throw new TweetExceptionBadRequest("PostTweetRequestRecord is null");
        }
        if (postTweetRequestRecord.tweetBody() == null) {
            throw new TweetExceptionBadRequest("Post tweet body is empty");
        }

        Tweet tweet = new Tweet();
        tweet.setTweetBody(postTweetRequestRecord.tweetBody());

        if (postTweetRequestRecord.hashTags() != null && !postTweetRequestRecord.hashTags().isEmpty()) {
            tweet.setHashTags(UtilHelper.getInstance().convertFromListOfStringsToString(postTweetRequestRecord.hashTags()));
        }

        tweet.setCreatedBy(xUsername);
        tweet.setCreatedAt(ZonedDateTime.now());
        return tweet;
    }

    public void validatePageLimitAndOffset(Long pageLimit, Long pageOffset) {
        if ((pageLimit != null && pageLimit < 0) || (pageOffset != null && pageOffset < 0)) {
            throw new TweetExceptionBadRequest("Page limit or offset is invalid!");
        }
    }
}

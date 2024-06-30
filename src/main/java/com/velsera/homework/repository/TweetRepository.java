package com.velsera.homework.repository;

import com.velsera.homework.domain.model.Tweet;
import com.velsera.homework.domain.records.TweetResponseRecord;

import java.util.List;

public interface TweetRepository {

    List<TweetResponseRecord> findTweetsByUsernameAndHashTags(List<String> createdBy, List<String> hashTags, Long pageLimit, Long pageOffset);

    List<TweetResponseRecord> findTweetsByUsernameAndHashTagsOld(List<String> createdBy, List<String> hashTags, Long pageLimit, Long pageOffset);

    TweetResponseRecord postNewTweet(Tweet tweet);

    Tweet deleteTweetByIdAndUsername(String xUsername, Long tweetId);
}

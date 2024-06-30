package com.velsera.homework.service;

import com.velsera.homework.domain.model.Tweet;
import com.velsera.homework.domain.records.PostTweetRequestRecord;
import com.velsera.homework.domain.records.TweetResponseRecord;

import javax.validation.Valid;
import java.util.List;


public interface TweetsService {

    List<TweetResponseRecord> findTweetsByUsernameAndHashTags(String xUsername, List<String> createdBy, List<String> hashTags, Long pageLimit, Long pageOffset);

    TweetResponseRecord postNewTweet(String xUsername, @Valid PostTweetRequestRecord postTweetRequestRecord);

    Tweet deleteTweetByIdAndUsername(String xUsername, Long tweetId);
}

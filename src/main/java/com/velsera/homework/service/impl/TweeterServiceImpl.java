package com.velsera.homework.service.impl;

import com.velsera.homework.controller.valiadations.ValidationsUtil;
import com.velsera.homework.domain.model.Tweet;
import com.velsera.homework.domain.records.PostTweetRequestRecord;
import com.velsera.homework.domain.records.TweetResponseRecord;
import com.velsera.homework.repository.TweetRepository;
import com.velsera.homework.service.TweetsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;


@Service
public class TweeterServiceImpl implements TweetsService {

    private final TweetRepository tweetRepository;

    public TweeterServiceImpl(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    @Transactional
    public List<TweetResponseRecord> findTweetsByUsernameAndHashTags(String xUsername, List<String> createdBy, List<String> hashTags, Long pageLimit, Long pageOffset) {

        ValidationsUtil.getInstance().validateHeaderUsername(xUsername);
        ValidationsUtil.getInstance().validatePageLimitAndOffset(pageLimit, pageOffset);

        return tweetRepository.findTweetsByUsernameAndHashTags(createdBy, hashTags, pageLimit, pageOffset);
    }

    @Override
    @Transactional
    public TweetResponseRecord postNewTweet(String xUsername, @Valid PostTweetRequestRecord postTweetRequestRecord) {

        ValidationsUtil.getInstance().validateHeaderUsername(xUsername);
        Tweet tweet = ValidationsUtil.getInstance().validateTweetBody(xUsername, postTweetRequestRecord);

        return tweetRepository.postNewTweet(tweet);
    }

    @Override
    @Transactional
    public Tweet deleteTweetByIdAndUsername(String xUsername, Long tweetId) {
        ValidationsUtil.getInstance().validateHeaderUsername(xUsername);
        return tweetRepository.deleteTweetByIdAndUsername(xUsername, tweetId);
    }
}


package com.velsera.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velsera.homework.controller.valiadations.ValidationsUtil;
import com.velsera.homework.domain.model.Tweet;
import com.velsera.homework.domain.records.PostTweetRequestRecord;
import com.velsera.homework.service.TweetsService;
import com.velsera.homework.util.UtilHelper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.Arrays;

public abstract class ControllerTestBase {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TweetsService tweetsService;

    @MockBean
    UtilHelper utilHelper;

    @MockBean
    ValidationsUtil validationsUtil;

    @Autowired
    ObjectMapper objectMapper;

    PostTweetRequestRecord validPostTweetRequest;
    Tweet validTweet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        String hashTags = "#tag1, #tag2";
        validPostTweetRequest = new PostTweetRequestRecord("This is a valid tweet.", Arrays.stream(hashTags.split(",")).toList());
        validTweet = new Tweet(1L, "user1", "This is a valid tweet.", hashTags, ZonedDateTime.now());
    }
}

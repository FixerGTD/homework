package com.velsera.homework.controller;

import com.velsera.homework.domain.model.Tweet;
import com.velsera.homework.domain.records.PostTweetRequestRecord;
import com.velsera.homework.domain.records.TweetResponseRecord;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TweetController.class)
class TestTweetControllerPost extends ControllerTestBase {

    @Test
    void testPostTweetSuccess() throws Exception {
        PostTweetRequestRecord requestRecord = new PostTweetRequestRecord("This is a tweet body", List.of("#tag1"));
        String zonedDateTime = new String("2024-05-25T19:01:23.785423Z");
        String hashTag = "#tag1, #tag2";
        Tweet tweet = new Tweet(1L, "This is a tweet body", hashTag, "user1", ZonedDateTime.parse(zonedDateTime));
        TweetResponseRecord responseRecord = new TweetResponseRecord(1L, "This is a tweet body", Arrays.stream(hashTag.split(",")).toList(), "user1", ZonedDateTime.parse(zonedDateTime));

        when(validationsUtil.validateTweetBody(any(String.class), any(PostTweetRequestRecord.class))).thenReturn(tweet);
        when(tweetsService.postNewTweet(any(), any())).thenReturn(responseRecord);

        mockMvc.perform(post("/tweets")
                        .header("X-Username", "validUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestRecord)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"totalCount\":1,\"data\":[{\"tweetId\":1,\"tweetBody\":\"This is a tweet body\",\"hashTag\":[\"#tag1\",\" #tag2\"],\"createdBy\":\"user1\",\"createdAt\":\"" + zonedDateTime +
                        "\"}],\"error\":null}"));
    }

    @Test
    void testPostTweetUnauthorized() throws Exception {
        PostTweetRequestRecord requestRecord = new PostTweetRequestRecord("This is a tweet body", List.of("#tag1"));

        mockMvc.perform(post("/tweets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestRecord)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testPostTweetBadRequest() throws Exception {
        PostTweetRequestRecord requestRecord = new PostTweetRequestRecord(null, List.of("#tag1"));

        mockMvc.perform(post("/tweets")
                        .header("X-Username", "validUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestRecord)))
                .andExpect(status().isBadRequest());
    }
}
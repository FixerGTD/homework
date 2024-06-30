package com.velsera.homework.controller;

import com.velsera.homework.domain.model.Tweet;
import com.velsera.homework.exceptions.TweetExceptionForbidden;
import com.velsera.homework.exceptions.TweetExceptionNotFound;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import java.time.ZonedDateTime;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TweetController.class)
class TestTweetControllerDelete extends ControllerTestBase {
    @Test
    void testDeleteTweetSuccess() throws Exception {
        String zonedDateTime = new String("2024-05-25T19:01:23.785423Z");
        Tweet tweet = new Tweet(1L, "This is a tweet body", "#tag1, #tag2", "user1", ZonedDateTime.parse(zonedDateTime));

        when(tweetsService.deleteTweetByIdAndUsername(eq("validUser"), anyLong())).thenReturn(tweet);

        mockMvc.perform(delete("/tweets/1")
                        .header("X-Username", "validUser")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"totalCount\":1,\"data\":[{\"tweetId\":1,\"tweetBody\":\"This is a tweet body\",\"hashTags\":\"#tag1, #tag2\",\"createdBy\":\"user1\",\"createdAt\":\"2024-05-25T19:01:23.785423Z\"}],\"error\":null}"));
    }

    @Test
    void testDeleteTweetUnauthorized() throws Exception {
        mockMvc.perform(delete("/tweets/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testDeleteTweetNotFound() throws Exception {
        Long tweetId = 1L;
        when(tweetsService.deleteTweetByIdAndUsername(eq("validUser"), eq(tweetId))).thenThrow(new TweetExceptionNotFound("Tweet with id " + tweetId + " not found"));

        mockMvc.perform(delete("/tweets/1")
                        .header("X-Username", "validUser")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteTweetForbidden() throws Exception {
        Long tweetId = 1L;
        when(tweetsService.deleteTweetByIdAndUsername(eq("validUser"), eq(tweetId))).thenThrow(new TweetExceptionForbidden("Username mismatch for tweet with id: " + tweetId + ". Cannot delete somebody else's tweet."));

        mockMvc.perform(delete("/tweets/1")
                        .header("X-Username", "validUser")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
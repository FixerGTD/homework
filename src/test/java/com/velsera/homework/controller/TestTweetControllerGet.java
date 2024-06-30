package com.velsera.homework.controller;

import com.velsera.homework.domain.records.TweetPageResponseRecord;
import com.velsera.homework.domain.records.TweetResponseRecord;
import com.velsera.homework.exceptions.TweetExceptionBadRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TweetController.class)
class TestTweetControllerGet extends ControllerTestBase {


    @Test
    void testGetTweetsSuccess() throws Exception {
        List<TweetResponseRecord> firstPage = Arrays.asList(
                new TweetResponseRecord(1L, "First tweet", List.of("#tag1"), "user1", ZonedDateTime.parse("2024-05-25T19:01:23.785423Z")),
                new TweetResponseRecord(2L, "Second tweet", List.of("#tag2"), "user2", ZonedDateTime.parse("2024-05-25T19:01:23.785456Z"))
        );

        when(tweetsService.findTweetsByUsernameAndHashTags(any(), any(), any(), any(), any())).thenReturn(firstPage);
        when(utilHelper.getNextPageUrl(any(), any(), any(), any())).thenReturn("http://nextPageUrl");
        when(utilHelper.getTweetPageResponse(any(), any())).thenReturn(new TweetPageResponseRecord(firstPage, "http://nextPageUrl"));

        String expectedJsonContent = "{\"totalCount\":2,\"data\":[{\"tweetResponseRecords\":[{\"tweetId\":1,\"tweetBody\":\"First tweet\",\"hashTag\":[\"#tag1\"],\"createdBy\":\"user1\",\"createdAt\":\"2024-05-25T19:01:23.785423Z\"},{\"tweetId\":2,\"tweetBody\":\"Second tweet\",\"hashTag\":[\"#tag2\"],\"createdBy\":\"user2\",\"createdAt\":\"2024-05-25T19:01:23.785456Z\"}],\"nextPage\":\"/v1/tweets?&createdBy=user1&createdBy=user2&hashTag=#tag1&hashTag=#tag2&pageLimit=10&pageOffset=2\"}],\"error\":null}";

        mockMvc.perform(get("/tweets")
                        .header("X-Username", "validUser")
                        .param("username", "user1", "user2")
                        .param("hashTag", "#tag1", "#tag2")
                        .param("pageLimit", "10")
                        .param("pageOffset", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJsonContent));
    }

    @Test
    void testGetTweetsUnauthorized() throws Exception {
        mockMvc.perform(get("/tweets")
                        .param("username", "user1", "user2")
                        .param("hashTag", "#tag1", "#tag2")
                        .param("pageLimit", "10")
                        .param("pageOffset", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetTweetsBadRequest() throws Exception {
        doThrow(new TweetExceptionBadRequest("Page limit or offset is invalid!")).when(tweetsService).findTweetsByUsernameAndHashTags(any(), any(), any(), any(), any());
        mockMvc.perform(get("/tweets")
                        .header("X-Username", "validUser")
                        .param("pageLimit", "-1")
                        .param("pageOffset", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
package com.velsera.homework.api.controller;


import com.velsera.homework.domain.dto.TweetPageResponse;
import com.velsera.homework.domain.dto.TweetResponse;
import com.velsera.homework.domain.model.Tweet;
import com.velsera.homework.service.impl.TweetsService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.velsera.homework.util.string.StringUtil;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Tag(name = "Tweeter APIs", description = "A set of APIs for retrieval, creation and deletion of tweets.")
@RestController
@RequestMapping(path = "tweets", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class TweetController {

    private final TweetsService tweetsService;


    @GetMapping()
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK."),
            @ApiResponse(responseCode = "400", description = "Bad request, some of the specified parameters are not valid."),
            @ApiResponse(responseCode = "401", description = "Error returned when username header is missing.")
    })
    public ResponseEntity<TweetPageResponse> tweets(
            @RequestHeader("X-Username") String xUsername,
            @RequestParam(required = false) List<String> usernames,
            @RequestParam(required = false) List<String> hashTag,
            @RequestParam(required = false) @Positive Integer limit, // pagesSize
            @RequestParam(required = false) @PositiveOrZero Integer offset
    ) {


        List<Tweet> tweetList = tweetsService.findByCreatedByInAndHashTagsIn(usernames, hashTag, limit, offset);
        List<TweetResponse> tweetResponses = new ArrayList<>();
        for (Tweet tweet : tweetList) {
            tweetResponses.add(new TweetResponse(tweet.getId(), tweet.getBody(), StringUtil.convertToListOfStrings(tweet.getHashTags(), Pattern.compile(",")), tweet.getCreatedBy(), tweet.getCreatedAt().toString()));
        }
        TweetPageResponse tweetPageResponse = new TweetPageResponse(tweetResponses, null);
        return ResponseEntity.ok(tweetPageResponse);
    }
}



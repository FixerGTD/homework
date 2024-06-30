package com.velsera.homework.controller;


import com.velsera.homework.domain.model.Tweet;
import com.velsera.homework.domain.model.response.FacadeResponse;
import com.velsera.homework.domain.records.PostTweetRequestRecord;
import com.velsera.homework.domain.records.TweetPageResponseRecord;
import com.velsera.homework.domain.records.TweetResponseRecord;
import com.velsera.homework.service.TweetsService;
import com.velsera.homework.util.UtilHelper;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Tweeter APIs", description = "A set of APIs for retrieval, creation and deletion of tweets.")
@RestController
@RequestMapping(path = "tweets", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Slf4j
public class TweetController {

    private final TweetsService tweetsService;
    private static final String X_USERNAME = "X-Username";

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "A page (potentially empty) of tweets matching the query params."),
            @ApiResponse(responseCode = "401", description = "Error returned when username header is missing - HttpStatus.UNAUTHORIZED."),
            @ApiResponse(responseCode = "400", description = "Bad request, some of the specified parameters are not valid - HttpStatus.BAD_REQUEST.")
    })
    @Transactional
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<FacadeResponse<TweetPageResponseRecord>> tweets(
            @RequestHeader(X_USERNAME) String xUsername,
            @RequestParam(required = false) List<String> username,
            @RequestParam(required = false) List<String> hashTag,
            @Positive @RequestParam(required = false) Long pageLimit,
            @PositiveOrZero @RequestParam(required = false) Long pageOffset
    ) {
        // First page
        List<TweetResponseRecord> firstPage = tweetsService.findTweetsByUsernameAndHashTags(xUsername, username, hashTag, pageLimit, pageOffset);
        // Next page (using the last tweetId from the previous page)
        String nextPageUrl = UtilHelper.getInstance().getNextPageUrl(username, hashTag, pageLimit, firstPage);
        FacadeResponse<TweetPageResponseRecord> response = new FacadeResponse<>(firstPage.size(), List.of(UtilHelper.getInstance().getTweetPageResponse(firstPage, nextPageUrl)), null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Returns created tweet."),
            @ApiResponse(responseCode = "401", description = "Error returned when username header is missing - HttpStatus.UNAUTHORIZED."),
            @ApiResponse(responseCode = "400", description = "Error returned when some of the request validations fails - HttpStatus.BAD_REQUEST.")
    })
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<FacadeResponse<TweetResponseRecord>> tweets(
            @RequestHeader(X_USERNAME) String xUsername,
            @RequestBody @Valid PostTweetRequestRecord postTweetRequestRecord

    ) {
        FacadeResponse<TweetResponseRecord> response = new FacadeResponse<>(1, List.of(tweetsService.postNewTweet(xUsername, postTweetRequestRecord)), null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tweet deleted successfully."),
            @ApiResponse(responseCode = "401", description = "Error returned when username header is missing - HttpStatus.UNAUTHORIZED."),
            @ApiResponse(responseCode = "404", description = "Error returned when tweet not found - HttpStatus.NOT_FOUND."),
            @ApiResponse(responseCode = "403", description = "Error returned when user tried to delete somebody else's tweet - HttpStatus.FORBIDDEN.")
    })
    @DeleteMapping("{tweetId}")
    public ResponseEntity<FacadeResponse<Tweet>> tweets(
            @RequestHeader(X_USERNAME) String xUsername, @PathVariable @PositiveOrZero Long tweetId) {
        FacadeResponse<Tweet> response = new FacadeResponse<>(1, List.of(tweetsService.deleteTweetByIdAndUsername(xUsername, tweetId)), null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}


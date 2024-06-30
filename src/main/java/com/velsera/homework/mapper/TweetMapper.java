package com.velsera.homework.mapper;

import com.velsera.homework.domain.dto.TweetDTO;
import com.velsera.homework.domain.model.Tweet;
import com.velsera.homework.domain.records.TweetResponseRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class TweetMapper implements BaseMapper {

    @Mapping(target = "tweetId", expression = "java(tweetDTO.getTweetId())")
    @Mapping(target = "tweetBody", expression = "java(tweetDTO.getTweetBody())")
    @Mapping(target = "hashTag", expression = "java(java.util.Arrays.stream(tweetDTO.getHashTags().split(\",\")).collect(java.util.stream.Collectors.toList()))")
    @Mapping(target = "createdBy", expression = "java(tweetDTO.getCreatedBy())")
    @Mapping(target = "createdAt", expression = "java(tweetDTO.getCreatedAt())")
    public abstract TweetResponseRecord toTweetResponseRecord(TweetDTO tweetDTO);

    public abstract List<TweetResponseRecord> fromTweetDTOSToTweetResponseRecords(List<TweetDTO> tweetDTOS);

    @Mapping(target = "tweetId", expression = "java(tweet.getTweetId())")
    @Mapping(target = "tweetBody", expression = "java(tweet.getTweetBody())")
    @Mapping(target = "hashTag", expression = "java(java.util.Arrays.stream(tweet.getHashTags().split(\",\")).collect(java.util.stream.Collectors.toList()))")
    @Mapping(target = "createdBy", expression = "java(tweet.getCreatedBy())")
    @Mapping(target = "createdAt", expression = "java(tweet.getCreatedAt())")
    public abstract TweetResponseRecord toTweetResponseRecord(Tweet tweet);

    public abstract List<TweetResponseRecord> fromTweetsToTweetResponseRecords(List<Tweet> tweets);

}
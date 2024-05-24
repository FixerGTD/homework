package com.velsera.homework.service.impl;

import com.velsera.homework.domain.model.Tweet;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TweetsService extends JpaRepository<Tweet, Long> {

    @QueryHints({
            @QueryHint(name = "org.hibernate.fetchSize", value = "10000")
    })
    @Query("SELECT t FROM Tweet t WHERE t.createdBy IN (:usernames) AND t.hashTags IN (:hashTags) LIMIT  :pageLimit OFFSET :pageOffset")
    List<Tweet> findByCreatedByInAndHashTagsIn(List<String> usernames, List<String> hashTags, Integer pageLimit, Integer pageOffset);

}

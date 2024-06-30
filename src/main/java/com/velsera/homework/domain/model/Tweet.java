package com.velsera.homework.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "tweet")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Tweet implements Serializable {

    @Serial
    private static final long serialVersionUID = 149497214590148520L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false, unique = true, name = "TWEET_ID")
    private Long tweetId;

    @Column(name = "TWEET_BODY")
    private String tweetBody;

    @Column(name = "HASH_TAGS")
    private String hashTags;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_AT")
    private ZonedDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tweet tweet)) return false;
        return Objects.equals(tweetId, tweet.tweetId) && Objects.equals(createdAt, tweet.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tweetId, createdAt);
    }
}

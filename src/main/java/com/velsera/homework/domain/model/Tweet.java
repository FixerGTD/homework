package com.velsera.homework.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "TWEET")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Tweet implements Serializable {

    @Serial
    private static final long serialVersionUID = 149497214590148520L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false, unique = true, name = "TWEET_ID")
    private Long id;

    @Column(name = "TWEET_BODY")
    private String body;

    @Column(name = "HASH_TAGS")
    private String hashTags;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_AT")
    private ZonedDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Tweet that = (Tweet) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBody(), getCreatedBy(), getCreatedBy());
    }
}

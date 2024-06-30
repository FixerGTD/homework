package com.velsera.homework.repository.impl;

import com.velsera.homework.domain.model.Tweet;
import com.velsera.homework.domain.records.TweetResponseRecord;
import com.velsera.homework.domain.transformer.TweetRecordTransformer;
import com.velsera.homework.exceptions.TweetExceptionBadRequest;
import com.velsera.homework.exceptions.TweetExceptionForbidden;
import com.velsera.homework.exceptions.TweetExceptionNotFound;
import com.velsera.homework.mapper.TweetMapper;
import com.velsera.homework.repository.TweetRepository;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class TweetRepositoryImpl implements TweetRepository {

    private final TweetMapper tweetMapper;

    @PersistenceContext
    EntityManager entityManager;

    public TweetRepositoryImpl(TweetMapper tweetMapper) {
        this.tweetMapper = tweetMapper;
    }

    @Deprecated
    @Override
    public List<TweetResponseRecord> findTweetsByUsernameAndHashTagsOld(List<String> createdBy, List<String> hashTags, Long pageLimit, Long pageOffset) {
        try {
            long startTime = System.currentTimeMillis();

            StringBuilder queryString = new StringBuilder("""
                    SELECT
                    t.tweetId as tweet_id, t.tweetBody as tweet_body, t.hashTags as hash_tags, t.createdBy as created_by, t.createdAt as created_at
                    FROM Tweet t WHERE 1=1
                    """);

            boolean createdByIsNotEmpty = createdBy != null && !createdBy.isEmpty();
            boolean hashTagsIsNotEmpty = hashTags != null && !hashTags.isEmpty();

            if (createdByIsNotEmpty) {
                queryString.append(" AND t.createdBy IN (:createdBy)");
            }
            if (hashTagsIsNotEmpty) {
                queryString.append(" AND t.hashTags IN (:hashTags)");
            }
            if (pageOffset != null) {
                queryString.append(" AND t.tweetId > :pageOffset");
            }

            queryString.append(" ORDER BY t.tweetId ASC");

            Query query = entityManager.createQuery(queryString.toString());

            if (createdByIsNotEmpty) {
                query.setParameter("createdBy", createdBy);
            }
            if (hashTagsIsNotEmpty) {
                query.setParameter("hashTags", hashTags);
            }
            if (pageOffset != null) {
                query.setParameter("pageOffset", pageOffset);
            }
            if (pageLimit != null) {
                query.setMaxResults(pageLimit.intValue());
            }

            List resultList = query.unwrap(org.hibernate.query.Query.class)
                    .setResultTransformer(new TweetRecordTransformer())
                    .setHint("org.hibernate.cacheable", Boolean.TRUE)
                    .setHint("org.hibernate.fetchSize", 1000000)
                    .getResultList();

            List tweetResponseRecords = tweetMapper.fromTweetDTOSToTweetResponseRecords(resultList);
            log.info("Total tweets found in: {} ms", System.currentTimeMillis() - startTime);
            return tweetResponseRecords;
        } catch (Exception e) {
            throw new TweetExceptionBadRequest("Error while finding tweets");
        }
    }


    @Override
    public List<TweetResponseRecord> findTweetsByUsernameAndHashTags(List<String> createdBy, List<String> hashTags, Long pageLimit, Long pageOffset) {
        try {
            long startTime = System.currentTimeMillis();

            // Create Pageable from pageLimit and pageOffset
            int pageSize = pageLimit.intValue();
            int pageNumber = pageOffset.intValue() / pageSize;
            Pageable pageable = PageRequest.of(pageNumber, pageSize);

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Tweet> cq = cb.createQuery(Tweet.class);
            Root<Tweet> tweet = cq.from(Tweet.class);

            List<Predicate> predicates = new ArrayList<>();

            if (createdBy != null && !createdBy.isEmpty()) {
                predicates.add(tweet.get("createdBy").in(createdBy));
            }
            if (hashTags != null && !hashTags.isEmpty()) {
                predicates.add(tweet.get("hashTags").in(hashTags));
            }

            cq.where(predicates.toArray(new Predicate[0]));
            cq.orderBy(cb.asc(tweet.get("tweetId")));

            TypedQuery<Tweet> query = entityManager.createQuery(cq);
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());

            List<Tweet> resultList = query.getResultList();
            List<TweetResponseRecord> tweetResponseRecords = tweetMapper.fromTweetsToTweetResponseRecords(resultList);

            log.info("Total tweets found in: {} ms", System.currentTimeMillis() - startTime);
            return tweetResponseRecords;
        } catch (Exception e) {
            throw new TweetExceptionBadRequest("Error while finding tweets", e);
        }
    }

    @Override
    public TweetResponseRecord postNewTweet(Tweet tweet) {
        try {
            Tweet merged = entityManager.merge(tweet);
            return tweetMapper.toTweetResponseRecord(merged);
        } catch (Exception e) {
            throw new TweetExceptionBadRequest("Error while posting a new tweet");
        }

    }

    @Override
    public Tweet deleteTweetByIdAndUsername(String xUsername, Long tweetId) {
        Tweet singleResult;
        try {
            singleResult = entityManager.createQuery("SELECT t FROM Tweet t WHERE t.tweetId = :tweetId", Tweet.class)
                    .setParameter("tweetId", tweetId).getSingleResult();
        } catch (NoResultException e) {
            throw new TweetExceptionNotFound("Specific tweet does not exist");
        }

        if (!singleResult.getCreatedBy().equals(xUsername)) {
            throw new TweetExceptionForbidden("Username mismatch for specific tweet. Cannot delete somebody else's tweet.");
        }
        entityManager.createQuery("DELETE FROM Tweet t WHERE t.tweetId = :tweetId AND t.createdBy = :xUsername").setParameter("tweetId", tweetId).setParameter("xUsername", xUsername).executeUpdate();
        return singleResult;
    }
}

package com.velsera.homework.repository;

import com.velsera.homework.domain.model.Tweet;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    @Override
    <S extends Tweet> S save(S entity);

    @Override
    <S extends Tweet> List<S> findAll(Example<S> example);

    @Override
    void deleteById(Long aLong);
}

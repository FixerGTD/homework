package com.velsera.homework.util;

import com.velsera.homework.domain.records.TweetPageResponseRecord;
import com.velsera.homework.domain.records.TweetResponseRecord;
import com.velsera.homework.exceptions.TweetException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class UtilHelper {

    private static final UtilHelper INSTANCE = new UtilHelper();

    private UtilHelper() {
    }

    public static UtilHelper getInstance() {
        return INSTANCE;
    }

    public String convertFromListOfStringsToString(List<String> value) {
        return String.join(", ", value);
    }

    public String getNextPageUrl(List<String> username, List<String> hashTag, Long pageLimit, List<TweetResponseRecord> firstPage) {
        Long nextCursor = null;
        if (!firstPage.isEmpty()) {
            nextCursor = firstPage.get(firstPage.size() - 1).tweetId();
        }
        StringBuilder nextPageUrlBuilder = new StringBuilder("/v1/tweets?");

        if (username != null && !username.isEmpty()) {
            username.forEach(e -> nextPageUrlBuilder.append("&createdBy=").append(e));
        }
        if (hashTag != null && !hashTag.isEmpty()) {
            hashTag.forEach(e -> nextPageUrlBuilder.append("&hashTag=").append(e));
        }
        if (pageLimit != null) {
            nextPageUrlBuilder.append("&pageLimit=").append(pageLimit);
        }
        if (nextCursor != null) {
            nextPageUrlBuilder.append("&pageOffset=").append(nextCursor);
        }
        return nextCursor != null ? nextPageUrlBuilder.toString() : null;
    }

    public TweetPageResponseRecord getTweetPageResponse(List<TweetResponseRecord> tweetResponseRecords, String nextPageUrl) {
        // Use a fixed thread pool to limit the number of threads.
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Callable<TweetResponseRecord>> tasks = getCallables(tweetResponseRecords);

        List<TweetResponseRecord> tweetResponses;
        try {
            // Execute all tasks and collect the results
            List<Future<TweetResponseRecord>> futures = executorService.invokeAll(tasks);
            tweetResponses = futures.stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            throw new TweetException("Error while creating TweetPageResponseRecord.", e);
                        }
                    })
                    .collect(Collectors.toList());
        } catch (InterruptedException e) {
            throw new TweetException("Task interrupted", e);
        } finally {
            executorService.shutdown();
        }

        return new TweetPageResponseRecord(tweetResponses, nextPageUrl);
    }

    private static List<Callable<TweetResponseRecord>> getCallables(List<TweetResponseRecord> tweetResponseRecords) {
        List<Callable<TweetResponseRecord>> tasks = new ArrayList<>();

        // Create tasks for each tweet
        for (TweetResponseRecord tweetResponseRecord : tweetResponseRecords) {
            tasks.add(() -> new TweetResponseRecord(
                    tweetResponseRecord.tweetId(),
                    tweetResponseRecord.tweetBody(),
                    tweetResponseRecord.hashTag(),
                    tweetResponseRecord.createdBy(),
                    tweetResponseRecord.createdAt()
            ));
        }
        return tasks;
    }

}

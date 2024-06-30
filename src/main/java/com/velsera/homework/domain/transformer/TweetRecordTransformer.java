package com.velsera.homework.domain.transformer;

import com.velsera.homework.domain.dto.TweetDTO;
import org.hibernate.transform.ResultTransformer;

import java.io.Serial;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TweetRecordTransformer implements ResultTransformer {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Map<Long, TweetDTO> tweetResponseRecordMap = new LinkedHashMap<>();

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        Map<String, Integer> aliasToIndexMap = aliasToIndexMap(aliases);

        Long tweeterRecordId = (Long) tuple[aliasToIndexMap.get(TweetDTO.TWEET_ID)];
        return tweetResponseRecordMap.computeIfAbsent(tweeterRecordId, id -> new TweetDTO(tuple, aliasToIndexMap));
    }

    @Override
    public List transformList(List list) {
        return new ArrayList<>(tweetResponseRecordMap.values());
    }

    public Map<String, Integer> aliasToIndexMap(String[] aliases) {
        Map<String, Integer> aliasToIndexMap = new LinkedHashMap<>();
        for (int i = 0; i < aliases.length; i++) {
            aliasToIndexMap.put(aliases[i], i);
        }
        return aliasToIndexMap;
    }
}

package dev.demo.search.repository;

import dev.demo.search.domain.GameInfo2;
import dev.demo.search.domain.GameInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomGameRepositoryImpl implements CustomGameRepository {
    private static final String WILDCARD = "/";
    private static final String INSENSITIVE = "i";  // 대소문자 구분 없이

    private final MongoTemplate mongoTemplate;
    private final MongoOperations mongoOperations;

    @Deprecated(since = "0.1")  // 쿼리 생성 이슈로 결과값이 안나옴
    public List<GameInfo2> findGameByKeywordTemplate(String keyword){
        Query query = new Query();
        query.addCriteria(Criteria.where("title").regex(WILDCARD+keyword+WILDCARD,INSENSITIVE));
        List<GameInfo2> games = mongoTemplate.find(query, GameInfo2.class);
        return games;
    }

    public List<GameInfo2> findGameByKeywordTest(String keyword){
        BasicQuery query = new BasicQuery("{$or:[{title:{$regex:/"+keyword+"/}},{title_en:{$regex:/"+keyword+"/}}]}");
        List<GameInfo2> games = mongoOperations.find(query, GameInfo2.class);
        return games;
    }

    public List<GameInfo> findGameByKeyword(String keyword){
        BasicQuery query = new BasicQuery("{$or:[{title:{$regex:/"+keyword+"/}}," +
                                            "{developer:{$regex:/"+keyword+"/}}," +
                                            "{publisher:{$regex:/"+keyword+"/}}," +
                                            "{introduce:{$regex:/"+keyword+"/}}," +
                                            "]}");
        List<GameInfo> games = mongoOperations.find(query, GameInfo.class);
        return games;
    }
}

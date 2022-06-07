package dev.demo.search.repository;

import dev.demo.search.domain.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Repository
public class BoardSearchRepository {

    private final ElasticsearchOperations elasticsearchOperations;

    public List<Board> searchAll() {
        Query query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery()).build();
        SearchHits<Board> searchHits = elasticsearchOperations.search(query, Board.class);
        return toBoardEntity(searchHits);
    }

    public List<Board> searchBoard(String keyword, String type, Pageable pageable) {
        Query query = generateDefaultQuery(keyword)
                .withQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.wildcardQuery("user.grade_name", type)))
                .withPageable(pageable)
                .build();
//        Query query = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.boolQuery()
//                        .must(QueryBuilders.wildcardQuery("user.grade_name", type))
//                        .must(QueryBuilders.queryStringQuery(keyword)
//                                .field("title").field("content").field("author"))
//                ).withPageable(pageable)
//                .build();
        SearchHits<Board> searchHits = elasticsearchOperations.search(query, Board.class);
        return toBoardEntity(searchHits);
    }

    public List<Board> findByKeywordForAll(String keyword, Pageable pageable) {
        Query query = generateDefaultQuery(keyword).withPageable(pageable).build();
        SearchHits<Board> searchHits = elasticsearchOperations.search(query, Board.class);
        return toBoardEntity(searchHits);
    }

    public Integer getTotalCount(String keyword) {
        Query query = generateDefaultQuery(keyword).build();
        SearchHits<Board> searchHits = elasticsearchOperations.search(query, Board.class);
        return Math.toIntExact(searchHits.getTotalHits());

    }

    private NativeSearchQueryBuilder generateDefaultQuery(String keyword) {
        return new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
//                        .should(QueryBuilders.queryStringQuery(keyword).defaultField("title"))
//                        .should(QueryBuilders.queryStringQuery(keyword).defaultField("content"))
//                        .should(QueryBuilders.queryStringQuery(keyword).defaultField("author"))
                        .must(QueryBuilders.queryStringQuery(keyword)
                                .field("title").field("content").field("author"))
                );
    }

    private List<Board> toBoardEntity(SearchHits<Board> searchHits) {
        return searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

}
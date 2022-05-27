package dev.demo.search.repository;

import dev.demo.search.domain.Board;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@Repository
public class BoardSearchRepository {

    private final ElasticsearchOperations elasticsearchOperations;

    public List<Board> searchTest() {
        Query query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .build();
        return getBoardResult(query);
    }

    public List<Board> searchBoard(String keyword, String type, Pageable pageable) {
        Query query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.queryStringQuery(type).defaultField("type"))
                        .should(QueryBuilders.queryStringQuery(keyword).defaultField("title"))
                        .should(QueryBuilders.queryStringQuery(keyword).defaultField("content"))
                        .should(QueryBuilders.queryStringQuery(keyword).defaultField("author"))
                ).withPageable(pageable)
                .build();
        Query query1 = generateDefaultQuery(keyword)
                .withQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.queryStringQuery(type).defaultField("type")))
                    .build();
        return getBoardResult(query);
    }

    public List<Board> findByKeywordForAll(String keyword, Pageable pageable) {
        Query query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        .should(QueryBuilders.queryStringQuery(keyword).defaultField("title"))
                        .should(QueryBuilders.queryStringQuery(keyword).defaultField("content"))
                        .should(QueryBuilders.queryStringQuery(keyword).defaultField("author"))
                ).withPageable(pageable).build();

        Query query1 = generateDefaultQuery(keyword).withPageable(pageable).build();

        return getBoardResult(query);
    }

    private NativeSearchQueryBuilder generateDefaultQuery(String keyword) {
        return new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        .should(QueryBuilders.queryStringQuery(keyword).defaultField("title"))
                        .should(QueryBuilders.queryStringQuery(keyword).defaultField("content"))
                        .should(QueryBuilders.queryStringQuery(keyword).defaultField("author"))
                );
    }




    private List<Board> getBoardResult(Query query) {
        SearchHits<Board> searchHits = elasticsearchOperations.search(query, Board.class);
        return searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

}

package dev.demo.search.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.Mapping;

@Getter
@Document(indexName = "gamewood.board2")
@Mapping(mappingPath = "mappings.json")
public class Board {

    @Id
    private String id;
    private final String title;
    private final String content;
    @Field("user.cp_home_game_nickname")
    private final String author;
    private final String createdAt;

    @Builder
    @PersistenceConstructor
    public Board(String title, String content, String author, String createdAt) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
    }
}

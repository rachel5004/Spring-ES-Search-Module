package dev.demo.search.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Builder
@Document(collection = "test4")
public class GameInfo2 {
    @Id
    private String id;

    @Field
    private String title;

    @Field("title_en")
    private String titleEn;

}


package dev.demo.search.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Document(collection = "gameinfo")
public class GameInfo {
    @Id
    private String id;

    private String title;
    @Field("title_en")
    private String englishTitle;
    private String developer;
    @Field("release_info")
    private List<ReleaseInfo> releaseInfo;
    private List<String> genre;
//    private String introduce;
//    private String posterImage;
//    private String star;

    @Getter
    @Builder
    public static class ReleaseInfo {
        private String platform;
        private String publisher;
        @Field("release_date")
        private String releaseDate;
    }

    @Builder
    public GameInfo(String title, String englishTitle, String developer, List<ReleaseInfo> releaseInfo, List<String> genre) {
        this.title = title;
        this.englishTitle = englishTitle;
        this.developer = developer;
        this.releaseInfo = releaseInfo;
        this.genre = genre;
    }
}


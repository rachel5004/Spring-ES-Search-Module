package dev.demo.search.domain;

import dev.demo.search.common.util.Constants;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

public class SearchDto {

    @Getter
    @Builder
    public static class SearchResponse {
        private Integer totalCount;
        private Integer gameCount;
        private List<GameInfo> gameInfos;
        private Integer boardCount;
        private List<Board> boards;

        public static SearchResponse of(Integer gameCount, List<GameInfo> gameInfos,Integer boardCount, List<Board> boards) {
            return SearchResponse.builder()
                    .totalCount(gameCount+boardCount)
                    .gameCount(gameCount)
                    .gameInfos(gameInfos)
                    .boardCount(boardCount)
                    .boards(boards).build();
        }
    }

    @Getter
    @Builder
    public static class SearchGameResponse {
        private List<String> platforms;
        private List<String> genres;
        private Integer totalCount;
        private List<GameInfo> gameInfos;

        public static SearchGameResponse of(Integer totalCount, List<GameInfo> gameInfos) {
            return SearchGameResponse.builder()
                    .platforms(Constants.PLATFORMS)
                    .genres(Constants.GENRES)
                    .totalCount(totalCount)
                    .gameInfos(gameInfos).build();
        }
    }

    @Getter
    @Builder
    public static class SearchBoardResponse {
        private Integer totalCount;
        private List<Board> boards;

        public static SearchBoardResponse of(List<Board> boards) {
            return SearchBoardResponse.builder()
                    .totalCount(boards.size())
                    .boards(boards).build();
        }
    }
}

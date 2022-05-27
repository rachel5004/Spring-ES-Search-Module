package dev.demo.search.domain;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

public class SearchDto {

    @Getter
    @Builder
    public static class SearchResponse {
        private int totalCount;
        private List<GameInfo> gameInfos;
        private List<Board> boards;

        public static SearchResponse of(int totalCount, List<GameInfo> gameInfos, List<Board> boards) {
            return SearchResponse.builder()
                    .totalCount(totalCount)
                    .gameInfos(gameInfos)
                    .boards(boards).build();
        }
    }

    @Getter
    @Builder
    public static class SearchGameResponse {
        private int totalCount;
        private List<GameInfo> gameInfos;

        public static SearchGameResponse of(int totalCount, List<GameInfo> gameInfos) {
            return SearchGameResponse.builder()
                    .totalCount(totalCount)
                    .gameInfos(gameInfos).build();
        }
    }

    @Getter
    @Builder
    public static class SearchBoardResponse {
        private int totalCount;
        private List<Board> boards;

        public static SearchBoardResponse of(List<Board> boards) {
            return SearchBoardResponse.builder()
                    .totalCount(boards.size())
                    .boards(boards).build();
        }
    }
}

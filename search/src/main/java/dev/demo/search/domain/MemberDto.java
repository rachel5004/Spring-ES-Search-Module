package dev.demo.search.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class MemberDto {

    @Getter
    @Builder
    public static class FeedResponse {
        private List<Board> feeds;

        public static MemberDto.FeedResponse of(List<Board> feeds) {
            return FeedResponse.builder()
                    .feeds(feeds).build();
        }
    }

    @Getter
    @Builder
    public static class CommentResponse {

    }

    @Getter
    @Builder
    public static class FavoriteResponse {

    }

    @Getter
    @Builder
    public static class BookmarkResponse {

    }

    @Getter
    @Builder
    public static class ReviewResponse {

    }

}

package dev.demo.search.service;


import dev.demo.search.domain.Board;
import dev.demo.search.domain.MemberDto;
import dev.demo.search.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberDto.FeedResponse getFeed(String memberNo, Integer page) {
//        List<Board> boards = memberRepository.getFeedByMemberNo(memberNo, page);
        Board board = new Board("","","","");
        return MemberDto.FeedResponse.of((List<Board>) board);
    }
//
//    public MemberDto.CommentResponse getComment(Integer page) {
//
//
//    }
//
//    public MemberDto.FavoriteResponse getFavoriteGames(Integer page) {
//
//
//    }
//
//    public MemberDto.BookmarkResponse getBookmarkPosts(Integer page) {
//
//
//    }
//
//    public MemberDto.ReviewResponse getReview(Integer page) {
//
//
//    }
}

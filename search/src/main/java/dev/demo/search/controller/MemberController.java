package dev.demo.search.controller;


import dev.demo.search.common.annotation.Authorized;
import dev.demo.search.common.response.CommonResponse;
import dev.demo.search.common.util.SecurityUtil;
import dev.demo.search.domain.MemberDto.*;
import dev.demo.search.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/member")
public class MemberController {
    private final MemberService memberService;

    @Authorized
    @GetMapping("/feed")
    public CommonResponse<FeedResponse> mypageFeed(Integer page) {
        var feedResponse = memberService.getFeed(SecurityUtil.getCurrentMemberNo(), page);
        return CommonResponse.success(feedResponse);
    }

//    @Authorized
//    @GetMapping("/comment")
//    public CommonResponse<CommentResponse> mypageComment(Integer page) {
//
//
//    }
//
//    @Authorized
//    @GetMapping("/favorite")
//    public CommonResponse<FavoriteResponse> mypageFavoriteGames(Integer page) {
//
//
//    }
//
//    @Authorized
//    @GetMapping("/bookmark")
//    public CommonResponse<BookmarkResponse> mypageBookmarkPosts(Integer page) {
//
//
//    }
//
//    @Authorized
//    @GetMapping("/review")
//    public CommonResponse<ReviewResponse> mypageReview(Integer page) {
//
//
//    }



}

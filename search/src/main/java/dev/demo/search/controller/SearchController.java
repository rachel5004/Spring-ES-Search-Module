package dev.demo.search.controller;

import dev.demo.search.common.annotation.NonAuthorized;
import dev.demo.search.common.response.CommonResponse;
import dev.demo.search.domain.SearchDto.*;
import dev.demo.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

    private static final String ALL = ".*";
    private final SearchService searchService;

    // 통합 검색 (게임 총 결과 수, 상위 4개 게임상세, 게시글 총 갯수, 상위 4개 글상세)
    // 키워드
    @NonAuthorized
    @GetMapping
    public CommonResponse<SearchResponse> searchAll(@RequestParam String keyword) {
        var searchInfo = searchService.searchByKeyword(keyword);
        return CommonResponse.success(searchInfo);
    }

    @NonAuthorized
    @GetMapping("/test")
    public CommonResponse<SearchBoardResponse> searchAll() {
        var searchInfo = searchService.searchTest();
        return CommonResponse.success(searchInfo);
    }

    // 게임 검색
    // 키워드, 플랫폼, 장르
    @NonAuthorized
    @GetMapping("/game")
    public CommonResponse<SearchGameResponse> searchGame(@RequestParam String keyword,
                                                         @RequestParam Optional<String> platform,
                                                         @RequestParam Optional<String> genre,
                                                         @RequestParam int page) {
        SearchGameResponse searchResponse =
                searchService.searchGameByKeyword(keyword, platform.orElse(ALL), genre.orElse(ALL), page);
        return CommonResponse.success(searchResponse);
    }


    // 게시글 검색
    // 전체, 자유, 공략
    // 최신순, 좋아요순, 조회순 조회 가능
    @NonAuthorized
    @GetMapping("/board")
    public CommonResponse<SearchBoardResponse> searchBoard(@RequestParam String keyword,
                                                           @RequestParam Optional<String> type,
                                                           @RequestParam int page) {
        SearchBoardResponse searchResponse = searchService.searchBoardByKeyword(keyword, type.orElse(ALL), page);
        return CommonResponse.success(searchResponse);
    }

}